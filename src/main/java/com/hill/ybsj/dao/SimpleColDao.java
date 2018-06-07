package com.hill.ybsj.dao;

import com.hill.ybsj.po.TSimpleColEntity;

import java.util.List;

public interface SimpleColDao {
    List<TSimpleColEntity> findBytypeId(String typeId);

    boolean find(TSimpleColEntity tSimpleColEntity);

    void add(TSimpleColEntity tSimpleColEntity);

    Long getColSize(TSimpleColEntity tSimpleColEntity);

    Long getMax(TSimpleColEntity tSimpleColEntity);

    TSimpleColEntity findOne(String col);

    void delete(TSimpleColEntity tSimpleColEntity);

    void update(TSimpleColEntity result,String hecol);

    void deleteByTypeId(String typeId);
}
