package com.hill.ybsj.dao.impl;

import com.hill.ybsj.dao.ISurveyorDao;
import com.hill.ybsj.po.TOrgEntity;
import com.hill.ybsj.po.TSurveyUserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class SurveyorDaoImpl implements ISurveyorDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public TSurveyUserEntity userFindOne(String userid) throws Exception {
        return hibernateTemplate.get(TSurveyUserEntity.class, userid);
    }

    @Override
    public void userAdd(TSurveyUserEntity entity) throws Exception {
        hibernateTemplate.save(entity);
    }

    @Override
    public List<TOrgEntity> getVisibleProvince(String orgid) throws Exception {
        Session session = sessionFactory.openSession();
        //1 代表管理员 2 代表供应商
        String hql = "FROM TOrgEntity WHERE pOrgid = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0, orgid);
        List<TOrgEntity> list = query.list();
        session.close();
        return list;
    }

    @Override
    public List<TSurveyUserEntity> userFindAll(int currentotal, int current, String province, String userId) throws Exception {
        Session session = sessionFactory.openSession();
        String hql;
        if (Integer.parseInt(province) < 1) {
            hql = "FROM TSurveyUserEntity WHERE userid = ?";
        } else {
            hql = "FROM TSurveyUserEntity WHERE userid = ? AND orgid = ?";
        }
        Query query = session.createQuery(hql);
        query.setParameter(0, userId);
        if (Integer.parseInt(province) >= 1) {
            query.setParameter(1, province);
        }
        query.setFirstResult(currentotal * (current - 1));
        query.setMaxResults(currentotal);
        List<TSurveyUserEntity> list = query.list();
        session.close();
        return list;
    }

    @Override
    public Integer userFindAllTotal(String province, String userId) throws Exception {
        Session session = sessionFactory.openSession();
        String hql;
        if (Integer.parseInt(province) < 1) {
            hql = "FROM TSurveyUserEntity WHERE userid = ?";
        } else {
            hql = "FROM TSurveyUserEntity WHERE userid = ? AND orgid = ?";
        }
        Query query = session.createQuery(hql);
        query.setParameter(0, userId);
        if (Integer.parseInt(province) >= 1) {
            query.setParameter(1, province);
        }
        List<TSurveyUserEntity> list = query.list();
        session.close();
        return list.size();
    }

    @Override
    public String userProvinceName(String orgid) throws Exception {
        if (orgid != null) {
            TOrgEntity orgEntity = hibernateTemplate.get(TOrgEntity.class, orgid);
            return orgEntity.getOrgname();
        } else {
            return "";
        }
    }

    @Override
    public void userDelete(TSurveyUserEntity result) {
        hibernateTemplate.delete(result);
    }

    @Override
    public void userUpdate(TSurveyUserEntity entity) {
        hibernateTemplate.update(entity);
    }

    @Override
    public Integer getDisrmNumber(TSurveyUserEntity entity) throws Exception {
        Session session = sessionFactory.openSession();
        NativeQuery sqlQuery = session.createSQLQuery("SELECT COUNT(DIS_RM) FROM T_SURVEY_INVITE WHERE DIS_RM = ?");
        sqlQuery.setParameter(0, entity.getsUserId());
        String data = Arrays.toString(sqlQuery.list().toArray());
        if (!"[]".equals(data)) {
            data = data.replace("[", "");
            data = data.replace("]", "");
            session.close();
            return Integer.parseInt(data);
        } else {
            session.close();
            return null;
        }

    }
}
