package com.hill.ybsj.controller;

import java.util.Map;

import com.hill.ybsj.po.TRoleFuncEntity;
import com.hill.ybsj.service.IRoleFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class RoleFuncController extends ActionSupport {
	@Autowired
	private IRoleFuncService roleFuncService;
	//json数据
	private Map<String,Object> dataMap;
	//接收角色-功能信息
	private TRoleFuncEntity tRoleFuncEntity;
	public TRoleFuncEntity gettRoleFuncEntity() {
		return tRoleFuncEntity;
	}
	public void settRoleFuncEntity(TRoleFuncEntity tRoleFuncEntity) {
		this.tRoleFuncEntity = tRoleFuncEntity;
	}
	//接收进来的角色role参数
	private String role;
	 //分页
    private int currentTotal, current;

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getCurrentTotal() {
		return currentTotal;
	}
	public void setCurrentTotal(int currentTotal) {
		this.currentTotal = currentTotal;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public Map<String,Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String,Object> dataMap) {
		this.dataMap = dataMap;
	}
	//显示角色功能界面
	public String showrolfcpage() {
		return ActionSupport.SUCCESS;
	}
	//加载角色功能列表数据
	public String findAll() throws Exception {
	   dataMap=roleFuncService.findAll(currentTotal, current, role);
	   return ActionSupport.SUCCESS;
	}
	//删除单个角色-功能映射关系
	public String delete() throws Exception{
		 dataMap=roleFuncService.delete(tRoleFuncEntity);
		  return ActionSupport.SUCCESS;
	}
	//根据据角色查找该角色当前没有的功能
	public String findnofuncbyrole() throws Exception{
		 dataMap=roleFuncService.findnofuncbyrole(role);
		 return ActionSupport.SUCCESS;
	}
	//添加角色功能
	public String add() throws Exception{
		dataMap=roleFuncService.add(tRoleFuncEntity);
		return ActionSupport.SUCCESS;
	}
}
