package com.hill.ybsj.dao.impl;

import com.hill.ybsj.dao.MantainConfigDao;
import com.hill.ybsj.po.TSurveyTypeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专项维护Dao层实现类
 *
 * @author Administrator
 * @create 2018-05-31 15:06
 */
@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW )
public class MantainConfigDaoImpl implements MantainConfigDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<TSurveyTypeEntity> findAll(int currentTotal, int current) {
        Session session = sessionFactory.openSession();
        String hql;
        hql="FROM TSurveyTypeEntity f order by TO_NUMBER(f.typeId) asc";
        Query query = session.createQuery(hql);
        query.setFirstResult(currentTotal * (current - 1));
        query.setMaxResults(currentTotal);
        List<TSurveyTypeEntity> list = query.list();
        session.close();
        return list;
    }

    @Override
    public Object findTotal() {
        Session session = sessionFactory.openSession();
        String hql;
        hql="From TSurveyTypeEntity";
        Query query = session.createQuery(hql);
        List<TSurveyTypeEntity> list = query.list();
        session.close();
        return list.size();
    }

    @Override
    public TSurveyTypeEntity findOne(String typeId) {

        return hibernateTemplate.get(TSurveyTypeEntity.class,typeId);
    }

    @Override
    public void update(TSurveyTypeEntity result) {
        hibernateTemplate.update(result);
    }

    @Override
    public boolean find(TSurveyTypeEntity surveyTypeEntity) {
        boolean flag=false;
        Session session = sessionFactory.openSession();
        String hql;
        hql="FROM TSurveyTypeEntity f  where  f.surveyType = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,surveyTypeEntity.getSurveyType());
        List<TSurveyTypeEntity> list = query.list();
        session.close();
        if(list.size()>0){
            flag=true;
        }
        else {
            flag=false;
        }
        return flag;
    }

    @Override
    public void add(TSurveyTypeEntity surveyTypeEntity) {
            hibernateTemplate.save(surveyTypeEntity);
    }

    @Override
    public void delete(TSurveyTypeEntity surveyTypeEntity) {
        hibernateTemplate.delete(surveyTypeEntity);
    }

    @Override
    public void deleteSurVeyCol(TSurveyTypeEntity tSurveyTypeEntity) {
        Session session = sessionFactory.openSession();
        String hql;
        hql=" DELETE  TSurveyColEntity as f  where  f.typeId = ?";
        Transaction tx=session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter(0,tSurveyTypeEntity.getTypeId());
        query.executeUpdate();
        tx.commit();
        session.close();
    }

}
