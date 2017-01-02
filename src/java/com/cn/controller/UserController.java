/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.controller;

import com.cn.bean.UserInfo;
import com.cn.util.DatabaseOpt;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);
    
    /**
     * 获取用户信息
     * @param userName
     * @param password
     * @param stationId
     * @param stationName 模糊查询
     * @param pageSize
     * @param pageIndex
     * @return 
     */
    public ArrayList<UserInfo> getUserInfo(String userName, String password, int stationId, String stationName, int pageSize, int pageIndex) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<UserInfo> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetUserInfo](?, ?, ?, ?, ?, ?, ?)}");
            statement.setString("userName", userName);
            statement.setString("password", password);
            statement.setInt("stationId", stationId);
            statement.setString("stationName", stationName);
            statement.setInt("pageSize", pageSize);
            statement.setInt("pageIndex", pageIndex);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UserInfo info = new UserInfo();
                info.setUserId(set.getInt("UserId"));
                info.setUsername(set.getString("UserName"));
                info.setPassword(set.getString("Password"));
                info.setStationId(set.getInt("StationId"));
                info.setStationIndex(set.getInt("StationIndex"));
                info.setStationName(set.getString("StationName"));
                info.setStationStructJson(set.getString("StationStructJson"));
                info.setUserType(set.getInt("UserType"));
                info.setUserTypeName(set.getString("UserTypeName"));
                info.setUserPermission(set.getString("UserPermission"));
                info.setUserTypeInitPage(set.getString("UserInitPage"));
                result.add(info);
            }
            UserInfo.setRecordCount(statement.getInt("recordCount"));
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                logger.error("数据库关闭连接错误", ex);
            }
        }
        return result;
    }
    
    /**
     * 添加用户信息
     * @param username
     * @param password
     * @param stationId
     * @param userType
     * @return 
     */
    public int addUserInfo(String username, String password, int stationId, int userType) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbAddUserInfo](?, ?, ?, ?, ?)}");
            statement.setString("username", username);
            statement.setString("password", password);
            statement.setInt("stationId", stationId);
            statement.setInt("userType", userType);
            statement.registerOutParameter("result", Types.INTEGER);
            statement.executeUpdate();
            result = statement.getInt("result");
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                logger.error("数据库关闭连接错误", ex);
            }
        }
        return result;
    }
    
    /**
     * 删除用户
     * @param userId
     * @return 
     */
    public int deleteUserInfo(int userId) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbDeleteUserInfo](?, ?)}");
            statement.setInt("userId", userId);
            statement.registerOutParameter("result", Types.INTEGER);
            statement.executeUpdate();
            result = statement.getInt("result");
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                logger.error("数据库关闭连接错误", ex);
            }
        }
        return result;
    }
}
