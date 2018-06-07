package com.hill.ybsj.service.impl;

import com.hill.ybsj.dao.MantainConfigDao;
import com.hill.ybsj.dao.SimpleColDao;
import com.hill.ybsj.po.TSimpleColEntity;
import com.hill.ybsj.service.SimpleColService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专项字段Service实现类
 *
 * @author Administrator
 * @create 2018-06-03 10:31
 */
@Service
public class SimpleColServiceImpl implements SimpleColService {
    @Autowired
    private SimpleColDao simpleColDao;
    @Autowired
    private MantainConfigDao mantainConfigDao;
    @Override
    public Map<String, Object> findBytypeId(String typeId) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;
        if (!"".equals(typeId)&&typeId!=null) {
            try {
                List<TSimpleColEntity> simpleColEntityList= simpleColDao.findBytypeId(typeId);
                map.put("simpleColEntityList",simpleColEntityList);
            } catch (Exception e) {
                //添加失败
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
    public Map<String, Object> add(TSimpleColEntity tSimpleColEntity) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;
        if (!"".equals(tSimpleColEntity)&&tSimpleColEntity!=null) {
            try {
                boolean flag = simpleColDao.find(tSimpleColEntity);
                if (flag == false) {
                    Long index=simpleColDao.getMax(tSimpleColEntity);
                    tSimpleColEntity.setOrdernum(index+1);
                    simpleColDao.add(tSimpleColEntity);
                    map.put("message", "添加成功");
                }
                else {
                    //添加失败
                    error = 3;
                    map.put("message", "该专项字段已经存在请重新添加");
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

    @Override
    public Map<String, Object> delete(TSimpleColEntity tSimpleColEntity) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;
        if (tSimpleColEntity != null && tSimpleColEntity.getStypeId() != null && !"".equals(tSimpleColEntity.getStypeId())) {
            try {
//                TSimpleColEntity result = simpleColDao.findOne(tSimpleColEntity.getStypeId());
//                if (result != null) {
                    simpleColDao.delete(tSimpleColEntity);
                    map.put("message", "删除成功");
//                } else {
//                    //修改失败
//                    error = 3;
//                    map.put("message", "删除失败");
//                }

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
    public Map<String, Object> findOne(TSimpleColEntity tSimpleColEntity) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;

        if (tSimpleColEntity != null && tSimpleColEntity.getCol() != null && !"".equals(tSimpleColEntity.getCol())) {
            try {
                Map<String, Object> fm = new HashMap();
                TSimpleColEntity tSimpleColEntity1 = simpleColDao.findOne(tSimpleColEntity.getCol());
                fm.put("stypeId", tSimpleColEntity1.getStypeId());
                fm.put("col", tSimpleColEntity1.getCol());
                fm.put("colname", tSimpleColEntity1.getColname());
                fm.put("isprov", tSimpleColEntity1.getIsprov());
                fm.put("iscity", tSimpleColEntity1.getIscity());
                fm.put("iscusttype", tSimpleColEntity1.getIscusttype());
                fm.put("isorder", tSimpleColEntity1.getIsorder());
                fm.put("isaddr", tSimpleColEntity1.getIsaddr());
                map.put("tSimpleColEntity", fm);
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
    public Map<String, Object> update(TSimpleColEntity tSimpleColEntity,String hecol) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;
        if (!"".equals(tSimpleColEntity)&&tSimpleColEntity!=null) {
            try {
                TSimpleColEntity result = simpleColDao.findOne(hecol);
                if (result != null) {
                    result.setStypeId(tSimpleColEntity.getStypeId());
                    result.setCol(tSimpleColEntity.getCol());
                    result.setColname(tSimpleColEntity.getColname());
                    result.setIsprov(tSimpleColEntity.getIsprov());
                    result.setIscity(tSimpleColEntity.getIscity());
                    result.setIscusttype(tSimpleColEntity.getIscusttype());
                    result.setIsorder(tSimpleColEntity.getIsorder());
                    result.setIsaddr(tSimpleColEntity.getIsaddr());
                    simpleColDao.update(result,hecol);
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
    public Map<String, Object> getMaxcol(TSimpleColEntity tSimpleColEntity) {
        Map<String, Object> map = new HashMap<>();
        int error = 0;
        if (!"".equals(tSimpleColEntity)&&tSimpleColEntity!=null&&!"".equals(tSimpleColEntity.getStypeId())&&tSimpleColEntity.getStypeId()!=null) {
            try {
                Long k = simpleColDao.getMax(tSimpleColEntity);
                if (k != null) {
                    map.put("maxcol",k+1);
                } else {
                    //修改失败
                    error = 3;
                    map.put("message", "数据异常");
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

}
