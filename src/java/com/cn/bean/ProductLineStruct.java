/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.bean;

import com.alibaba.fastjson.JSONArray;
import java.util.ArrayList;

/**
 *
 * @author LFeng
 */
public class ProductLineStruct {
    private String fieldName;
    private String fieldValue;
    private int childRowNum;
    private int viewType;
    private int fieldType;
    private boolean textHorizon;
    private int width;
    private ArrayList<JSONArray> rowData;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public int getChildRowNum() {
        return childRowNum;
    }

    public void setChildRowNum(int childRowNum) {
        this.childRowNum = childRowNum;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isTextHorizon() {
        return textHorizon;
    }

    public void setTextHorizon(boolean textHorizon) {
        this.textHorizon = textHorizon;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ArrayList<JSONArray> getRowData() {
        return rowData;
    }

    public void setRowData(ArrayList<JSONArray> rowData) {
        this.rowData = rowData;
    }
    
}
