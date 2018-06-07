package com.hill.ybsj.dao.impl;

import com.hill.ybsj.api.maputil.GeocodingTools2;
import com.hill.ybsj.api.maputil.MapUtil;
import com.hill.ybsj.api.maputil.TCityLocationEntity2;
import com.hill.ybsj.dao.ISurveyDao;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SurveyDaoImpl implements ISurveyDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public String getCityList(String orgid) throws SQLException {
        Session session = sessionFactory.openSession();
        String sql = "SELECT ORGID,ORGNAME FROM T_ORG t WHERE P_ORGID =?";
        Query query = session.createSQLQuery(sql)
                .setParameter(0, orgid)
                .addScalar("ORGID", StandardBasicTypes.STRING)
                .addScalar("ORGNAME", StandardBasicTypes.STRING);
        JSONArray json = new JSONArray();
        for (Object o : query.list()) {
            Object[] objects = (Object[]) o;
            String ORGID = (String) objects[0];
            String ORGNAME = (String) objects[1];
            JSONObject jo = new JSONObject();
            jo.put("ORGID", ORGID);
            jo.put("ORGNAME", ORGNAME);
            json.add(jo);
        }
        session.close();
        return "{\"dataList\":" + json.toJSONString() + "}";
    }

    @Override
    public String getSurveyType(String rowv) throws SQLException {
        Session session = sessionFactory.openSession();
        String sql = null;
        if (null == rowv) {
            sql = "SELECT t.survey_type,t.type_id||'_'||t.COL_NUM tab FROM T_SURVEY_TYPE t ";
        } else {
            sql = "SELECT s.survey_type,s.type_id ||'_'|| NVL(s.open_lat_lng,0) ||'_'|| t.col tab FROM T_SURVEY_TYPE s left join T_SIMPLE_COL t on S.TYPE_ID = T.TYPE_ID and t.is_addr=1";
        }
        Query query = session.createSQLQuery(sql)
                .addScalar("survey_type", StandardBasicTypes.STRING)
                .addScalar("tab", StandardBasicTypes.STRING);
        JSONArray json = new JSONArray();
        for (Object o : query.list()) {
            Object[] objects = (Object[]) o;
            JSONObject jo = new JSONObject();
            jo.put("survey_type", (String) objects[0]);
            jo.put("tab", (String) objects[1]);
            json.add(jo);
        }
        session.close();
        return "{\"dataList\":" + json.toJSONString() + "}";
    }

    @Override
    public String getCustType(String type) throws SQLException {
        Session session = sessionFactory.openSession();
        String sql = "SELECT PID,PNAME FROM T_P_CODE t WHERE T.PTYPE=?";
        Query query = session.createSQLQuery(sql)
                .setParameter(0, type)
                .addScalar("PID", StandardBasicTypes.STRING)
                .addScalar("PNAME", StandardBasicTypes.STRING);
        JSONArray json = new JSONArray();
        for (Object o : query.list()) {
            Object[] objects = (Object[]) o;
            JSONObject jo = new JSONObject();
            jo.put("PID", (String) objects[0]);
            jo.put("PNAME", (String) objects[1]);
            json.add(jo);
        }
        session.close();
        return "{\"dataList\":" + json.toJSONString() + "}";
    }

    @Override
    public String getSurveyCol(String tab) throws SQLException {
        String sql = "SELECT COL,COL_NAME FROM T_SURVEY_COL t WHERE T.TYPE_ID=?";
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery(sql)
                .setParameter(0, tab)
                .addScalar("COL", StandardBasicTypes.STRING)
                .addScalar("COL_NAME", StandardBasicTypes.STRING);
        JSONArray json = new JSONArray();
        for (Object o : query.list()) {
            Object[] objects = (Object[]) o;
            JSONObject jo = new JSONObject();
            jo.put("COL", (String) objects[0]);
            jo.put("COL_NAME", (String) objects[1]);
            json.add(jo);
        }
        session.close();
        return "{\"dataList\":" + json.toJSONString() + "}";
    }

    @Override
    public String getSurveyData(String typeId, String orgid, String custType, String city, String address, int dist) throws SQLException {
        //获取省份名称
        String orgname = getOrgname(orgid);
        //获取城市名称
        String cityname = getOrgname(city);

        Session session = sessionFactory.openSession();
        String sql = "select max(DECODE(IS_PROV, 1, COL)) prv,max(DECODE(IS_CITY, 1, COL)) ct,max(DECODE(IS_CUSTTYPE, 1, COL)) cust,max(DECODE(IS_ORDER, 1, COL)) ord,max(DECODE(IS_ADDR, 1, COL)) addr,MAX(F.OPEN_LAT_LNG) ff "+
                "from T_SIMPLE_COL t,T_SURVEY_TYPE f WHERE t.TYPE_ID = ? AND T.TYPE_ID=F.TYPE_ID AND (IS_PROV = 1 OR IS_CITY = 1 OR IS_CUSTTYPE = 1)";
        Query query = session.createSQLQuery(sql)
                .setParameter(0, typeId)
                .addScalar("prv", StandardBasicTypes.STRING)
                .addScalar("ct", StandardBasicTypes.STRING)
                .addScalar("cust", StandardBasicTypes.STRING)
                .addScalar("ord", StandardBasicTypes.STRING)
                .addScalar("addr", StandardBasicTypes.STRING)
                .addScalar("ff", StandardBasicTypes.STRING);
        List colObj = query.list();
        StringBuilder tick = new StringBuilder();
        String[] colName=new String[]{"","","","","",""};
        for (Object o : colObj) {
            Object[] objects = (Object[]) o;
            colName[0]=(String) objects[0];//省字段
            colName[1]=(String) objects[1];//市字段
            colName[2]=(String) objects[2];//客户类型字段
            colName[3]=(String) objects[3];//数据排序字段
            colName[4]=(String) objects[4];//地址字段
            colName[5]=(String) objects[5];//是否开启经纬度
        }
        if (!"".equals(city) && null != city) {
            tick.append(" AND EXISTS (SELECT 1 FROM T_ORG O WHERE O.ORGID='" + city + "' AND A."+colName[1]+" LIKE '%'||O.ORGNAME||'%') ");
        }
        if (!"".equals(custType) && null != custType) {
            tick.append(" AND EXISTS (SELECT 1 FROM T_P_CODE P WHERE P.PID='" + custType + "' AND A."+colName[2]+" LIKE '%'||P.PNAME||'%') ");
        }


//        System.out.println("省份：" + orgname + "城市：" + cityname + "搜索地址：" + address + "搜索半径：" + dist);
        String arsql = "";
        boolean flag = false;
        TCityLocationEntity2 cityentity = new TCityLocationEntity2();
        if (!"".equals(address)&&"1".equals(colName[5])) {//地址搜索并且开启了经纬度
            flag = true;
            //声明ak
            String ak = "xh2XppvAc5uB36HDZHKTOMnV3gSUULTb";
            if (!"".equals(cityname) && !address.contains(cityname)) address = cityname + address;
            if (!address.contains(orgname)) address = orgname + address;

            Map<String, Object> map1 = GeocodingTools2.getLatAndLngByAddress(address, ak);
            cityentity = (TCityLocationEntity2) map1.get("entity");

            //获取地址附近坐标范围
            double[] locaion = MapUtil.GetAround(cityentity.getLat(), cityentity.getLng(), dist);
//            System.out.println("拼接后搜索地址：" + address);
            //minLat, minLng, maxLat, maxLng;
//            System.out.println("距离范围，最小经度：" + locaion[1] + "，最大经度：" + locaion[3] + "，最小为纬度：" + locaion[0] + "，最大纬度：" + locaion[2]);
            arsql = " AND LNG BETWEEN " + locaion[1] + " AND " + locaion[3]
                    + " AND LAT BETWEEN " + locaion[0] + " AND " + locaion[2];
        }
        JSONArray json = new JSONArray();
        sql = "SELECT S.SHOW_NUM,T.COL FROM T_SURVEY_TYPE S,T_SURVEY_COL t WHERE S.TYPE_ID=T.TYPE_ID AND S.TYPE_ID=?";
        query = session.createSQLQuery(sql)
                .setParameter(0, typeId)
                .addScalar("SHOW_NUM", StandardBasicTypes.INTEGER)
                .addScalar("COL", StandardBasicTypes.STRING);
        colObj = query.list();
        StringBuilder col = new StringBuilder();
        int showNumber = 0;
        for (Object o : colObj) {
            Object[] objects = (Object[]) o;
            if (showNumber == 0) {
                showNumber = Integer.parseInt(objects[0].toString());
            }
            col.append(",");
            col.append((String) objects[1]);
        }
        SQLQuery sq = null;
        if ("".equals(address)) {
            if ("".equals(orgid)) {
                sql = "SELECT ROWVAL,LNG,LAT" + col.toString() + " FROM (SELECT A.ROWID ROWVAL,LNG,LAT" + col.toString() + ",ROW_NUMBER() OVER(ORDER BY DBMS_RANDOM.RANDOM) RN FROM T_SURVEY A "
                        + "WHERE A.TYPE_ID =? AND NOT EXISTS(SELECT 1 FROM T_SURVEY_INVITE F WHERE F.TYPE_ID=A.TYPE_ID AND F.ROWVAL=A.ROWID AND F.IN_FLAG=1)"
                        + tick+ " ) Q WHERE Q.RN<="+showNumber;
                sq = session.createSQLQuery(sql)
                        .setParameter(0, typeId);
            } else {
                if(!"".equals(colName[0]) && null != colName[0]) {
                    sql = "SELECT ROWVAL,LNG,LAT"+ col.toString() +" FROM(SELECT A.ROWID ROWVAL,LNG,LAT" + col.toString() + ",ROW_NUMBER() OVER(ORDER BY DBMS_RANDOM.RANDOM) RN FROM T_SURVEY A,T_ORG B "
                        + "WHERE "+colName[0]+" LIKE '%'||B.ORGNAME||'%' " + tick + " AND B.ORGID=?"
                        + " AND A.TYPE_ID =? AND NOT EXISTS(SELECT 1 FROM T_SURVEY_INVITE F WHERE F.TYPE_ID=A.TYPE_ID AND F.ROWVAL=A.ROWID AND F.IN_FLAG=1) ) Q WHERE Q.RN<="+showNumber;
                    sq = session.createSQLQuery(sql)
                            .setParameter(0, orgid)
                            .setParameter(1, typeId);
                }else{
                    sql = "SELECT ROWVAL,LNG,LAT"+ col.toString() +" FROM(SELECT A.ROWID ROWVAL,LNG,LAT" + col.toString() + ",ROW_NUMBER() OVER(ORDER BY DBMS_RANDOM.RANDOM) RN FROM T_SURVEY A "
                            + "WHERE A.TYPE_ID =? AND NOT EXISTS(SELECT 1 FROM T_SURVEY_INVITE F WHERE F.TYPE_ID=A.TYPE_ID AND F.ROWVAL=A.ROWID AND F.IN_FLAG=1) " + tick + " ) Q WHERE Q.RN<="+showNumber;
                    sq = session.createSQLQuery(sql)
                            .setParameter(0, 'A');
                }
            }
        }else {
            if(!"1".equals(colName[5])){//没有开启经纬度查询，直接字符串匹配
                arsql=" AND "+colName[4]+" LIKE '%"+address+"%' ";
            }
            if ("".equals(orgid)) {
                sql = "SELECT A.ROWID ROWVAL,LNG,LAT" + col.toString() + " FROM T_SURVEY A "
                        + "WHERE A.TYPE_ID =? AND NOT EXISTS(SELECT 1 FROM T_SURVEY_INVITE F WHERE F.TYPE_ID=A.TYPE_ID AND F.ROWVAL=A.ROWID AND F.IN_FLAG=1) "
                        + tick
                        + arsql + " ORDER BY DBMS_RANDOM.RANDOM";
                sq = session.createSQLQuery(sql)
                        .setParameter(0, typeId);
            } else {
                if(!"".equals(colName[0]) && null != colName[0]) {
                    sql = "SELECT A.ROWID ROWVAL,LNG,LAT" + col.toString() + " FROM T_SURVEY A,T_ORG B "
                            + "WHERE "+colName[0]+" LIKE '%'||B.ORGNAME||'%'" + tick + " AND B.ORGID=?"
                            + " AND A.TYPE_ID =? AND NOT EXISTS(SELECT 1 FROM T_SURVEY_INVITE F WHERE F.TYPE_ID=A.TYPE_ID AND F.ROWVAL=A.ROWID AND F.IN_FLAG=1)"
                            + arsql + " ORDER BY DBMS_RANDOM.RANDOM";
                    sq = session.createSQLQuery(sql)
                            .setParameter(0, orgid)
                            .setParameter(1, typeId);
                }
                else{
                    sql = "SELECT A.ROWID ROWVAL,LNG,LAT" + col.toString() + " FROM T_SURVEY A "
                            + "WHERE A.TYPE_ID =? AND NOT EXISTS(SELECT 1 FROM T_SURVEY_INVITE F WHERE F.TYPE_ID=A.TYPE_ID AND F.ROWVAL=A.ROWID AND F.IN_FLAG=1)"
                            + arsql+tick + " ORDER BY DBMS_RANDOM.RANDOM";
                    sq = session.createSQLQuery(sql)
                            .setParameter(0, 'A');
                }
            }
        }
        sq.addScalar("ROWVAL", StandardBasicTypes.STRING)
                .addScalar("LNG", StandardBasicTypes.DOUBLE)
                .addScalar("LAT", StandardBasicTypes.DOUBLE);
        for (Object o : colObj) {
            Object[] objects = (Object[]) o;
            sq.addScalar((String) objects[1], StandardBasicTypes.STRING);
        }
        List<Object> list = sq.list();
        System.out.println("执行的SQL语句为"+sql);
        int k = 0;
        for (Object o : list) {
            JSONObject jo = new JSONObject();
            Object[] objects = (Object[]) o;
            jo.put("ROWVAL", (String) objects[0]);
            Double lng = (Double) objects[1];
            Double lat = (Double) objects[2];
            jo.put("LNG", lng);
            jo.put("LAT", lat);
            if (flag) {
                //获取拿出的数据与搜索的地址距离
                Double aadistance = MapUtil.getDistance(lng, lat, cityentity.getLng(), cityentity.getLat());
                //满足半径距离
                if (aadistance <= dist) {
                    for (int i = 0; i < colObj.size(); i++) {
                        Object[] colO = (Object[]) colObj.get(i);
                        jo.put((String) colO[1], (String) objects[i + 3]);
                    }
                    json.add(jo);
                    k++;
                }
            } else {
                for (int i = 0; i < colObj.size(); i++) {
                    Object[] colO = (Object[]) colObj.get(i);
                    jo.put((String) colO[1], (String) objects[i + 3]);
                }
                json.add(jo);
                k++;
            }
            if (k >= showNumber) {
                break;
            }
        }
        session.close();
        return "{\"code\":0,\"msg\":\"\",\"count\":100,\"data\":" + json.toJSONString() + "}";
    }

    private List<Object> getObjectList(Session session, String sql, String orgid, String tab, List colObj, int start, int end) {
        SQLQuery sq = session.createSQLQuery(sql)
                .setParameter(0, orgid)
                .setParameter(1, tab)
                .setParameter(2, tab)
                .addScalar("ROWVAL", StandardBasicTypes.STRING)
                .addScalar("LNG", StandardBasicTypes.DOUBLE)
                .addScalar("LAT", StandardBasicTypes.DOUBLE)
                .addScalar("SHOW_NUM", StandardBasicTypes.INTEGER);
        for (Object o : colObj) {
            sq.addScalar((String) o, StandardBasicTypes.STRING);
        }

        sq.addScalar("RANDOM_VAL", StandardBasicTypes.INTEGER);
        sq.setFirstResult(start);
        sq.setMaxResults(end);
        return sq.list();
    }

    @Override
    public String getSurveyInvData(String typeId, String custType, String city, String userid, Integer start, Integer end) throws SQLException {
        JSONArray json = new JSONArray();
        Session session = sessionFactory.openSession();
        String  sql = "select max(DECODE(IS_PROV, 1, COL)) prv,max(DECODE(IS_CITY, 1, COL)) ct,max(DECODE(IS_CUSTTYPE, 1, COL)) cust,max(DECODE(IS_ORDER, 1, COL)) ord,max(DECODE(IS_ADDR, 1, COL)) addr,MAX(F.OPEN_LAT_LNG) ff "+
                "from T_SIMPLE_COL t,T_SURVEY_TYPE f WHERE t.TYPE_ID = ? AND T.TYPE_ID=F.TYPE_ID AND (IS_PROV = 1 OR IS_CITY = 1 OR IS_CUSTTYPE = 1)";
        Query query = session.createSQLQuery(sql)
                .setParameter(0, typeId)
                .addScalar("prv", StandardBasicTypes.STRING)
                .addScalar("ct", StandardBasicTypes.STRING)
                .addScalar("cust", StandardBasicTypes.STRING)
                .addScalar("ord", StandardBasicTypes.STRING)
                .addScalar("addr", StandardBasicTypes.STRING)
                .addScalar("ff", StandardBasicTypes.STRING);
        List colObj = query.list();
        StringBuilder tick = new StringBuilder();
        String[] colName=new String[]{"","","","","",""};
        for (Object o : colObj) {
            Object[] objects = (Object[]) o;
            colName[0]=(String) objects[0];//省字段
            colName[1]=(String) objects[1];//市字段
            colName[2]=(String) objects[2];//客户类型字段
            colName[3]=(String) objects[3];//数据排序字段
            colName[4]=(String) objects[4];//地址字段
            colName[5]=(String) objects[5];//是否开启经纬度
        }
        if (!"".equals(city) && null != city) {
            tick.append(" AND EXISTS (SELECT 1 FROM T_ORG O WHERE O.ORGID='" + city + "' AND A."+colName[1]+" LIKE '%'||O.ORGNAME||'%') ");
        }
        if (!"".equals(custType) && null != custType) {
            tick.append(" AND EXISTS (SELECT 1 FROM T_P_CODE P WHERE P.PID='" + custType + "' AND A."+colName[2]+" LIKE '%'||P.PNAME||'%') ");
        }
        sql = "SELECT T.COL FROM T_SURVEY_COL t WHERE T.TYPE_ID=?";
        query = session.createSQLQuery(sql)
                .setParameter(0, typeId)
                .addScalar("COL", StandardBasicTypes.STRING);
        colObj = query.list();
        StringBuilder col = new StringBuilder();
        for (Object o : colObj) {
            col.append((String) o);
            col.append(",");
        }
        sql = "SELECT ROWVAL,SF_DIS,TOTAL,LNG,LAT," + col + "ROWXH FROM(SELECT A.ROWID ROWVAL,A.LNG,A.LAT,CASE WHEN F.DISTRI IS NULL OR F.DISTRI='2' THEN '未分配' ELSE (SELECT S_USER_NAME FROM T_SURVEY_USER WHERE S_USER_ID=F.DIS_RM) END SF_DIS,count(1) over() total," + col + "ROWNUM rowxh FROM T_SURVEY A,T_SURVEY_INVITE F "
                + "WHERE F.TYPE_ID=? AND F.USERID=? AND F.ROWVAL=A.ROWID AND F.IN_FLAG=1 " + tick
                + " ) T WHERE T.ROWXH>" + start + " AND T.ROWXH <=" + end;
        SQLQuery sq = session.createSQLQuery(sql)
                .setParameter(0, typeId)
                .setParameter(1, userid)
                .addScalar("ROWVAL", StandardBasicTypes.STRING)
                .addScalar("SF_DIS", StandardBasicTypes.STRING)
                .addScalar("TOTAL", StandardBasicTypes.INTEGER)
                .addScalar("LNG", StandardBasicTypes.DOUBLE)
                .addScalar("LAT", StandardBasicTypes.DOUBLE);
        for (Object o : colObj) {
            sq.addScalar((String) o, StandardBasicTypes.STRING);
        }
        sq.addScalar("ROWXH", StandardBasicTypes.INTEGER);
        int total = 0;
        for (Object o : sq.list()) {
            JSONObject jo = new JSONObject();
            Object[] objects = (Object[]) o;
            jo.put("ROWVAL", (String) objects[0]);
            jo.put("SF_DIS", (String) objects[1]);
            Double lng = (Double) objects[3];
            Double lat = (Double) objects[4];
            jo.put("LNG", lng);
            jo.put("LAT", lat);
            if (total == 0) {
                total = (int) objects[2];
            }
            for (int i = 0; i < colObj.size(); i++) {
                jo.put((String) colObj.get(i), (String) objects[i + 5]);
            }
            json.add(jo);
        }
        session.close();
        return "{\"code\":0,\"msg\":\"\",\"count\":" + total + ",\"data\":" + json.toJSONString() + "}";
    }

    @Override
    public void saveInvit(String tab, String rowv, String invRes, String faultRes, String userid) throws SQLException {
        String[] rw = rowv.split(",");
        String sql = "INSERT INTO T_SURVEY_INVITE(TYPE_ID, ROWVAL, USERID, IN_FLAG, FAULT_CODE) VALUES(?,?,?,?,?)";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(sql);
        for (int i = 0; i < rw.length; i++) {
            if ("".equals(rw[i])) {
                break;
            }
            query.setParameter(0, tab);
            query.setParameter(1, rw[i]);
            query.setParameter(2, userid);
            query.setParameter(3, invRes);
            query.setParameter(4, faultRes);
            query.executeUpdate();
        }
        transaction.commit();
        session.close();
    }

    @Override
    public void saveDist(String tab, String rowv, String distRes, String diaocy) throws SQLException {
        String[] rw = rowv.split(",");
        String sql = "UPDATE T_SURVEY_INVITE SET DISTRI=?,DIS_RM=? WHERE TYPE_ID=? AND ROWVAL=?";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(sql);
        for (int i = 0; i < rw.length; i++) {
            if ("".equals(rw[i])) {
                break;
            }
            query.setParameter(0, distRes);
            query.setParameter(1, diaocy);
            query.setParameter(2, tab);
            query.setParameter(3, rw[i]);
            query.executeUpdate();
        }
        transaction.commit();
        session.close();
    }

    @Override
    public String getSurveyUser(String userid) throws SQLException {
        Session session = sessionFactory.openSession();
        String sql = "SELECT T.S_USER_ID,T.S_USER_NAME FROM T_SURVEY_USER T WHERE T.USERID=?";
        Query query = session.createSQLQuery(sql)
                .setParameter(0, userid)
                .addScalar("S_USER_ID", StandardBasicTypes.STRING)
                .addScalar("S_USER_NAME", StandardBasicTypes.STRING);
        JSONArray json = new JSONArray();
        for (Object o : query.list()) {
            Object[] objects = (Object[]) o;
            JSONObject jo = new JSONObject();
            jo.put("S_USER_ID", (String) objects[0]);
            jo.put("S_USER_NAME", (String) objects[1]);
            json.add(jo);
        }
        session.close();
        return "{\"dataList\":" + json.toJSONString() + "}";
    }

    @Override
    public String getOrgname(String orgid) {
        String orgname = "";
        String sql = "SELECT ORGNAME FROM T_ORG  O WHERE O.ORGID = ?";
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery(sql)
                .setParameter(0, orgid)
                .addScalar("ORGNAME", StandardBasicTypes.STRING);
        JSONArray json = new JSONArray();
        for (Object o : query.list()) {
            orgname = o.toString();
        }
        session.close();
        return orgname;
    }

    @Override
    public void deleteByTypeId(String typeId) {
        String sql = "DELETE FROM T_SURVEY A WHERE A.TYPE_ID = ? ";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(sql);
        query.setParameter(0, typeId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public Map<String, Object> getDistance(String city, String addr) {
        Map<String, Object> map = new HashMap<String, Object>();
        Double distance = null;
        String ak = "xh2XppvAc5uB36HDZHKTOMnV3gSUULTb";
        Map<String, Object> map1;
        Map<String, Object> map2;
        map1 = GeocodingTools2.getLatAndLngByAddress(city, ak);
        map2 = GeocodingTools2.getLatAndLngByAddress(addr, ak);
        TCityLocationEntity2 cityentity = (TCityLocationEntity2) map1.get("entity");
        TCityLocationEntity2 addrentity = (TCityLocationEntity2) map2.get("entity");
        if (cityentity.getCode().equals("0") || addrentity.getCode().equals("0")) {
            System.out.println("获取地址坐标失败请重新输入");
        } else if (map1.get("status").equals("302") || map2.get("status").equals("302")) {
            System.out.println("请求配额已用完");
        } else {
            distance = MapUtil.getDistance(addrentity.getLng(), addrentity.getLat(), cityentity.getLng(), cityentity.getLat());
        }
//        System.out.println("此次距离"+addrentity.getLng()+addrentity.getLat()+"-"+cityentity.getLng()+cityentity.getLat()+distance);
        map.put("entity", addrentity);
        map.put("distance", distance);
        return map;
    }

}
