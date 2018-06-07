package com.hill.ybsj.service;

import java.util.Map;

import com.hill.ybsj.po.TFuncEntity;

public interface IFuncService {
public Map<String,Object> findFuncAll( int currentTotal,int current) throws Exception;
public Map<String,Object> findOne(TFuncEntity funcentity) throws Exception ;
public Map<String, Object> update(TFuncEntity funcentity) throws Exception;
public Map<String, Object> delete(TFuncEntity funcentity) throws Exception;
public Map<String, Object> add(TFuncEntity funcentity) throws Exception;
public Map<String, Object> checkfId(String fId);
}
