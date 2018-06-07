package com.hill.ybsj.controller;

import com.hill.ybsj.po.TSimpleColEntity;
import com.hill.ybsj.po.TSurveyTypeEntity;
import com.hill.ybsj.service.MantainConfigService;
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
public class MantainConfigController extends ActionSupport {
    @Autowired
    private MantainConfigService mantainConfigService;
    //获取请求中传过来的surveyTypeEntity对象

    private TSurveyTypeEntity surveyTypeEntity;
    //用于检查新增功能是否的存在的id
    private String cId;
    //json数据
    private Map<String, Object> dataMap;
    //分页
    private int currentTotal, current;

    public TSurveyTypeEntity getSurveyTypeEntity() {
        return surveyTypeEntity;
    }

    public void setSurveyTypeEntity(TSurveyTypeEntity surveyTypeEntity) {
        this.surveyTypeEntity = surveyTypeEntity;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
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

    public String showConfigList() throws Exception {
        return ActionSupport.SUCCESS;
    }

    /**
     * 分页查询专项维护列表
     * @return
     */
    public String findAll(){
        dataMap = mantainConfigService.findAll(currentTotal, current);
        return ActionSupport.SUCCESS;
    }
    /**
     * 添加专项
     */
    public String add() throws Exception {
        /**
         * 新增专项默认字段列数为0
         */
        surveyTypeEntity.setColNum((long)0);
        dataMap = mantainConfigService.add(surveyTypeEntity);
        return ActionSupport.SUCCESS;
    }

    /**
     * 查询单个专项的信息
     * @return
     * @throws Exception
     */
    public String findOne() throws Exception {
        dataMap = mantainConfigService.findOne(surveyTypeEntity);
        return ActionSupport.SUCCESS;
    }

    /**
     * 修改专项信息
     * @return
     * @throws Exception
     */
    public String update() throws Exception {
        dataMap = mantainConfigService.update(surveyTypeEntity);
        return ActionSupport.SUCCESS;
    }

    /**
     * 删除专项
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        dataMap = mantainConfigService.delete(surveyTypeEntity);
        return ActionSupport.SUCCESS;
    }

    /**
     * 专项id异步校验
     * @return
     */
    public String checkfId() {
        dataMap = mantainConfigService.checkfId(cId);
        return ActionSupport.SUCCESS;
    }
}
