package com.hill.ybsj.service;

import com.hill.ybsj.po.TSurveyTypeEntity;

import java.util.Map;

public interface MantainConfigService {
    Map<String,Object> findAll(int currentTotal, int current);

    Map<String,Object> findOne(TSurveyTypeEntity surveyTypeEntity);

    Map<String,Object> update(TSurveyTypeEntity surveyTypeEntity);

    Map<String,Object> delete(TSurveyTypeEntity surveyTypeEntity);

    Map<String,Object> checkfId(String cId);

    Map<String,Object> add(TSurveyTypeEntity surveyTypeEntity);
}
