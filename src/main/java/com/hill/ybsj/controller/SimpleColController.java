package com.hill.ybsj.controller;

import com.hill.ybsj.po.TSimpleColEntity;
import com.hill.ybsj.service.SimpleColService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * 专项维护控制类
 *
 * @author Administrator
 * @create 2018-05-30 20:05
 */
@Controller
public class SimpleColController extends ActionSupport {
    @Autowired
    private SimpleColService simpleColService;
    private String stypeId;
    private String hecol;
    private TSimpleColEntity tSimpleColEntity;
    //json数据
    private Map<String, Object> dataMap;
    //分页
    private int currentTotal, current;

    public String getStypeId() {
        return stypeId;
    }

    public void setStypeId(String stypeId) {
        this.stypeId = stypeId;
    }

    public String getHecol() {
        return hecol;
    }

    public void setHecol(String hecol) {
        this.hecol = hecol;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
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

    public TSimpleColEntity gettSimpleColEntity() {
        return tSimpleColEntity;
    }

    public void settSimpleColEntity(TSimpleColEntity tSimpleColEntity) {
        this.tSimpleColEntity = tSimpleColEntity;
    }

    public String findBytypeId(){
        dataMap=simpleColService.findBytypeId(stypeId);
        return  ActionSupport.SUCCESS;
    }
    public String add() throws Exception {
        dataMap = simpleColService.add(tSimpleColEntity);
        return ActionSupport.SUCCESS;
    }
    public String delete() throws Exception {
        dataMap = simpleColService.delete(tSimpleColEntity);
        return ActionSupport.SUCCESS;
    }
    public String findOne() throws Exception {
        dataMap = simpleColService.findOne(tSimpleColEntity);
        return ActionSupport.SUCCESS;
    }
    public String update() throws Exception {
        dataMap = simpleColService.update(tSimpleColEntity,hecol);
        return ActionSupport.SUCCESS;
    }
    public String getMaxcol(){
        dataMap = simpleColService.getMaxcol(tSimpleColEntity);
        return ActionSupport.SUCCESS;
    }

}
