/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.bean;

/**
 *
 * @author LFeng
 */
public class TechnicalYaZhuang {
    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }
    
    private int technicalID;
    private String productCode;
    private String productLength;
    private String productHuGuang;
    private int firstStation;
    private int xiaLiaoNextStation;
    private int caiBiaoNextStation;
    private int daMaNextStation;
    private int yuZhuangNextStation;
    private int huaXianNextStation;
    private int huGuanNextStation;
    private int baoZhuangNextStation;
    private int queRenNextStation;

    public int getTechnicalID() {
        return technicalID;
    }

    public void setTechnicalID(int technicalID) {
        this.technicalID = technicalID;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductLength() {
        return productLength;
    }

    public void setProductLength(String productLength) {
        this.productLength = productLength;
    }

    public String getProductHuGuang() {
        return productHuGuang;
    }

    public void setProductHuGuang(String productHuGuang) {
        this.productHuGuang = productHuGuang;
    }

    public int getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(int firstStation) {
        this.firstStation = firstStation;
    }

    public int getXiaLiaoNextStation() {
        return xiaLiaoNextStation;
    }

    public void setXiaLiaoNextStation(int xiaLiaoNextStation) {
        this.xiaLiaoNextStation = xiaLiaoNextStation;
    }

    public int getCaiBiaoNextStation() {
        return caiBiaoNextStation;
    }

    public void setCaiBiaoNextStation(int caiBiaoNextStation) {
        this.caiBiaoNextStation = caiBiaoNextStation;
    }

    public int getDaMaNextStation() {
        return daMaNextStation;
    }

    public void setDaMaNextStation(int daMaNextStation) {
        this.daMaNextStation = daMaNextStation;
    }

    public int getYuZhuangNextStation() {
        return yuZhuangNextStation;
    }

    public void setYuZhuangNextStation(int yuZhuangNextStation) {
        this.yuZhuangNextStation = yuZhuangNextStation;
    }

    public int getHuaXianNextStation() {
        return huaXianNextStation;
    }

    public void setHuaXianNextStation(int huaXianNextStation) {
        this.huaXianNextStation = huaXianNextStation;
    }

    public int getHuGuanNextStation() {
        return huGuanNextStation;
    }

    public void setHuGuanNextStation(int huGuanNextStation) {
        this.huGuanNextStation = huGuanNextStation;
    }

    public int getBaoZhuangNextStation() {
        return baoZhuangNextStation;
    }

    public void setBaoZhuangNextStation(int baoZhuangNextStation) {
        this.baoZhuangNextStation = baoZhuangNextStation;
    }

    public int getQueRenNextStation() {
        return queRenNextStation;
    }

    public void setQueRenNextStation(int queRenNextStation) {
        this.queRenNextStation = queRenNextStation;
    }
    
}
