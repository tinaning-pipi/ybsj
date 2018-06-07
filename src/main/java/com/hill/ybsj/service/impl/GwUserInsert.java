package com.hill.ybsj.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GwUserInsert {
    private Connection con = null;
    private HttpServletRequest request;

    public GwUserInsert(HttpServletRequest request) {
        this.request = request;
    }

    private String[] getSql(String zxName) {
        String[] sql = new String[]{"", ""};
        int zdCd = Integer.parseInt((zxName.split("_"))[1]);
        StringBuilder sb=new StringBuilder();
        StringBuilder sb2=new StringBuilder();
        sb.append("INSERT INTO T_SURVEY(TYPE_ID");
        sb2.append(")VALUES('");
        sb2.append((zxName.split("_"))[0]);
        sb2.append("'");
        //拿到专项type_id,获取
        for(int i=1;i<=zdCd;i++){
            sb.append(",COL");
            sb.append(i);
            sb2.append(",?");
        }
        sql[0]=sb.append(sb2.append(")").toString()).toString();
        sql[1]=zdCd+"";
//            sql[0] = "INSERT INTO USER_95598WZ(YHBH,YHMC,TEL,PHONE,YDDZ,CXBZ,SEX,PROV)VALUES(?,?,?,?,?,?,?,?)";
//            sql[1] = "8";

        ;
        return sql;
    }

    /**
     * 插入答卷
     *
     * @param li
     */
    public void doInsert(List<List> li, String zxName) {
        try {
            SessionFactory sessionFactory = (SessionFactory) WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("sessionFactory");
            Session session = sessionFactory.openSession();
            session.doWork(connection -> con = connection);
            con.setAutoCommit(false);
            int len = li.size();
            String[] sql = getSql(zxName);
            int q = 0;
            int siz = 0;
            PreparedStatement pre = con.prepareStatement(sql[0]);
            for (int i = 0; i < len; i++) {
                siz = li.get(i).size();
                if (siz > Integer.parseInt(sql[1])) {
                    siz = Integer.parseInt(sql[1]);
                }
                for (int j = 0; j < siz; j++) {
                    pre.setString(j + 1, (li.get(i).get(j) + "").replace("'", ""));
                }
                for (int j = siz; j < Integer.parseInt(sql[1]); j++) {
                    pre.setString(j + 1, "");
                }
                pre.addBatch();
                q++;
                if (q > 5000) {
                    pre.executeBatch();
                    q = 0;
                }
                if (i % 200 == 0) {
                    con.commit();
                }
            }
            pre.executeBatch();
            con.commit();
            pre.close();
            con.setAutoCommit(true);
            session.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
