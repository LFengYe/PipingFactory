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
public class StationInfo {

    private int stationId;
    private String stationName;
    private String stationStructJson;
    private String stationDataTable;
    private int productLineId;
    private int stationIndex;
    private String addDataPro;
    private String updateDataPro;

    public void setStructJsonFiledValue(String filedName, String filedValue) {
        int index = stationStructJson.indexOf(filedName);
        if (index != -1) {
            index += stationStructJson.substring(index).indexOf("fieldValue") + 1;
            index += 12;
            stationStructJson = stationStructJson.substring(0, index) + filedValue + stationStructJson.substring(index, stationStructJson.length());
        } else {
            System.out.println("设置Json字段值出错");
        }
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationStructJson() {
        return stationStructJson;
    }

    public void setStationStructJson(String stationStructJson) {
        this.stationStructJson = stationStructJson;
    }

    public String getStationDataTable() {
        return stationDataTable;
    }

    public void setStationDataTable(String stationDataTable) {
        this.stationDataTable = stationDataTable;
    }

    public int getProductLineId() {
        return productLineId;
    }

    public void setProductLineId(int productLineId) {
        this.productLineId = productLineId;
    }

    public int getStationIndex() {
        return stationIndex;
    }

    public void setStationIndex(int stationIndex) {
        this.stationIndex = stationIndex;
    }

    public String getAddDataPro() {
        return addDataPro;
    }

    public void setAddDataPro(String addDataPro) {
        this.addDataPro = addDataPro;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getUpdateDataPro() {
        return updateDataPro;
    }

    public void setUpdateDataPro(String updateDataPro) {
        this.updateDataPro = updateDataPro;
    }
}
