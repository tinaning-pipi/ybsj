package com.hill.ybsj.dao;

import com.hill.ybsj.po.TSurveyTypeEntity;

import java.util.List;

public interface MantainConfigDao {
    List<TSurveyTypeEntity> findAll(int currentTotal, int current);

    Object findTotal();

    TSurveyTypeEntity findOne(String typeId);

    void update(TSurveyTypeEntity result);

    boolean find(TSurveyTypeEntity surveyTypeEntity);

    void add(TSurveyTypeEntity surveyTypeEntity);

    void delete(TSurveyTypeEntity surveyTypeEntity);

    void deleteSurVeyCol(TSurveyTypeEntity tSurveyTypeEntity);
}
