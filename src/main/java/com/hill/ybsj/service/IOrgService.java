package com.hill.ybsj.service;

import java.util.Map;

import com.hill.ybsj.po.TOrgEntity;

public interface IOrgService {
public Map<String,Object> findAll( int currentTotal,int current, String porgid) throws Exception;
public Map<String,Object> findOne(TOrgEntity orgentity) throws Exception ;
public Map<String, Object> update(TOrgEntity orgentity) throws Exception;
public Map<String, Object> delete(TOrgEntity orgentity) throws Exception;
public Map<String, Object> add(TOrgEntity orgentity) throws Exception;
public Map<String, Object> checkId(String oid);
public Map<String, Object> showporglist() throws Exception;
public Map<String,Object> showcitylist(String porgid);
}
