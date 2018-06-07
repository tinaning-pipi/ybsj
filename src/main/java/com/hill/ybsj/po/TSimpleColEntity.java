package com.hill.ybsj.po;


public class TSimpleColEntity {
private  String  stypeId;
private  String  col;
private  String colname;
private  Long   ordernum;
private  Long isprov;
private  Long iscity;
private  Long iscusttype;
private  Long isorder;
private  Long isaddr;
public TSimpleColEntity(){}
public TSimpleColEntity(String col,String colname){
    this.col=col;
    this.colname=colname;
}
    public String getStypeId() {
        return stypeId;
    }
    public void setStypeId(String stypeId) {
        this.stypeId = stypeId;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    public Long getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }

    public Long getIsprov() {
        return isprov;
    }

    public void setIsprov(Long isprov) {
        this.isprov = isprov;
    }

    public Long getIscity() {
        return iscity;
    }

    public void setIscity(Long iscity) {
        this.iscity = iscity;
    }

    public Long getIscusttype() {
        return iscusttype;
    }

    public void setIscusttype(Long iscusttype) {
        this.iscusttype = iscusttype;
    }

    public Long getIsorder() {
        return isorder;
    }

    public void setIsorder(Long isorder) {
        this.isorder = isorder;
    }

    public Long getIsaddr() {
        return isaddr;
    }

    public void setIsaddr(Long isaddr) {
        this.isaddr = isaddr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TSimpleColEntity that = (TSimpleColEntity) o;

        if (stypeId != null ? !stypeId.equals(that.stypeId) : that.stypeId != null) return false;
        if (col != null ? !col.equals(that.col) : that.col != null) return false;
        if (colname != null ? !col.equals(that.colname) : that.colname != null) return false;
        if (ordernum != null ? !ordernum.equals(that.ordernum) : that.ordernum != null) return false;
        if ( isprov!= null ? !isprov.equals(that.isprov) : that.isprov != null) return false;
        if ( iscity!= null ? !iscity.equals(that.iscity) : that.iscity != null) return false;
        if ( iscusttype!= null ? !iscusttype.equals(that.iscusttype) : that.iscusttype != null) return false;
        if ( isorder!= null ? !isorder.equals(that.isorder) : that.isorder != null) return false;
        if ( isaddr!= null ? !isaddr.equals(that.isaddr) : that.isaddr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stypeId != null ? stypeId.hashCode() : 0;
        result = 31 * result + (col != null ? col.hashCode() : 0);
        result = 31 * result + (colname != null ? colname.hashCode() : 0);
        result = 31 * result + (ordernum != null ? ordernum.hashCode() : 0);
        result = 31 * result + (isprov != null ? isprov.hashCode() : 0);
        result = 31 * result + (iscity != null ? iscity.hashCode() : 0);
        result = 31 * result + (iscusttype != null ? iscusttype.hashCode() : 0);
        result = 31 * result + (isorder != null ? isorder.hashCode() : 0);
        result = 31 * result + (isaddr != null ? isaddr.hashCode() : 0);
        return result;
    }
}