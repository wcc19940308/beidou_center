package com.ctbt.beidou.base.model;

public class BdFishRecordDetail {
    private Integer detailId;

    private Integer recordId;

    private String fishType;

    private String fishGrade;

    private Integer fishWeight;

    private String weightUnit;

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType == null ? null : fishType.trim();
    }

    public String getFishGrade() {
        return fishGrade;
    }

    public void setFishGrade(String fishGrade) {
        this.fishGrade = fishGrade == null ? null : fishGrade.trim();
    }

    public Integer getFishWeight() {
        return fishWeight;
    }

    public void setFishWeight(Integer fishWeight) {
        this.fishWeight = fishWeight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit == null ? null : weightUnit.trim();
    }
}