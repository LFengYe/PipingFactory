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
public class TechnicalChengXing {
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
    private int daMaNextStation;
    private int huaXianNextStation;
    private int chengXingNextStation;
    private int jiaReLengQueNextStation;
//    private int lengQueNextStation;
    private int jieChangNextStation;

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

    public int getDaMaNextStation() {
        return daMaNextStation;
    }

    public void setDaMaNextStation(int daMaNextStation) {
        this.daMaNextStation = daMaNextStation;
    }

    public int getHuaXianNextStation() {
        return huaXianNextStation;
    }

    public void setHuaXianNextStation(int huaXianNextStation) {
        this.huaXianNextStation = huaXianNextStation;
    }

    public int getChengXingNextStation() {
        return chengXingNextStation;
    }

    public void setChengXingNextStation(int chengXingNextStation) {
        this.chengXingNextStation = chengXingNextStation;
    }

    public int getJieChangNextStation() {
        return jieChangNextStation;
    }

    public void setJieChangNextStation(int jieChangNextStation) {
        this.jieChangNextStation = jieChangNextStation;
    }

    public int getJiaReLengQueNextStation() {
        return jiaReLengQueNextStation;
    }

    public void setJiaReLengQueNextStation(int jiaReLengQueNextStation) {
        this.jiaReLengQueNextStation = jiaReLengQueNextStation;
    }

}
