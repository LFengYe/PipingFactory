/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.controller;

import com.cn.bean.pc.OrderInfo;
import com.cn.bean.StationInfo;
import com.cn.util.DatabaseOpt;
import com.cn.util.Units;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author LFeng
 */
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class);
    private final String[] productInfoField = {"MarkColorValue", "MarkColorValue1", "ProductGraphicValue", "YaZhuangOrHuaXianValue", "YaZhuangOrHuaXianSortValue", "ProductBatchValue", "ProductLengthValue"};

    /**
     * APP端获取第一个工位订单信息
     *
     * @param finishTime
     * @param orderId
     * @param userId
     * @param stationId
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public ArrayList<com.cn.bean.OrderInfo> getFirstStationOrderInfo(String finishTime, int orderId, int userId, int stationId, int pageSize, int pageIndex) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<com.cn.bean.OrderInfo> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetFirstStationOrderInfo](?, ?, ?, ?, ?, ?, ?)}");
            statement.setString("finishTime", finishTime);
            statement.setInt("orderId", orderId);
            statement.setInt("userId", userId);
            statement.setInt("stationId", stationId);
            statement.setInt("pageSize", pageSize);
            statement.setInt("pageIndex", pageIndex);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                com.cn.bean.OrderInfo info = new com.cn.bean.OrderInfo();
                info.setOrderId(set.getInt("OrderId"));
                info.setProductCommand(set.getString("ProductCommand"));
                info.setProductName(set.getString("ProductName"));
                info.setProductCode(set.getString("ProductCode"));
                info.setGraphicCode(set.getString("GraphicCode"));
                info.setCustomerName(set.getString("CustomerName"));
                info.setProductStandard(set.getString("ProductStandard"));
                info.setProductBatch(set.getString("ProductBatch"));
                info.setYaZhuangOrHuaXian(set.getString("YaZhuangOrHuaXian"));
                info.setYaZhuangOrHuaXianSort(set.getString("YaZhuangOrHuaXianSort"));
                info.setPlanNum(set.getInt("PlanNum"));
                info.setIsGuanShu(set.getInt("IsGuanShu"));
                info.setProductColor(set.getString("ProductColor"));
                info.setProductColor1(set.getString("ProductColor1"));
                info.setStationStructJson(set.getString("StationStructJson"));
                info.setProductLineShort(set.getString("ProductLineShort"));
                info.setProductLineStructJson(set.getString("ProductLineStructJson"));

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
     * APP端根据工位与周转卡号获取订单信息
     *
     * @param cardNum
     * @param stationId
     * @param finishTime
     * @return
     */
    public ArrayList<com.cn.bean.OrderInfo> getOrderInfo(String cardNum, int stationId, String finishTime) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<com.cn.bean.OrderInfo> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetOrderInfo](?, ?, ?)}");
            statement.setInt("stationId", stationId);
            statement.setString("cardNum", cardNum);
            statement.setString("finishTime", finishTime);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                if (set.getString("message").equalsIgnoreCase("0")) {
                    com.cn.bean.OrderInfo info = new com.cn.bean.OrderInfo();
                    info.setOrderId(set.getInt("OrderId"));
                    info.setProductCommand(set.getString("ProductCommand"));
                    info.setProductName(set.getString("ProductName"));
                    info.setProductCode(set.getString("ProductCode"));
                    info.setGraphicCode(set.getString("GraphicCode"));
                    info.setCustomerName(set.getString("CustomerName"));
                    info.setProductStandard(set.getString("ProductStandard"));
                    info.setProductBatch(set.getString("ProductBatch"));
                    info.setYaZhuangOrHuaXian(set.getString("YaZhuangOrHuaXian"));
                    info.setYaZhuangOrHuaXianSort(set.getString("YaZhuangOrHuaXianSort"));
                    info.setPlanNum(set.getInt("PlanNum"));
                    info.setIsGuanShu(set.getInt("IsGuanShu"));
                    info.setProductColor(set.getString("ProductColor"));
                    info.setProductColor1(set.getString("ProductColor1"));
                    info.setStationStructJson(set.getString("StationStructJson"));
                    info.setProductLineShort(set.getString("ProductLineShort"));
                    info.setProductLineStructJson(set.getString("ProductLineStructJson"));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat format1 = new SimpleDateFormat("yy/MM/dd");
                    Date date = format.parse(set.getString("PlanFinishedTime"));
                    info.setPlanFinishedTime(format1.format(date));
                    result.add(info);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } catch (ParseException ex) {
            logger.error("日期格式转换错误", ex);
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
     * 获取自动下料订单
     *
     * @param userId
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public ArrayList<com.cn.bean.OrderInfo> getAutoXiaLiaoOrderInfo(int userId, int pageSize, int pageIndex) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<com.cn.bean.OrderInfo> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetAutoXiaLiaoOrderInfo](?, ?, ?, ?)}");
            statement.setInt("userId", userId);
            statement.setInt("pageSize", pageSize);
            statement.setInt("pageIndex", pageIndex);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                com.cn.bean.OrderInfo info = new com.cn.bean.OrderInfo();
                info.setOrderId(set.getInt("OrderId"));
                info.setProductCommand(set.getString("ProductCommand"));
                info.setProductName(set.getString("ProductName"));
                info.setProductCode(set.getString("ProductCode"));
                info.setGraphicCode(set.getString("GraphicCode"));
                info.setCustomerName(set.getString("CustomerName"));
                info.setProductStandard(set.getString("ProductStandard"));
                info.setProductBatch(set.getString("ProductBatch"));
                info.setYaZhuangOrHuaXian(set.getString("YaZhuangOrHuaXian"));
                info.setYaZhuangOrHuaXianSort(set.getString("YaZhuangOrHuaXianSort"));
                info.setPlanNum(set.getInt("PlanNum"));
                info.setIsGuanShu(set.getInt("IsGuanShu"));
                info.setProductColor(set.getString("ProductColor"));
                info.setProductColor1(set.getString("ProductColor1"));
                info.setStationStructJson(set.getString("StationStructJson"));
                info.setProductLineShort(set.getString("ProductLineShort"));
                info.setProductLineStructJson(set.getString("ProductLineStructJson"));

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
     * APP段获取可能自动下料的列表, 用工位用户自行选择
     *
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public ArrayList<OrderInfo> getCouldAutoXiaLiaoOrder(int pageSize, int pageIndex) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<OrderInfo> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetOrderList_CouldAutoXiaLiao](?, ?, ?)}");
            statement.setInt("pageIndex", pageIndex);
            statement.setInt("pageSize", pageSize);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                OrderInfo info = new OrderInfo();
                info.setOrderId(set.getInt("OrderId"));
                info.setProductCommand(set.getString("ProductCommand"));
                info.setProductName(set.getString("ProductName"));
                info.setProductCode(set.getString("ProductCode"));
                info.setGraphicCode(set.getString("GraphicCode"));
                info.setCustomerName(set.getString("CustomerName"));
                info.setPlanNum(set.getInt("PlanNum"));
                info.setProductColor(set.getString("ProductColor"));
                info.setPlanFinishTime(set.getString("PlanFinishedTime"));
                info.setProductStandard(set.getString("ProductStandard"));
                info.setProductUnit(set.getString("ProductUnit"));
                result.add(info);
            }
            OrderInfo.setRecordCount(statement.getInt("recordCount"));
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
     * 根据订单ID获取订单信息
     *
     * @param orderId
     * @return
     */
    public com.cn.bean.OrderInfo getOrderInfoWithId(int orderId) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        com.cn.bean.OrderInfo info = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("select * from tbOrder_Station where OrderId = ?");
            statement.setInt(1, orderId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                info = new com.cn.bean.OrderInfo();
                info.setOrderId(set.getInt("OrderId"));
                info.setProductCommand(set.getString("ProductCommand"));
                info.setProductName(set.getString("ProductName"));
                info.setProductCode(set.getString("ProductCode"));
                info.setGraphicCode(set.getString("GraphicCode"));
                info.setCustomerName(set.getString("CustomerName"));
                info.setProductStandard(set.getString("ProductStandard"));
                info.setProductBatch(set.getString("ProductBatch"));
                info.setYaZhuangOrHuaXian(set.getString("YaZhuangOrHuaXian"));
                info.setYaZhuangOrHuaXianSort(set.getString("YaZhuangOrHuaXianSort"));
                info.setProductLength(set.getString("ProductLength"));
                info.setPlanNum(set.getInt("PlanNum"));
                info.setIsGuanShu(set.getInt("IsGuanShu"));
                info.setProductColor(set.getString("ProductColor"));
                info.setProductColor1(set.getString("ProductColor1"));
                info.setStationStructJson(set.getString("StationStructJson"));
                info.setProductLineShort(set.getString("ProductLineShort"));
                info.setProductLineStructJson(set.getString("ProductLineStructJson"));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format1 = new SimpleDateFormat("yy/MM/dd");
                Date date = format.parse(set.getString("PlanFinishedTime"));
                info.setPlanFinishedTime(format1.format(date));
            }
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } catch (ParseException ex) {
            logger.error("日期格式转换错误", ex);
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
        return info;
    }

    /**
     * PC端获取订单列表
     *
     * @param productCommand
     * @param productName
     * @param productCode
     * @param productLineId
     * @param isFinished
     * @param isXiaLiaoFinished
     * @param isStorage
     * @param isGuanShu
     * @param finishTime
     * @param cardNum
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public ArrayList<OrderInfo> getOrderList(String productCommand, String productName, String productCode, int productLineId, int isFinished,
            int isXiaLiaoFinished, int isStorage, int isGuanShu, String finishTime, String cardNum, int pageSize, int pageIndex) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<OrderInfo> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetOrderList](?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setString("productCommand", productCommand);
            statement.setString("productName", productName);
            statement.setString("productCode", productCode);
            statement.setString("planFinishedTime", finishTime);
            statement.setString("cardNum", cardNum);
            statement.setInt("productLineId", productLineId);
            statement.setInt("isFinished", isFinished);
            statement.setInt("isXiaLiaoFinished", isXiaLiaoFinished);
            statement.setInt("isStorage", isStorage);
            statement.setInt("isGuanShu", isGuanShu);
            statement.setInt("pageIndex", pageIndex);
            statement.setInt("pageSize", pageSize);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                OrderInfo info = new OrderInfo();
                info.setOrderId(set.getInt("OrderId"));
                info.setProductCommand(set.getString("ProductCommand"));
                info.setProductName(set.getString("ProductName"));
                info.setProductCode(set.getString("ProductCode"));
                info.setGraphicCode(set.getString("GraphicCode"));
                info.setCustomerName(set.getString("CustomerName"));
                info.setPlanNum(set.getInt("PlanNum"));
                info.setProductColor(set.getString("ProductColor"));
                info.setPlanFinishTime(set.getString("PlanFinishedTime"));
                info.setProductStandard(set.getString("ProductStandard"));
                info.setProductUnit(set.getString("ProductUnit"));
                info.setCardNum(set.getString("CardNum"));
                result.add(info);
            }
            OrderInfo.setRecordCount(statement.getInt("recordCount"));
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
     * 管束未完成订单 完成状态为未完成, 管束状态为管束
     *
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public ArrayList<OrderInfo> getUnFinishedList(int pageSize, int pageIndex) {
        return getOrderList(null, null, null, 4, 0, -1, -1, 1, null, null, pageSize, pageIndex);
    }

    /**
     * 修改订单管束类型
     *
     * @param orderId
     * @param isGuanShu
     * @return
     */
    public int updateGuanShuOrder(int orderId, int isGuanShu) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbUpdateOrderGuanShuState](?, ?, ?)}");
            statement.setInt("orderId", orderId);
            statement.setInt("isGuanShu", isGuanShu);
            statement.registerOutParameter("result", Types.INTEGER);
            statement.executeUpdate();
            result = statement.getInt("result");

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
     * 获取订单已完成序号(管束)
     *
     * @param orderId
     * @param stationId
     * @return
     */
    public String getFinishedSerial(int orderId, int stationId) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        String result = "";
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call tbGetFinishedSerial(?, ?)}");
            statement.setInt("orderId", orderId);
            statement.setInt("stationId", stationId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result += set.getString("GuanShuSerial") + ",";
            }
            if (result.length() > 0) {
                result = result.substring(0, result.length() - 1);
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
     * 修改订单下料状态 0 -- 未进行 | 1 -- 已完成 | 2 -- 正在进行(已有用户读取)(弃用) | 3 -- 自动下料
     *
     * @param orderId
     * @param xiaLiaoState
     * @return
     */
    public int updateXiaLiaoState(int orderId, int xiaLiaoState) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbUpdateOrderXiaLiaoState](?, ?, ?)}");
            statement.setInt("orderId", orderId);
            statement.setInt("xiaLiaoState", xiaLiaoState);
            statement.registerOutParameter("result", Types.INTEGER);
            statement.executeUpdate();
            result = statement.getInt("result");

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
     * 修改订单读取用户
     *
     * @param orderId
     * @param userId
     * @return
     */
    public int updateReadUser(int orderId, int userId) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbUpdateOrderReadUser](?, ?, ?)}");
            statement.setInt("orderId", orderId);
            statement.setInt("userId", userId);
            statement.registerOutParameter("result", Types.INTEGER);
            statement.executeUpdate();
            result = statement.getInt("result");

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
     * APP端添加工位数据
     *
     * @param info
     * @param username
     * @param cardNum
     * @param orderId
     * @param isGuanShu
     * @param jsonData
     * @return
     */
    public int addStationData(StationInfo info, String username, String cardNum, int orderId, int isGuanShu, String jsonData) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        CallableStatement updateMarkColor = null;
        int uploadProductInfoParamsCount = 0;
        int updateProductInfoParamsCount = 0;
        int result = -1;
        try {
            JsonArray array = new JsonParser().parse(jsonData).getAsJsonArray();
            String sql = "{call " + info.getAddDataPro() + "(?, ?, ?, ?";
            for (JsonElement element : array) {
                if (!Units.isStrInArray(element.getAsJsonObject().get("fieldName").getAsString(), productInfoField)) {
                    sql += ", ?";
                } else {
                    uploadProductInfoParamsCount++;
                }
            }
            if (isGuanShu == 1) {
                sql += ", ?)}";
            }
            if (isGuanShu == 0) {
                sql += ", ?, ?)}";
            }
            System.out.println("sql:" + sql + ",isGuanShu:" + isGuanShu + ",cardNum:" + cardNum);

            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall(sql);
            statement.setString("cardNum", cardNum);
            statement.setString("username", username);
            statement.setInt("orderid", orderId);
            statement.setInt("isGuanShu", isGuanShu);
            statement.registerOutParameter("result", Types.INTEGER);
            if (isGuanShu == 0) {
                statement.setInt("GuanShuSerial", 0);
            }
            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();
                if (Units.isStrInArray(object.get("fieldName").getAsString(), productInfoField)) {
                    if (null != updateMarkColor) {
                        updateMarkColor.setString(object.get("fieldName").getAsString(), object.get("fieldValue").getAsString());
                    } else {
                        String sql1 = "{call [tbUpdateMarkColor](?";
                        for (int i = 0; i < productInfoField.length; i++) {
                            sql1 += ", ?";
                        }
                        sql1 += ")}";
                        updateMarkColor = conn.prepareCall(sql1);
                        updateMarkColor.setInt("orderId", orderId);
                        for (String field : productInfoField) {
                            updateMarkColor.setString(field, null);
                        }
                        updateMarkColor.setString(object.get("fieldName").getAsString(), object.get("fieldValue").getAsString());
                    }
                    updateProductInfoParamsCount++;
                    if (updateProductInfoParamsCount == uploadProductInfoParamsCount) {
                        updateMarkColor.executeUpdate();
                    }
                    continue;
                }
                /*
                 if (object.get("fieldName").getAsString().equals("MarkColorValue")) {
                 updateMarkColorParamsCount++;
                 if (null != updateMarkColor) {
                 updateMarkColor.setString("MarkColorValue", object.get("fieldValue").getAsString());
                 if (updateMarkColorParamsCount == 2) {
                 updateMarkColor.executeUpdate();
                 }
                 } else {
                 updateMarkColor = conn.prepareCall("{call [tbUpdateMarkColor](?, ?, ?, ?)}");
                 updateMarkColor.setInt("orderId", orderId);
                 updateMarkColor.setString("MarkColorValue", object.get("fieldValue").getAsString());
                 }
                 continue;
                 }
                 if (object.get("fieldName").getAsString().equals("MarkColorValue1")) {
                 updateMarkColorParamsCount++;
                 if (null != updateMarkColor) {
                 updateMarkColor.setString("MarkColorValue1", object.get("fieldValue").getAsString());
                 if (updateMarkColorParamsCount == 2) {
                 updateMarkColor.executeUpdate();
                 }
                 } else {
                 updateMarkColor = conn.prepareCall("{call [tbUpdateMarkColor](?, ?, ?, ?)}");
                 updateMarkColor.setInt("orderId", orderId);
                 updateMarkColor.setString("MarkColorValue1", object.get("fieldValue").getAsString());
                 }
                 continue;
                 }
                 */

                switch (object.get("fieldType").getAsInt()) {
                    case 1: {
                        statement.setInt(object.get("fieldName").getAsString(), object.get("fieldValue").getAsInt());
                        break;
                    }
                    case 2: {
                        statement.setFloat(object.get("fieldName").getAsString(), object.get("fieldValue").getAsFloat());
                        break;
                    }
                    case 3: {
                        statement.setString(object.get("fieldName").getAsString(), object.get("fieldValue").getAsString());
                        break;
                    }
                    case 4: {
                        if (object.get("fieldValue").getAsBoolean()) {
                            statement.setString(object.get("fieldName").getAsString(), "true");
                        } else {
                            statement.setString(object.get("fieldName").getAsString(), "false");
                        }
                        break;
                    }
                }
            }

            statement.executeUpdate();
            result = statement.getInt("result");
            System.out.println("result:" + result);
        } catch (SQLException ex) {
            logger.error("数据库操作错误", ex);
        } finally {
            try {
                if (updateMarkColor != null) {
                    updateMarkColor.close();
                }
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
     * 导入订单(需要检测产品编号, 在工艺表中是否存在)
     *
     * @param fileName
     * @param productLineId
     * @param isEmergency
     * @param isGuanShu
     * @return
     * @throws IllegalStateException
     */
    public int importOrder(String fileName, int productLineId, int isEmergency, int isGuanShu) throws IllegalStateException {
        InputStream inputStream = null;
        try {
            File file = new File(fileName);
            inputStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            ArrayList<OrderInfo> imports = new ArrayList<>();
            for (int i = 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
//                logger.info("count row num:" + sheet.getPhysicalNumberOfRows() + ",the row num is:" + i);
                HSSFRow row = sheet.getRow(i);
                if (null == row) {
                    continue;
                }

                int cellNum = row.getPhysicalNumberOfCells();
//                logger.info("count cell num is:" + cellNum);
                if (cellNum >= 8) {
                    OrderInfo info = new OrderInfo();
                    info.setProductLineId(productLineId);

                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    info.setProductCommand(row.getCell(0).getStringCellValue());
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    info.setProductCode(row.getCell(1).getStringCellValue());
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    info.setProductName(row.getCell(2).getStringCellValue());
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    info.setProductUnit(row.getCell(3).getStringCellValue());
                    row.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
                    info.setPlanNum((int) row.getCell(4).getNumericCellValue());
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    info.setProductStandard(row.getCell(5).getStringCellValue());

                    if (row.getCell(6).getCellType() == Cell.CELL_TYPE_STRING) {
                        info.setPlanFinishTime(row.getCell(6).getStringCellValue());
                    } else {
                        if (HSSFDateUtil.isCellDateFormatted(row.getCell(6))) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = HSSFDateUtil.getJavaDate(row.getCell(6).getNumericCellValue());
                            info.setPlanFinishTime(dateFormat.format(date));
                        } else {
                            DecimalFormat df = new DecimalFormat("0");
                            info.setPlanFinishTime(df.format(row.getCell(6).getNumericCellValue()));
                        }
                    }
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    info.setCustomerName(row.getCell(7).getStringCellValue());
                    imports.add(info);
                }
            }

            DatabaseOpt opt;
            Connection conn = null;
            CallableStatement statement = null;
            opt = new DatabaseOpt();
            try {
                conn = opt.getConnect();
                statement = conn.prepareCall("{call [tbAddOrderInfo](?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                for (OrderInfo infoImport : imports) {
                    statement.setInt("productLineId", infoImport.getProductLineId());
                    statement.setInt("planNum", infoImport.getPlanNum());
                    statement.setString("productCommand", infoImport.getProductCommand());
                    statement.setString("productCode", infoImport.getProductCode());
                    statement.setString("productName", infoImport.getProductName());
                    statement.setString("productUnit", infoImport.getProductUnit());
                    statement.setString("productStandard", infoImport.getProductStandard());
                    statement.setString("planFinishedTime", infoImport.getPlanFinishTime());
                    statement.setInt("isEmergency", isEmergency);
                    statement.setInt("isGuanShu", isGuanShu);
                    statement.setString("customerName", infoImport.getCustomerName());
                    statement.addBatch();
                }
                statement.executeBatch();
                return 0;
            } catch (SQLException ex) {
                logger.error("数据库执行错误", ex);
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

        } catch (FileNotFoundException ex) {
            logger.error("未找到文件", ex);
        } catch (IOException ex) {
            logger.error("IO异常", ex);
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                logger.error("关闭输入流异常", ex);
            }
        }
        return -1;
    }

    /**
     * 订单入库
     *
     * @param orderId
     * @return
     */
    public int orderStorage(int orderId) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call tbUpdateOrderInStorage(?, ?)}");
            statement.setInt("orderId", orderId);
            statement.registerOutParameter("result", Types.INTEGER);
            statement.executeUpdate();
            result = statement.getInt("result");

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
     * 删除订单
     *
     * @param orderId
     * @return
     */
    public int orderDelete(int orderId) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbDeleteOrderInfo](?, ?)}");
            statement.setInt("orderId", orderId);
            statement.registerOutParameter("result", Types.INTEGER);
            statement.executeUpdate();
            result = statement.getInt("result");

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
}
