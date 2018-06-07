package com.hill.ybsj.service;

import com.hill.ybsj.po.TSimpleColEntity;

import java.util.Map;

public interface SimpleColService {
    Map<String,Object> findBytypeId(String typeId);

    Map<String,Object> add(TSimpleColEntity tSimpleColEntity);

    Map<String,Object> delete(TSimpleColEntity tSimpleColEntity);

    Map<String,Object> findOne(TSimpleColEntity tSimpleColEntity);

    Map<String,Object> update(TSimpleColEntity tSimpleColEntity,String hecol);

    Map<String,Object> getMaxcol(TSimpleColEntity stypeId);
}
