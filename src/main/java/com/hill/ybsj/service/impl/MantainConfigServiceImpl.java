package com.hill.ybsj.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hill.ybsj.dao.ISurveyDao;
import com.hill.ybsj.dao.MantainConfigDao;
import com.hill.ybsj.dao.SimpleColDao;
import com.hill.ybsj.po.TFuncEntity;
import com.hill.ybsj.po.TSurveyTypeEntity;
import com.hill.ybsj.service.MantainConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专项维护的Service实现类
 *
 * @author Administrator
 * @create 2018-05-31 14:11
 */
@Service
public class MantainConfigServiceImpl implements MantainConfigService {
    @Autowired
    private MantainConfigDao mantainConfigDao;
    @Autowired
    private SimpleColDao simpleColDao;
    @Autowired
    private ISurveyDao iSurveyDao;
    @Override
    public Map<String, Object> findAll(int currentTotal, int current) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;
        if (currentTotal >= 0 && current >= 0) {
            try {
                List<TSurveyTypeEntity> surveytypelist = mantainConfigDao.findAll(currentTotal, current);
                map.put("surveytypelist", surveytypelist);
                //获取数据的条数
                map.put("total", mantainConfigDao.findTotal());
                map.put("message", "");
            } catch (Exception e) {
                //获取数据异常
                error = 2;
                map.put("message", "获取数据异常");
                e.printStackTrace();
            }
        } else {
            //请求参数异常
            error = 1;
            map.put("message", "请求参数异常");
        }
        map.put("error", error);
        return map;
    }

    @Override
    public Map<String, Object> findOne(TSurveyTypeEntity surveyTypeEntity) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;

        if (surveyTypeEntity != null && surveyTypeEntity.getTypeId() != null && !"".equals(surveyTypeEntity.getTypeId())) {
            try {
                Map<String, Object> fm = new HashMap();
                TSurveyTypeEntity surveyTypeEntity1 = mantainConfigDao.findOne(surveyTypeEntity.getTypeId());
                fm.put("typeId", surveyTypeEntity1.getTypeId());
                fm.put("surveyType", surveyTypeEntity1.getSurveyType());
                fm.put("showNum", surveyTypeEntity1.getShowNum());
                fm.put("openlnla", surveyTypeEntity1.getOpenlnla());
                fm.put("colNum", surveyTypeEntity1.getColNum());
                map.put("surveyTypeEntity", fm);
            } catch (Exception e) {
                //获取失败
                error = 2;
                map.put("message", "请求数据失败");
            }
        } else {
            error = 1;
            map.put("message", "请求参数异常");
        }
        map.put("error", error);
        return map;
    }

    @Override
    public Map<String, Object> update(TSurveyTypeEntity surveyTypeEntity) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;
        if (surveyTypeEntity != null && surveyTypeEntity.getTypeId() != null && surveyTypeEntity.getShowNum() != null && surveyTypeEntity.getSurveyType() != null && surveyTypeEntity.getSurveyType()!=null&& surveyTypeEntity.getColNum()!=null&& surveyTypeEntity.getOpenlnla()!=null&& !"".equals(surveyTypeEntity.getTypeId()) && !"".equals(surveyTypeEntity.getSurveyType()) && !"".equals(surveyTypeEntity.getOpenlnla())&&!"".equals(surveyTypeEntity.getShowNum())&&!"".equals(surveyTypeEntity.getColNum())) {
            try {
                TSurveyTypeEntity result = mantainConfigDao.findOne(surveyTypeEntity.getTypeId());
                if (result != null) {
                    result.setTypeId(surveyTypeEntity.getTypeId());
                    result.setShowNum(surveyTypeEntity.getShowNum());
                    result.setColNum(surveyTypeEntity.getColNum());
                    result.setSurveyType(surveyTypeEntity.getSurveyType());
                    result.setOpenlnla(surveyTypeEntity.getOpenlnla());
                    mantainConfigDao.update(result);
                    map.put("message", "修改成功");
                } else {
                    //修改失败
                    error = 3;
                    map.put("message", "修改失败");
                }

            } catch (Exception e) {
                //修改失败
                error = 2;
                map.put("message", "修改失败");
                e.printStackTrace();
            }

        } else {
            //非法操作
            error = 1;
            map.put("message", "非法操作");
        }
        map.put("error", error);
        return map;
    }

    @Override
    public Map<String, Object> delete(TSurveyTypeEntity surveyTypeEntity) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;

        if (surveyTypeEntity != null && surveyTypeEntity.getTypeId() != null && !"".equals(surveyTypeEntity.getTypeId())) {
            try {
                TSurveyTypeEntity result = mantainConfigDao.findOne(surveyTypeEntity.getTypeId());
                if (result != null) {
                    mantainConfigDao.delete(surveyTypeEntity);
                    iSurveyDao.deleteByTypeId(surveyTypeEntity.getTypeId());
                    simpleColDao.deleteByTypeId(surveyTypeEntity.getTypeId());
                    mantainConfigDao.deleteSurVeyCol(surveyTypeEntity);
                    map.put("message", "删除成功");
                } else {
                    //修改失败
                    error = 3;
                    map.put("message", "删除失败");
                }

            } catch (Exception e) {
                //修改失败
                error = 2;
                map.put("message", "数据异常");
                e.printStackTrace();
            }

        } else {
            //非法操作
            error = 1;
            map.put("message", "非法操作");
        }
        map.put("error", error);
        return map;
    }

    @Override
    public Map<String, Object> checkfId(String cId) {
        Map<String, Object> map = new HashMap<>();
        if (cId != null && !"".equals(cId)) {
            try {
                TSurveyTypeEntity result = mantainConfigDao.findOne(cId);
                if (result != null) {
                    map.put("message", "0");
                } else {
                    map.put("message", "1");
                }

            } catch (Exception e) {
                //添加失败

                map.put("message", "");
                e.printStackTrace();
            }

        } else {
            //非法操作
            map.put("message", "非法操作");
        }

        return map;
    }

    @Override
    public Map<String, Object> add(TSurveyTypeEntity surveyTypeEntity) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;
        if (surveyTypeEntity != null && surveyTypeEntity.getTypeId() != null && surveyTypeEntity.getShowNum() != null && surveyTypeEntity.getSurveyType() != null && surveyTypeEntity.getSurveyType()!=null&& surveyTypeEntity.getColNum()!=null&& surveyTypeEntity.getOpenlnla()!=null&& !"".equals(surveyTypeEntity.getTypeId()) && !"".equals(surveyTypeEntity.getSurveyType()) && !"".equals(surveyTypeEntity.getOpenlnla())&&!"".equals(surveyTypeEntity.getShowNum())&&!"".equals(surveyTypeEntity.getColNum())) {
            try {
                boolean flag = mantainConfigDao.find(surveyTypeEntity);
                if (flag == false) {
                    mantainConfigDao.add(surveyTypeEntity);
                    map.put("message", "添加成功");
                }
                else {
                    //添加失败
                    error = 3;
                    map.put("message", "该专项已经存在请重新添加");
                }

            } catch (Exception e) {
                //添加失败
                error = 2;
                map.put("message", "添加失败");
                e.printStackTrace();
            }

        } else {
            //非法操作
            error = 1;
            map.put("message", "非法操作");
        }
        map.put("error", error);
        return map;
    }
}
