package com.ctbt.beidou.base.model;

public class SysDic {
    private Integer dicId;

    private String dicName;

    private String tableName;

    private String dicSql;

    private String validity;

    public Integer getDicId() {
        return dicId;
    }

    public void setDicId(Integer dicId) {
        this.dicId = dicId;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getDicSql() {
        return dicSql;
    }

    public void setDicSql(String dicSql) {
        this.dicSql = dicSql == null ? null : dicSql.trim();
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity == null ? null : validity.trim();
    }
}