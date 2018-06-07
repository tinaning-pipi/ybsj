package com.hill.ybsj.dao.impl;

import com.hill.ybsj.dao.SimpleColDao;
import com.hill.ybsj.po.TSimpleColEntity;
import com.hill.ybsj.po.TSurveyTypeEntity;
import net.minidev.json.JSONObject;
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
 * 专项字段Dao层实现类
 *
 * @author Administrator
 * @create 2018-06-03 10:33
 */
@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW )
public class SimpleColDaoImpl implements SimpleColDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<TSimpleColEntity> findBytypeId(String typeId) {
        Session session = sessionFactory.openSession();
        String hql;
        hql="FROM TSimpleColEntity f where f.stypeId = ? order by f.ordernum asc";
        Query query = session.createQuery(hql);
        query.setParameter(0,typeId);
        List<TSimpleColEntity> list = query.list();
        session.close();
        return list;
    }

    @Override
    public boolean find(TSimpleColEntity tSimpleColEntity) {
        boolean flag=false;
        Session session = sessionFactory.openSession();
        String hql;
        hql="FROM TSimpleColEntity f  where  f.col = ? and f.stypeId = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,tSimpleColEntity.getCol());
        query.setParameter(1,tSimpleColEntity.getStypeId());
        List<TSimpleColEntity> list = query.list();
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
    public void add(TSimpleColEntity tSimpleColEntity) {
        hibernateTemplate.save(tSimpleColEntity);
    }

    @Override
    public Long getColSize(TSimpleColEntity tSimpleColEntity) {
        Long k=(long)0;
        Session session = sessionFactory.openSession();
        String hql;
        hql="FROM TSimpleColEntity f  where  f.stypeId = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,tSimpleColEntity.getStypeId());
            k=new Long(query.list().size());
        session.close();
        return  k;
    }

    @Override
    public Long getMax(TSimpleColEntity tSimpleColEntity) {
        Long k=(long)0;
        Session session = sessionFactory.openSession();
        String hql;
        hql="select max(f.ordernum) FROM TSimpleColEntity f  where  f.stypeId = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,tSimpleColEntity.getStypeId());
        if(query.list().size()>0&&query.list().get(0)!=null){
            k=new Long(Integer.parseInt(query.list().get(0).toString()));
        }
        session.close();
        return  k;
    }

    @Override
    public TSimpleColEntity findOne(String col) {
            return hibernateTemplate.get(TSimpleColEntity.class,col);
    }

    @Override
    public void delete(TSimpleColEntity tSimpleColEntity) {
        Session session = sessionFactory.openSession();
        String hql;
        hql=" DELETE  TSimpleColEntity as f  where  f.stypeId = ? and  f.col = ?";
        Transaction tx=session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter(0,tSimpleColEntity.getStypeId());
        query.setParameter(1,tSimpleColEntity.getCol());
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void update(TSimpleColEntity result,String hecol) {
        System.out.println("修改的主题数据为：---"+result.getIsprov()+"-"+result.getIscity());
        Session session = sessionFactory.openSession();
        String hql;
        hql=" UPDATE TSimpleColEntity t set t.stypeId = ?, t.col = ?,t.colname = ?,t.ordernum = ?,t.isprov = ?,t.iscity = ?,t.iscusttype = ?,t.isorder = ?,t.isaddr = ? WHERE t.col = ? AND t.stypeId = ?";
        Transaction tx=session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter(0,result.getStypeId());
        query.setParameter(1,result.getCol());
        query.setParameter(2,result.getColname());
        query.setParameter(3,result.getOrdernum());
        query.setParameter(4,result.getIsprov());
        query.setParameter(5,result.getIscity());
        query.setParameter(6,result.getIscusttype());
        query.setParameter(7,result.getIsorder());
        query.setParameter(8,result.getIsaddr());
        query.setParameter(9,hecol);
        query.setParameter(10,result.getStypeId());
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void deleteByTypeId(String typeId) {
        Session session = sessionFactory.openSession();
        String hql;
        hql=" DELETE  TSimpleColEntity as f  where  f.stypeId = ?";
        Transaction tx=session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter(0,typeId);
        query.executeUpdate();
        tx.commit();
        session.close();

    }
}
