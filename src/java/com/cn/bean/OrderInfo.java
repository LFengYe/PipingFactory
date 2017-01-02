/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.bean;

import com.cn.util.Units;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author LFeng
 */
public class OrderInfo {

    private int orderId;
    private int productLineId;
    private String productLineShort;
    private String productLineStructJson;
    private String stationStructJson;
    private String productCommand;
    private String productName;
    private String productCode;
    private String graphicCode;
    private String productStandard;
    private String customerName;
    private int planNum;
    private String productColor;
    private String productColor1;
    private String planFinishedTime;
    private int isGuanShu;
    private String yaZhuangOrHuaXian;
    private String yaZhuangOrHuaXianSort;
    private String productBatch;

    public int getOrderId() {
        return orderId;
    }

    public String getStationStructJson() {
        return stationStructJson;
    }

    public void setStationStructJson(String stationStructJson) {
        this.stationStructJson = stationStructJson;
    }

    public String getProductLineShort() {
        return productLineShort;
    }

    public void setProductLineShort(String productLineShort) {
        this.productLineShort = productLineShort;
    }

    public String getProductLineStructJson() {
        return productLineStructJson;
    }

    public void setProductLineStructJson(String productLineStructJson) {
//        System.out.println(productLineStructJson);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(productLineStructJson);
        JsonObject object = element.getAsJsonObject();
        ProductLineStruct struct = new ProductLineStruct();
        struct.setFieldName(object.get("fieldName").getAsString());
        struct.setFieldValue(object.get("fieldValue").getAsString());
        struct.setChildRowNum(object.get("childRowNum").getAsInt());
        struct.setViewType(object.get("viewType").getAsInt());
        struct.setFieldType(object.get("fieldType").getAsInt());
        struct.setTextHorizon(object.get("textHorizon").getAsBoolean());
        struct.setWidth(object.get("width").getAsInt());

        for (int i = 0; i < struct.getChildRowNum(); i++) {
            String fieldName = object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().get("fieldName").getAsString();
            switch (fieldName) {
                case "ProductZlValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == productCommand) ? ("") : (productCommand));
                    break;
                }
                case "ProductNameValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", ((null == productName) ? ("") : (productName)) + "/" + ((null == graphicCode) ? ("") : (graphicCode)));
                    break;
                }
                case "ProductCodeValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == productCode) ? ("") : (productCode));
                    break;
                }
                case "CustomerNameValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == customerName) ? ("") : (customerName));
                    break;
                }
                case "PlanNumValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", planNum);
                    break;
                }
                case "ProductGraphicValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == graphicCode) ? ("") : (graphicCode));
                    if (!Units.strIsEmpty(graphicCode))
                        object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("viewType", 1);
                    break;
                }
                case "YaZhuangOrHuaXianValue": {
                    /*
                    if (null != yaZhuangOrHuaXian && yaZhuangOrHuaXian.compareTo("true") == 0)
                        yaZhuangOrHuaXian = "冲压";
                    if (null != yaZhuangOrHuaXian && yaZhuangOrHuaXian.compareTo("false") == 0)
                        yaZhuangOrHuaXian = "划线";
                    */
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == yaZhuangOrHuaXian) ? ("") : (yaZhuangOrHuaXian));
                    if (!Units.strIsEmpty(yaZhuangOrHuaXian))
                        object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("viewType", 1);
                    break;
                }
                case "YaZhuangOrHuaXianSortValue": {
                    /*
                    System.out.println(yaZhuangOrHuaXianSort);
                    if (null != yaZhuangOrHuaXianSort && yaZhuangOrHuaXianSort.compareTo("true") == 0)
                        yaZhuangOrHuaXianSort = "冲长划短";
                    if (null != yaZhuangOrHuaXianSort && yaZhuangOrHuaXianSort.compareTo("false") == 0)
                        yaZhuangOrHuaXianSort = "冲短划长";
                    */
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == yaZhuangOrHuaXianSort) ? ("") : (yaZhuangOrHuaXianSort));
                    if (!Units.strIsEmpty(yaZhuangOrHuaXianSort))
                        object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("viewType", 1);
                    break;
                }
                case "ProductStandardValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == productStandard) ? ("") : (productStandard));
                    break;
                }
                case "ProductBatchValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == productBatch) ? ("") : (productBatch));
                    break;
                }
                case "MarkColorValue": {
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(1).getAsJsonObject().addProperty("fieldValue", (null == productColor) ? ("") : (productColor));
                    object.get("rowData" + (i + 1)).getAsJsonArray().get(2).getAsJsonObject().addProperty("fieldValue", (null == productColor1) ? ("") : (productColor1));
                    break;
                }
                case "MarkColorValue1": {
                    break;
                }
            }
        }
        Gson gson = new Gson();
        if (isGuanShu == 1) {
            object.addProperty("childRowNum", 7);
            JsonElement serialElement = parser.parse("[{\"fieldName\":\"FinishedSerial\", \"fieldValue\":\"已完成序号\", \"viewType\":1,\"fieldType\":3,\"childRowNum\":1,\"textHorizon\":true,\"width\":1},"
                    + "{\"fieldName\":\"FinishedSerialValue\", \"fieldValue\":\"%s\", \"viewType\":1,\"fieldType\":3,\"childRowNum\":1,\"textHorizon\":true,\"width\":1},"
                    + "{\"fieldName\":\"CurSerial\", \"fieldValue\":\"当前序号\", \"viewType\":1,\"fieldType\":3,\"childRowNum\":1,\"textHorizon\":true,\"width\":1},"
                    + "{\"fieldName\":\"GuanShuSerial\", \"fieldValue\":\"\", \"viewType\":2,\"fieldType\":1,\"childRowNum\":1,\"textHorizon\":true,\"width\":1}]");
            object.add("rowData7", serialElement);
        }
        this.productLineStructJson = gson.toJson(object);
//        System.out.println("productLineStructJson" + this.productLineStructJson);
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductLineId(int productLineId) {
        this.productLineId = productLineId;
    }

    public void setProductCommand(String productCommand) {
        this.productCommand = productCommand;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setGraphicCode(String graphicCode) {
        this.graphicCode = graphicCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPlanNum(int planNum) {
        this.planNum = planNum;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public int getProductLineId() {
        return productLineId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getPlanFinishedTime() {
        return planFinishedTime;
    }

    public void setPlanFinishedTime(String planFinishedTime) {
        this.planFinishedTime = planFinishedTime;
    }

    public String getProductName() {
        return productName;
    }

    public String getGraphicCode() {
        return graphicCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getPlanNum() {
        return planNum;
    }

    public String getProductColor1() {
        return productColor1;
    }

    public void setProductColor1(String productColor1) {
        this.productColor1 = productColor1;
    }

    public int getIsGuanShu() {
        return isGuanShu;
    }

    public void setIsGuanShu(int isGuanShu) {
        this.isGuanShu = isGuanShu;
    }

    public String getProductStandard() {
        return productStandard;
    }

    public void setProductStandard(String productStandard) {
        this.productStandard = productStandard;
    }

    public String getYaZhuangOrHuaXian() {
        return yaZhuangOrHuaXian;
    }

    public void setYaZhuangOrHuaXian(String yaZhuangOrHuaXian) {
        this.yaZhuangOrHuaXian = yaZhuangOrHuaXian;
    }

    public String getYaZhuangOrHuaXianSort() {
        return yaZhuangOrHuaXianSort;
    }

    public void setYaZhuangOrHuaXianSort(String yaZhuangOrHuaXianSort) {
        this.yaZhuangOrHuaXianSort = yaZhuangOrHuaXianSort;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

}
