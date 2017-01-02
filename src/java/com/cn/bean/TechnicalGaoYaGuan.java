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
public class TechnicalGaoYaGuan {
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
    private int jianYanNextStation;
    private int daMaNextStation;
    private int huaXianNextStation;
    private int yuZhuangNextStation;
    private int kouYaNextStation;
    private int daYaNextStation;
    private int chuiSaoNextStation;
    private int seBiaoNextStation;
    private int baoZhuangNextStation;

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

    public int getJianYanNextStation() {
        return jianYanNextStation;
    }

    public void setJianYanNextStation(int jianYanNextStation) {
        this.jianYanNextStation = jianYanNextStation;
    }

    public int getHuaXianNextStation() {
        return huaXianNextStation;
    }

    public void setHuaXianNextStation(int huaXianNextStation) {
        this.huaXianNextStation = huaXianNextStation;
    }

    public int getYuZhuangNextStation() {
        return yuZhuangNextStation;
    }

    public void setYuZhuangNextStation(int yuZhuangNextStation) {
        this.yuZhuangNextStation = yuZhuangNextStation;
    }

    public int getKouYaNextStation() {
        return kouYaNextStation;
    }

    public void setKouYaNextStation(int kouYaNextStation) {
        this.kouYaNextStation = kouYaNextStation;
    }

    public int getDaYaNextStation() {
        return daYaNextStation;
    }

    public void setDaYaNextStation(int daYaNextStation) {
        this.daYaNextStation = daYaNextStation;
    }

    public int getChuiSaoNextStation() {
        return chuiSaoNextStation;
    }

    public void setChuiSaoNextStation(int chuiSaoNextStation) {
        this.chuiSaoNextStation = chuiSaoNextStation;
    }

    public int getSeBiaoNextStation() {
        return seBiaoNextStation;
    }

    public void setSeBiaoNextStation(int seBiaoNextStation) {
        this.seBiaoNextStation = seBiaoNextStation;
    }

    public int getBaoZhuangNextStation() {
        return baoZhuangNextStation;
    }

    public void setBaoZhuangNextStation(int baoZhuangNextStation) {
        this.baoZhuangNextStation = baoZhuangNextStation;
    }

    public int getDaMaNextStation() {
        return daMaNextStation;
    }

    public void setDaMaNextStation(int daMaNextStation) {
        this.daMaNextStation = daMaNextStation;
    }

    
}
