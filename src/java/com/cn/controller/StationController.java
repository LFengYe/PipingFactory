/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.controller;

import com.cn.bean.Data;
import com.cn.bean.StationInfo;
import com.cn.util.DatabaseOpt;
import com.cn.util.JsonUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class StationController {
    private static final Logger logger = Logger.getLogger(StationController.class);
    
    /**
     * 根据用户名获取工位信息
     * @param username
     * @return 
     */
    public StationInfo getStationInfoWithUserName(String username) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        StationInfo result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbGetStationInfoWithUserName](?)}");
            statement.setString("username", username);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result = new StationInfo();
                result.setStationId(set.getInt("StationId"));
                result.setStationStructJson(set.getString("StationStructJson"));
                result.setStationDataTable(set.getString("StationDataTable"));
                result.setAddDataPro(set.getString("StationAddDataPro"));
                result.setProductLineId(set.getInt("ProductLineId"));
                result.setStationIndex(set.getInt("StationIndex"));
            }
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                logger.error("数据库关闭连接错误", ex);
            }
        }
        return result;
    }
    
    /**
     * 获取订单工位列表
     * @param orderId
     * @return 
     */
    public ArrayList<StationInfo> getStationList(int orderId) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<StationInfo> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetStationList](?)}");
            statement.setInt("orderId", orderId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                StationInfo info = new StationInfo();
                info.setStationId(set.getInt("StationId"));
                info.setStationName(set.getString("StationName"));
                info.setStationIndex(set.getInt("StationIndex"));
                info.setStationDataTable(set.getString("StationDataTable"));
                info.setStationStructJson(set.getString("StationStructJson"));
                info.setAddDataPro(set.getString("StationAddDataPro"));
                result.add(info);
            }
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                logger.error("数据库关闭连接错误", ex);
            }
        }
        return result;
    }
    
    /**
     * 获取工位详情
     * @param orderId
     * @param stationId
     * @return 
     */
    public StationInfo getStationInfo(int orderId, int stationId) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        StationInfo result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbGetStationDetail](?, ?)}");
            statement.setInt("orderId", orderId);
            statement.setInt("stationId", stationId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result = new StationInfo();
                result.setStationId(set.getInt("StationId"));
                result.setStationName(set.getString("StationName"));
                result.setStationStructJson(set.getString("StationStructJson"));
                
                Data data = JsonUtil.getDate(set.getString("StationStructJson"));
                for (int i = 0; i < data.getChildRowNum(); i++) {
                    ArrayList<String> tmp = getNameAndValueName(data, i, "");
                    for (String item : tmp) {
                        result.setStructJsonFiledValue(item, set.getString(item));
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                logger.error("数据库关闭连接错误", ex);
            }
        }
        return result;
    }
    
    /**
     * 
     * @param data
     * @param index
     * @param pre
     * @return 
     */
    public ArrayList<String> getNameAndValueName(Data data, int index, String pre) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Data> child = data.getRowData().get(index);
        for (Data item : child) {
            if (item.getChildRowNum() == 1) {
                if (item.getViewType() == 1) {
                    //result.add(pre + item.getFieldValue());
                } else {
                    result.add(item.getFieldName());
                }
            } else if (item.getChildRowNum() > 1) {
                for (int j = 0; j < item.getChildRowNum(); j++)
                    result.addAll(getNameAndValueName(item, j, item.getFieldValue() + "_"));
            }
        }
        return result;
    }
}
