/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.controller;

import com.cn.bean.Version;
import com.cn.util.DatabaseOpt;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class VersionController {
    private static final Logger logger = Logger.getLogger(VersionController.class);
    
    /**
     * 获取最新版本
     * @param updateType
     * @return 
     */
    public Version getNewestVersion(int updateType) {
        DatabaseOpt opt = new DatabaseOpt();
        Connection conn = opt.getConnect();
        CallableStatement statement = null;
        Version result = null;
        try {
            statement = conn.prepareCall("{call tbGetUpdateInfo()}");
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                result = new Version();
                result.setVarsionID(set.getInt("VersionId"));
                result.setUpdateTime(set.getString("UpdateTime"));
                result.setVersionNumber(set.getInt("VersionNumber"));
                result.setVersionNumberName(set.getString("VersionNumberName"));
                result.setAppPathUrl(set.getString("AppPathUrl"));
            }
        } catch (SQLException ex) {
            logger.error("数据库执行出错!", ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                logger.error("数据库关闭出错!", ex);
            }
        }
        return result;
    }
}
