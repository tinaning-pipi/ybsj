package com.hill.ybsj.po;

public class TSurveyTypeEntity {
    private String typeId;
    private String surveyType;
    private Long showNum;
    private Long colNum;
    private Long openlnla;
    public TSurveyTypeEntity(){}
    public TSurveyTypeEntity(String typeId,String surveyType,Long showNum, String ybXs,Long colNum,Long openlnla){
        this.typeId=typeId;
        this.surveyType=surveyType;
        this.showNum=showNum;
        this.colNum=colNum;
        this.openlnla=openlnla;
    }
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public Long getShowNum() {
        return showNum;
    }

    public void setShowNum(Long showNum) {
        this.showNum = showNum;
    }

    public Long getColNum() {
        return colNum;
    }

    public void setColNum(Long colNum) {
        this.colNum = colNum;
    }

    public Long getOpenlnla() {
        return openlnla;
    }

    public void setOpenlnla(Long openlnla) {
        this.openlnla = openlnla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TSurveyTypeEntity that = (TSurveyTypeEntity) o;

        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (surveyType != null ? !surveyType.equals(that.surveyType) : that.surveyType != null) return false;
        if (showNum != null ? !showNum.equals(that.showNum) : that.showNum != null) return false;
        if (colNum != null ? !colNum.equals(that.colNum) : that.colNum != null) return false;
        if (openlnla != null ? !openlnla.equals(that.openlnla) : that.openlnla != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId != null ? typeId.hashCode() : 0;
        result = 31 * result + (surveyType != null ? surveyType.hashCode() : 0);
        result = 31 * result + (showNum != null ? showNum.hashCode() : 0);
        result = 31 * result + (colNum != null ? colNum.hashCode() : 0);
        result = 31 * result + (openlnla != null ? openlnla.hashCode() : 0);
        return result;
    }
}
