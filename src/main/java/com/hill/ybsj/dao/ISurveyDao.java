package com.hill.ybsj.dao;

import java.sql.SQLException;

public interface ISurveyDao {
    public String getCityList(String orgid) throws SQLException;

    public String getSurveyType(String rowv) throws SQLException;

    public String getCustType(String type) throws SQLException;

    public String getSurveyCol(String tab) throws SQLException;

    public String getSurveyData(String typeId, String orgid, String custType, String city, String address, int dist) throws SQLException;

    public String getSurveyInvData(String tab, String custType, String city, String userid, Integer start, Integer end) throws SQLException;

    public void saveInvit(String tab, String rowv, String invRes, String faultRes, String userid) throws SQLException;

    public void saveDist(String tab, String rowv, String distRes, String diaocy) throws SQLException;

    public String getSurveyUser(String userid) throws SQLException;

    public String getOrgname(String orgid);

    public void deleteByTypeId(String typeId);
}
