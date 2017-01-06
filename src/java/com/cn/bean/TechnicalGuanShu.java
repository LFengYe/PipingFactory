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
public class TechnicalGuanShu {
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
    private int yaZhuangNextStation;
    private int huaXianNextStation;

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

    public int getYaZhuangNextStation() {
        return yaZhuangNextStation;
    }

    public void setYaZhuangNextStation(int yaZhuangNextStation) {
        this.yaZhuangNextStation = yaZhuangNextStation;
    }

    public int getHuaXianNextStation() {
        return huaXianNextStation;
    }

    public void setHuaXianNextStation(int huaXianNextStation) {
        this.huaXianNextStation = huaXianNextStation;
    }
    
}
