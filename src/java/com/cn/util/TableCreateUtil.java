/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.util;

import com.cn.bean.Data;
import java.util.ArrayList;

/**
 *
 * @author LFeng
 */
public class TableCreateUtil {
    
    public static void main(String[] args) {
        
    }
    
    public static void createTable(String jsonData) {
        Data data = JsonUtil.getDate(jsonData);
        TableCreateUtil util = new TableCreateUtil();
        System.out.println("tableHtml:" + util.createTotalRow(data));
    }
    
    private String createTotalRow(Data data) {
        String tableHtml = "";
        for (int row = 0; row < data.getChildRowNum(); row++) {
            tableHtml += generateSingleLayout(row, data);
        }
        return tableHtml;
    }
    
    private String generateSingleLayout(int row, Data data) {
        String sigleItemHtml = "";
        ArrayList<Data> rowData = data.getRowData().get(row);
        for (Data rowItem : rowData) {
            int rowNum = rowItem.getChildRowNum();
            int fieldType = rowItem.getFieldType();
            int viewType = rowItem.getViewType();
            String fieldName = rowItem.getFieldName();
            String fieldValue = rowItem.getFieldValue();
            
            switch (viewType) {
                case 1: {
                    sigleItemHtml += "<td>" + fieldValue + "<td>";
                    break;
                }
                case 2: {
                    sigleItemHtml += "<td><input type='text' name='" + fieldName + "' id='" + fieldName + "' value=''>" + fieldValue + "</><td>";
                    break;
                }
                case 3: {
                    sigleItemHtml += "<td><label><input type='radio' name='" + fieldName + "' id='" + fieldName + "' value='fieldValue'>" + "合格" + "/></label>";
                    sigleItemHtml += "<label><input type='radio' name='" + fieldName + "' id='" + fieldName + "' value='fieldValue'>" + "不合格" + "/></label><td>";
                    break;
                }
                case 4: {
                    sigleItemHtml += "<td><input type='text' name='" + fieldName + "' id='" + fieldName + "' value=''>" + fieldValue + "</><td>";
                    break;
                }
                case 5: {
                    sigleItemHtml += "<td><input type='text' name='" + fieldName + "' id='" + fieldName + "' value=''>" + fieldValue + "</><td>";
                    break;
                }
            }
            
            if (rowNum > 1) {
                sigleItemHtml += createTotalRow(rowItem);
            }
        }
        return sigleItemHtml;
    }
}
