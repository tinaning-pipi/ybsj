package com.hill.ybsj.service;

import java.util.Map;

import com.hill.ybsj.po.TRoleFuncEntity;

public interface IRoleFuncService {
	public Map<String,Object> findAll( int currentTotal,int current,String role) throws Exception;
	public Map<String, Object> delete(TRoleFuncEntity tRoleFuncEntity) throws Exception;
	public Map<String, Object> add(TRoleFuncEntity tRoleFuncEntity) throws Exception;
	public Map<String, Object> findnofuncbyrole(String role);
}
