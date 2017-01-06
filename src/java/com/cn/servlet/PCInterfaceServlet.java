/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.servlet;

import com.alibaba.fastjson.JSONObject;
import com.cn.bean.StationInfo;
import com.cn.bean.TechnicalChengXing;
import com.cn.bean.TechnicalGaoYaGuan;
import com.cn.bean.TechnicalGuanShu;
import com.cn.bean.TechnicalYaZhuang;
import com.cn.bean.TechnicalZhuan;
import com.cn.bean.pc.OrderInfo;
import com.cn.bean.UserInfo;
import com.cn.controller.OrderController;
import com.cn.controller.StationController;
import com.cn.controller.TechnicalController;
import com.cn.controller.UserController;
import com.cn.util.Units;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class PCInterfaceServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(PCInterfaceServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param params
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String params)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String subUri = uri.substring(uri.lastIndexOf("/") + 1,
                uri.lastIndexOf("."));
        String json = "";
        try {
            System.out.println(subUri + ",params:" + params);
            JSONObject paramsJson = JSONObject.parseObject(params);
            HttpSession session = request.getSession();
            /*验证是否登陆*/
            if (!"login".equals(subUri) && session.getAttribute("user") == null) {
                session.invalidate();
                json = Units.objectToJson(-99, "未登陆", null);
                PrintWriter out = response.getWriter();
                try {
                    response.setContentType("text/html;charset=UTF-8");
                    response.setHeader("Cache-Control", "no-store");
                    response.setHeader("Pragma", "no-cache");
                    response.setDateHeader("Expires", 0);
                    out.print(json);
                } finally {
                    out.close();
                }
                return;
            }
            switch (subUri) {
                //<editor-fold desc="login">
                case "login": {
                    String username = paramsJson.getString("username");
                    String password = paramsJson.getString("password");
                    UserController controller = new UserController();
                    ArrayList<UserInfo> result = controller.getUserInfo(username, null, -1, null, 1, 1);
                    if (null != result && result.size() > 0) {
                        UserInfo info = result.get(0);
                        if (password.equals(info.getPassword())) {
                            json = Units.objectToJson(0, "", info);
                            session.setAttribute("user", info);
                        } else {
                            json = Units.objectToJson(-1, "用户名或密码输入错误!", null);
                        }
                    } else {
                        json = Units.objectToJson(-1, "用户名不存在!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getUserList">
                case "getUserList": {
                    String username = paramsJson.getString("username");
                    String stationName = paramsJson.getString("stationName");
                    int pageSize = paramsJson.getIntValue("pageSize");
                    int pageIndex = paramsJson.getIntValue("pageIndex");
                    UserController controller = new UserController();
                    ArrayList<UserInfo> result = controller.getUserInfo(username, null, -1, stationName, pageSize, pageIndex);
                    if (null != result && result.size() > 0) {
                        json = Units.listToJson(result, UserInfo.getRecordCount());
                    } else {
                        json = Units.objectToJson(-1, "记录为空", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="addUserInfo">
                case "addUserInfo": {
                    String username = paramsJson.getString("username");
                    String password = paramsJson.getString("password");
                    int stationId = paramsJson.getIntValue("stationId");
                    int userType = paramsJson.getIntValue("userType");
                    UserController controller = new UserController();
                    int result = controller.addUserInfo(username, password, stationId, userType);
                    if (result == 0) {
                        json = Units.objectToJson(result, "添加成功!", null);
                    } else if (result == 1) {
                        json = Units.objectToJson(result, "用户名已存在!", null);
                    } else {
                        json = Units.objectToJson(result, "添加失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="deleteUserInfo">
                case "deleteUserInfo": {
                    int userId = paramsJson.getIntValue("userId");
                    UserController controller = new UserController();
                    int result = controller.deleteUserInfo(userId);
                    if (result == 0) {
                        json = Units.objectToJson(result, "删除成功!", null);
                    } else if (result == 1) {
                        json = Units.objectToJson(result, "该用户不能删除!", null);
                    } else {
                        json = Units.objectToJson(result, "删除失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getOrderInfo">
                case "getOrderInfo": {
                    String cardNum = paramsJson.getString("cardNum");
                    int stationId = paramsJson.getIntValue("stationId");
                    OrderController controller = new OrderController();
                    ArrayList<com.cn.bean.OrderInfo> orderInfos = controller.getOrderInfo(cardNum, stationId, Units.getNowDate());
                    if (null != orderInfos && orderInfos.size() > 0) {
                        json = Units.objectToJson(0, "", orderInfos.get(0));
                    } else {
                        json = Units.objectToJson(-1, "没有对应的周转卡号计划单, 请核对!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="inputOrder(录入订单)">
                case "inputOrder": {
                    String username = paramsJson.getString("username");
                    int orderId = paramsJson.getIntValue("orderId");
//                    int isGuanShu = paramsJson.getIntValue("isGuanShu");
                    int isGuanShu = Integer.valueOf((null != paramsJson.getString("isGuanShu")) ? (paramsJson.getString("isGuanShu")) : ("0"));
                    String cardNum = paramsJson.getString("cardNum");
                    String data = paramsJson.getString("data");
                    StationController stationController = new StationController();
                    StationInfo info = stationController.getStationInfoWithUserName(username);
                    UserController userController = new UserController();
                    UserInfo userInfo = userController.getUserInfo(username, null, -1, null, 1, 1).get(0);

                    OrderController controller = new OrderController();
                    int result = controller.addStationData(info, username, cardNum, orderId, isGuanShu, data);
                    if (result == 0) {
                        if (info.getStationIndex() == 1) {
//                            ArrayList<OrderInfo> orderInfos = controller.getFirstStationOrderInfo(Units.getNowDate(), -1, 1, 1);
                            ArrayList<com.cn.bean.OrderInfo> orderInfos;
                            if (userInfo.getUserType() == -1) {
                                orderInfos = controller.getAutoXiaLiaoOrderInfo(userInfo.getUserId(), 1, 1);
                            } else {
                                orderInfos = controller.getFirstStationOrderInfo(Units.getNowDate(), -1, userInfo.getUserId(), 0, 1, 1);
                            }
                            if (null != orderInfos && orderInfos.size() > 0) {
                                json = Units.objectToJson(1, "", orderInfos.get(0));
                                String finishedSerial = controller.getFinishedSerial(orderInfos.get(0).getOrderId(), info.getStationId());
                                if (null != finishedSerial && finishedSerial.length() > 0) {
                                    json = String.format(json, finishedSerial);
                                } else {
                                    json = String.format(json, "暂无已完成序号");
                                }
                                controller.updateReadUser(orderInfos.get(0).getOrderId(), userInfo.getUserId());
                            } else {
                                json = Units.objectToJson(-1, "暂无需要执行的计划单!", null);
                            }
                        } else {
                            json = Units.objectToJson(0, "数据添加成功!", null);
                        }
                    } else if (result == 1) {
                        json = Units.objectToJson(-1, "周转卡号正在使用中!", null);
                    } else if (result == 2) {
                        json = Units.objectToJson(-1, "订单数据已提交!", null);
                    } else {
                        json = Units.objectToJson(-1, "数据添加失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getOrderList">
                case "getOrderList": {
                    String productCommand = paramsJson.getString("productCommand");
                    String productName = paramsJson.getString("productName");
                    String productCode = paramsJson.getString("productCode");
                    int productLineId = Integer.valueOf((null == paramsJson.getString("productLineId")) ? ("-1") : (paramsJson.getString("productLineId")));
                    String finishedTime = paramsJson.getString("planFinishedTime");
                    String cardNum = paramsJson.getString("cardNum");
                    int isFinished = Integer.valueOf((null == paramsJson.getString("isFinished")) ? ("-1") : (paramsJson.getString("isFinished")));
                    int isStorage = Integer.valueOf((null == paramsJson.getString("isStorage")) ? ("-1") : (paramsJson.getString("isStorage")));
                    int pageSize = paramsJson.getIntValue("pageSize");
                    int pageIndex = paramsJson.getIntValue("pageIndex");
                    OrderController controller = new OrderController();
                    ArrayList<OrderInfo> result = controller.getOrderList(productCommand, productName, productCode, productLineId, isFinished, -1, isStorage, -1, finishedTime, cardNum, pageSize, pageIndex);
                    if (null != result && result.size() > 0) {
                        json = Units.listToJson(result, OrderInfo.getRecordCount());
                    } else {
                        json = Units.objectToJson(-1, "记录为空", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="orderImport">
                case "orderImport": {
                    String loadpath = getServletContext().getRealPath("/").replace("\\", "/") + "excelFile/"; //上传文件存放目录
                    String fileName = paramsJson.getString("fileName");
                    int productLineId = paramsJson.getIntValue("productLineId");
                    int isEmergency = paramsJson.getIntValue("isEmergency");
                    OrderController controller = new OrderController();
                    int result;
                    if (productLineId == 4) {
                        result = controller.importOrder(loadpath + fileName, productLineId, isEmergency, 1);
                    } else {
                        result = controller.importOrder(loadpath + fileName, productLineId, isEmergency, 0);
                    }
                    if (result == 0) {
                        json = Units.objectToJson(0, "导入成功!", null);
                    } else {
                        json = Units.objectToJson(0, "存在重复数据, 导入失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="orderStorage">
                case "orderStorage": {
                    int userId = paramsJson.getIntValue("orderId");
                    OrderController controller = new OrderController();
                    int result = controller.orderStorage(userId);
                    if (result == 0) {
                        json = Units.objectToJson(result, "入库成功!", null);
                    } else if (result == 1) {
                        json = Units.objectToJson(result, "订单未完成, 不能入库!", null);
                    } else {
                        json = Units.objectToJson(result, "入库失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="orderDelete">
                case "orderDelete": {
                    int userId = paramsJson.getIntValue("orderId");
                    OrderController controller = new OrderController();
                    int result = controller.orderDelete(userId);
                    if (result == 0) {
                        json = Units.objectToJson(result, "删除成功!", null);
                    } else if (result == 1) {
                        json = Units.objectToJson(result, "已有工位在进行, 不能删除!", null);
                    } else {
                        json = Units.objectToJson(result, "删除失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getStationList">
                case "getStationList": {
                    int orderId = paramsJson.getIntValue("orderId");
                    StationController controller = new StationController();
                    ArrayList<StationInfo> result = controller.getStationList(orderId);
                    if (null != result && result.size() > 0) {
                        json = Units.listToJson(result, 0);
                    } else {
                        json = Units.objectToJson(-1, "记录为空", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getStationDetail">
                case "getStationDetail": {
                    int orderId = paramsJson.getIntValue("orderId");
                    int stationId = paramsJson.getIntValue("stationId");
                    StationController controller = new StationController();
                    StationInfo info = controller.getStationInfo(orderId, stationId);
                    if (null != info) {
                        json = Units.objectToJson(0, "", info);
                    } else {
                        json = Units.objectToJson(-1, "该订单没有该工位, 或该工位未完成!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getTechnicalList">
                case "getTechnicalList": {
                    int productLineId = paramsJson.getIntValue("productLineId");
                    String productCode = paramsJson.getString("productCode");
                    int pageSize = paramsJson.getIntValue("pageSize");
                    int pageIndex = paramsJson.getIntValue("pageIndex");
                    TechnicalController controller = new TechnicalController();
                    if (productLineId == 1) {
                        ArrayList<TechnicalGaoYaGuan> result = controller.getTechnicalList_GaoYaGuan(productCode, pageIndex, pageSize);
                        if (null != result && result.size() > 0) {
                            json = Units.listToJson(result, TechnicalGaoYaGuan.getRecordCount());
                        } else {
                            json = Units.objectToJson(-1, "记录为空", null);
                        }
                    }
                    if (productLineId == 2) {
                        ArrayList<TechnicalYaZhuang> result = controller.getTechnicalList_YaZhuang(productCode, pageIndex, pageSize);
                        if (null != result && result.size() > 0) {
                            json = Units.listToJson(result, TechnicalYaZhuang.getRecordCount());
                        } else {
                            json = Units.objectToJson(-1, "记录为空", null);
                        }
                    }
                    if (productLineId == 3) {
                        ArrayList<TechnicalChengXing> result = controller.getTechnicalList_ChengXing(productCode, pageIndex, pageSize);
                        if (null != result && result.size() > 0) {
                            json = Units.listToJson(result, TechnicalChengXing.getRecordCount());
                        } else {
                            json = Units.objectToJson(-1, "记录为空", null);
                        }
                    }
                    if (productLineId == 4) {
                        ArrayList<TechnicalGuanShu> result = controller.getTechnicalList_GuanShu(productCode, pageIndex, pageSize);
                        if (null != result && result.size() > 0) {
                            json = Units.listToJson(result, TechnicalGuanShu.getRecordCount());
                        } else {
                            json = Units.objectToJson(-1, "记录为空", null);
                        }
                    }
                    if (productLineId == 99) {
                        ArrayList<TechnicalZhuan> result = controller.getTechnicalList_Zhuan(productCode, pageIndex, pageSize);
                        if (null != result && result.size() > 0) {
                            json = Units.listToJson(result, TechnicalZhuan.getRecordCount());
                        } else {
                            json = Units.objectToJson(-1, "记录为空", null);
                        }
                    }
//                    System.out.println(json);
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="addTechnicalYaZhuang">
                case "addTechnicalYaZhuang": {
                    int productLineId = paramsJson.getIntValue("productLineId");
                    if (productLineId == 2) {
                        String productCode = paramsJson.getString("productCode");
                        int xiaLiaoNextStation = paramsJson.getIntValue("xiaLiaoNextStation");
                        int caiBiaoNextStation = paramsJson.getIntValue("caiBiaoNextStation");
                        int daMaNextStation = paramsJson.getIntValue("daMaNextStation");
                        int yuZhuangNextStation = paramsJson.getIntValue("yaZhuangNextStation");
                        int huaXianNextStation = paramsJson.getIntValue("huaXianNextStation");
                        int huGuanNextStation = paramsJson.getIntValue("huGuanNextStation");
                        int baoZhuangNextStation = paramsJson.getIntValue("baoZhuangNextStation");
                        TechnicalController controller = new TechnicalController();
                        int result = controller.addTechnical_YaZhuang(productCode, xiaLiaoNextStation, caiBiaoNextStation, daMaNextStation, yuZhuangNextStation, huaXianNextStation, huGuanNextStation, baoZhuangNextStation);
                        if (result == 0) {
                            json = Units.objectToJson(result, "添加成功!", null);
                        } else if (result == 1) {
                            json = Units.objectToJson(result, "物料编号已存在!", null);
                        } else {
                            json = Units.objectToJson(result, "添加失败!", null);
                        }
                    } else {
                        json = Units.objectToJson(-1, "添加失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="updateTechnicalYaZhuang">
                case "updateTechnicalYaZhuang": {
                    int technicalId = paramsJson.getIntValue("technicalId");
                    int xiaLiaoNextStation = paramsJson.getIntValue("xiaLiaoNextStation");
                    int caiBiaoNextStation = paramsJson.getIntValue("caiBiaoNextStation");
                    int daMaNextStation = paramsJson.getIntValue("daMaNextStation");
                    int yuZhuangNextStation = paramsJson.getIntValue("yaZhuangNextStation");
                    int huaXianNextStation = paramsJson.getIntValue("huaXianNextStation");
                    int huGuanNextStation = paramsJson.getIntValue("huGuanNextStation");
                    int baoZhuangNextStation = paramsJson.getIntValue("baoZhuangNextStation");
                    TechnicalController controller = new TechnicalController();
                    int result = controller.updateTechnical_YaZhuang(technicalId, xiaLiaoNextStation, caiBiaoNextStation, daMaNextStation, yuZhuangNextStation, huaXianNextStation, huGuanNextStation, baoZhuangNextStation);
                    if (result == 0) {
                        json = Units.objectToJson(result, "修改成功!", null);
                    } else {
                        json = Units.objectToJson(result, "修改失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="addTechnicalGaoYaGuan">
                case "addTechnicalGaoYaGuan": {
                    int productLineId = paramsJson.getIntValue("productLineId");
                    if (productLineId == 1) {
                        String productCode = paramsJson.getString("productCode_g");
                        int xiaLiaoNextStation = paramsJson.getIntValue("xiaLiaoNextStation_g");
                        int jianYanNextStation = paramsJson.getIntValue("jianYanNextStation_g");
                        int daMaNextStation = paramsJson.getIntValue("daMaNextStation_g");
                        int yuZhuangNextStation = paramsJson.getIntValue("yuZhuangNextStation_g");
                        int kouYaNextStation = paramsJson.getIntValue("kouYaNextStation_g");
                        int daYaNextStation = paramsJson.getIntValue("daYaNextStation_g");
                        int huaXianNextStation = paramsJson.getIntValue("huaXianNextStation_g");
                        int baoZhuangNextStation = paramsJson.getIntValue("baoZhuangNextStation_g");
                        TechnicalController controller = new TechnicalController();
                        int result = controller.addTechnical_GaoYaGuan(productCode, xiaLiaoNextStation, jianYanNextStation, daMaNextStation, yuZhuangNextStation, kouYaNextStation, daYaNextStation, huaXianNextStation, baoZhuangNextStation);
                        if (result == 0) {
                            json = Units.objectToJson(result, "添加成功!", null);
                        } else if (result == 1) {
                            json = Units.objectToJson(result, "物料编号已存在!", null);
                        } else {
                            json = Units.objectToJson(result, "添加失败!", null);
                        }
                    } else {
                        json = Units.objectToJson(-1, "添加失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="updateTechnicalGaoYaGuan">
                case "updateTechnicalGaoYaGuan": {
                    int technicalId = paramsJson.getIntValue("technicalId_g");
                    String productCode = paramsJson.getString("productCode_g");
                    int xiaLiaoNextStation = paramsJson.getIntValue("xiaLiaoNextStation_g");
                    int jianYanNextStation = paramsJson.getIntValue("jianYanNextStation_g");
                    int daMaNextStation = paramsJson.getIntValue("daMaNextStation_g");
                    int yuZhuangNextStation = paramsJson.getIntValue("yuZhuangNextStation_g");
                    int kouYaNextStation = paramsJson.getIntValue("kouYaNextStation_g");
                    int daYaNextStation = paramsJson.getIntValue("daYaNextStation_g");
                    int huaXianNextStation = paramsJson.getIntValue("huaXianNextStation_g");
                    int baoZhuangNextStation = paramsJson.getIntValue("baoZhuangNextStation_g");
                    TechnicalController controller = new TechnicalController();
                    int result = controller.updateTechnical_GaoYaGuan(technicalId, xiaLiaoNextStation, jianYanNextStation, daMaNextStation, yuZhuangNextStation, kouYaNextStation, daYaNextStation, huaXianNextStation, baoZhuangNextStation);
                    if (result == 0) {
                        json = Units.objectToJson(result, "修改成功!", null);
                    } else {
                        json = Units.objectToJson(result, "修改失败!", null);
                    }

                    break;
                }
                //</editor-fold>

                //<editor-fold desc="addTechnicalChengXing">
                case "addTechnicalChengXing": {
                    int productLineId = paramsJson.getIntValue("productLineId");
                    if (productLineId == 3) {
                        String productCode = paramsJson.getString("productCode_c");
                        int xiaLiaoNextStation = paramsJson.getIntValue("xiaLiaoNextStation_c");
                        int daMaNextStation = paramsJson.getIntValue("daMaNextStation_c");
                        int huaXianNextStation = paramsJson.getIntValue("huaXianNextStation_c");
                        int chengXingNextStation = paramsJson.getIntValue("chengXingNextStation_c");
                        int jiaReLengQueNextStation = paramsJson.getIntValue("jiaReLengQueNextStation_c");
                        int jieChangNextStation = paramsJson.getIntValue("jieChangNextStation_c");
                        TechnicalController controller = new TechnicalController();
                        int result = controller.addTechnical_ChengXing(productCode, xiaLiaoNextStation, daMaNextStation, huaXianNextStation, chengXingNextStation, jiaReLengQueNextStation, jieChangNextStation);
                        if (result == 0) {
                            json = Units.objectToJson(result, "添加成功!", null);
                        } else if (result == 1) {
                            json = Units.objectToJson(result, "物料编号已存在!", null);
                        } else {
                            json = Units.objectToJson(result, "添加失败!", null);
                        }
                    } else {
                        json = Units.objectToJson(-1, "添加失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="updateTechnicalChengXing">
                case "updateTechnicalChengXing": {
                    int technicalId = paramsJson.getIntValue("technicalId_c");
                    int xiaLiaoNextStation = paramsJson.getIntValue("xiaLiaoNextStation_c");
                    int daMaNextStation = paramsJson.getIntValue("daMaNextStation_c");
                    int huaXianNextStation = paramsJson.getIntValue("huaXianNextStation_c");
                    int chengXingNextStation = paramsJson.getIntValue("chengXingNextStation_c");
                    int jiaReLengQueNextStation = paramsJson.getIntValue("jiaReLengQueNextStation_c");
                    int jieChangNextStation = paramsJson.getIntValue("jieChangNextStation_c");
                    TechnicalController controller = new TechnicalController();
                    int result = controller.updateTechnical_ChengXing(technicalId, xiaLiaoNextStation, daMaNextStation, huaXianNextStation, chengXingNextStation, jiaReLengQueNextStation, jieChangNextStation);
                    if (result == 0) {
                        json = Units.objectToJson(result, "修改成功!", null);
                    } else {
                        json = Units.objectToJson(result, "修改失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="addTechnicalZhuan">
                case "addTechnicalZhuan": {
                    int productLineId = paramsJson.getIntValue("productLineId");
                    if (productLineId == 99) {
                        String productCode = paramsJson.getString("productCode_z");
                        int chengXinginputInfoNextStation = paramsJson.getIntValue("chengXingInputInfoNextStation_c");
                        int chengXingxiaLiaoNextStation = paramsJson.getIntValue("chengXingXiaLiaoNextStation_c");
                        int chengXingdaMaNextStation = paramsJson.getIntValue("chengXingDaMaNextStation_c");
                        int chengXinghuaXianNextStation = paramsJson.getIntValue("chengXingHuaXianNextStation_c");
                        int chengXingchengXingNextStation = paramsJson.getIntValue("chengXingChengXingNextStation_c");
                        int chengXingjiaReLengQueNextStation = paramsJson.getIntValue("chengXingJieReLengQueNextStation_c");
                        int chengXingjieChangNextStation = paramsJson.getIntValue("chengXingJieChangNextStation_c");
                        int yaZhuangxiaLiaoNextStation = paramsJson.getIntValue("yaZhuangxiaLiaoNextStation");
                        int yaZhuangcaiBiaoNextStation = paramsJson.getIntValue("yaZhuangcaiBiaoNextStation");
                        int yaZhuangdaMaNextStation = paramsJson.getIntValue("yaZhuangdaMaNextStation");
                        int yaZhuangyuZhuangNextStation = paramsJson.getIntValue("yaZhuangyaZhuangNextStation");
                        int yaZhuanghuaXianNextStation = paramsJson.getIntValue("yaZhuanghuaXianNextStation");
                        int yaZhuanghuGuanNextStation = paramsJson.getIntValue("yaZhuanghuGuanNextStation");
                        int yaZhuangbaoZhuangNextStation = paramsJson.getIntValue("yaZhuangbaoZhuangNextStation");
                        TechnicalController controller = new TechnicalController();
                        int result = controller.addTechnical_Zhuan(productCode, chengXinginputInfoNextStation, chengXingxiaLiaoNextStation, chengXingdaMaNextStation, chengXinghuaXianNextStation, chengXingchengXingNextStation, chengXingjiaReLengQueNextStation, chengXingjieChangNextStation, yaZhuangxiaLiaoNextStation, yaZhuangcaiBiaoNextStation, yaZhuangdaMaNextStation, yaZhuangyuZhuangNextStation, yaZhuanghuaXianNextStation, yaZhuanghuGuanNextStation, yaZhuangbaoZhuangNextStation);
                        if (result == 0) {
                            json = Units.objectToJson(result, "添加成功!", null);
                        } else if (result == 1) {
                            json = Units.objectToJson(result, "物料编号已存在!", null);
                        } else {
                            json = Units.objectToJson(result, "添加失败!", null);
                        }
                    } else {
                        json = Units.objectToJson(-1, "添加失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="updateTechnicalZhuan">
                case "updateTechnicalZhuan": {
                    int technicalId = paramsJson.getIntValue("technicalId_z");
                    int chengXinginputInfoNextStation = paramsJson.getIntValue("chengXingInputInfoNextStation_c");
                    int chengXingxiaLiaoNextStation = paramsJson.getIntValue("chengXingXiaLiaoNextStation_c");
                    int chengXingdaMaNextStation = paramsJson.getIntValue("chengXingDaMaNextStation_c");
                    int chengXinghuaXianNextStation = paramsJson.getIntValue("chengXingHuaXianNextStation_c");
                    int chengXingchengXingNextStation = paramsJson.getIntValue("chengXingChengXingNextStation_c");
                    int chengXingjiaReLengQueNextStation = paramsJson.getIntValue("chengXingJieReLengQueNextStation_c");
                    int chengXingjieChangNextStation = paramsJson.getIntValue("chengXingJieChangNextStation_c");
                    int yaZhuangxiaLiaoNextStation = paramsJson.getIntValue("yaZhuangxiaLiaoNextStation");
                    int yaZhuangcaiBiaoNextStation = paramsJson.getIntValue("yaZhuangcaiBiaoNextStation");
                    int yaZhuangdaMaNextStation = paramsJson.getIntValue("yaZhuangdaMaNextStation");
                    int yaZhuangyuZhuangNextStation = paramsJson.getIntValue("yaZhuangyaZhuangNextStation");
                    int yaZhuanghuaXianNextStation = paramsJson.getIntValue("yaZhuanghuaXianNextStation");
                    int yaZhuanghuGuanNextStation = paramsJson.getIntValue("yaZhuanghuGuanNextStation");
                    int yaZhuangbaoZhuangNextStation = paramsJson.getIntValue("yaZhuangbaoZhuangNextStation");
                    TechnicalController controller = new TechnicalController();
                    int result = controller.updateTechnical_Zhuan(technicalId, chengXinginputInfoNextStation, chengXingxiaLiaoNextStation, chengXingdaMaNextStation, chengXinghuaXianNextStation, chengXingchengXingNextStation, chengXingjiaReLengQueNextStation, chengXingjieChangNextStation, yaZhuangxiaLiaoNextStation, yaZhuangcaiBiaoNextStation, yaZhuangdaMaNextStation, yaZhuangyuZhuangNextStation, yaZhuanghuaXianNextStation, yaZhuanghuGuanNextStation, yaZhuangbaoZhuangNextStation);
                    if (result == 0) {
                        json = Units.objectToJson(result, "修改成功!", null);
                    } else {
                        json = Units.objectToJson(result, "修改失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="addTechnicalGuanShu">
                case "addTechnicalGuanShu": {
                    int productLineId = paramsJson.getIntValue("productLineId");
                    if (productLineId == 4) {
                        String productCode = paramsJson.getString("productCode_gs");
                        int xiaLiaoNextStation = paramsJson.getIntValue("xiaLiaoNextStation_gs");
                        int caiBiaoNextStation = paramsJson.getIntValue("caiBiaoNextStation_gs");
                        int yaZhuangNextStation = paramsJson.getIntValue("yaZhuangNextStation_gs");
                        int huaXianNextStation = paramsJson.getIntValue("huaXianNextStation_gs");
                        TechnicalController controller = new TechnicalController();
                        int result = controller.addTechnical_GuanShu(productCode, xiaLiaoNextStation, caiBiaoNextStation, yaZhuangNextStation, huaXianNextStation);
                        if (result == 0) {
                            json = Units.objectToJson(result, "添加成功!", null);
                        } else if (result == 1) {
                            json = Units.objectToJson(result, "物料编号已存在!", null);
                        } else {
                            json = Units.objectToJson(result, "添加失败!", null);
                        }
                    } else {
                        json = Units.objectToJson(-1, "生产线选择错误!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="updateTechnicalGuanShu">
                case "updateTechnicalGuanShu": {
                    int technicalId = paramsJson.getIntValue("technicalId");
                    int xiaLiaoNextStation = paramsJson.getIntValue("xiaLiaoNextStation_gs");
                    int caiBiaoNextStation = paramsJson.getIntValue("caiBiaoNextStation_gs");
                    int yaZhuangNextStation = paramsJson.getIntValue("yaZhuangNextStation_gs");
                    int huaXianNextStation = paramsJson.getIntValue("huaXianNextStation_gs");
                    TechnicalController controller = new TechnicalController();
                    int result = controller.updateTechnical_GuanShu(technicalId, xiaLiaoNextStation, caiBiaoNextStation, yaZhuangNextStation, huaXianNextStation);
                    if (result == 0) {
                        json = Units.objectToJson(result, "修改成功!", null);
                    } else {
                        json = Units.objectToJson(result, "修改失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="deleteTechnical">
                case "deleteTechnical": {
                    int technicalId = paramsJson.getIntValue("technicalId");
                    int productLineId = paramsJson.getIntValue("productLineId");
                    TechnicalController controller = new TechnicalController();
                    int result = controller.deleteTechnical(productLineId, technicalId);
                    if (result == 0) {
                        json = Units.objectToJson(result, "删除成功!", null);
                    } else {
                        json = Units.objectToJson(result, "删除失败!", null);
                    }
                    break;
                }
                //</editor-fold>
            }
        } catch (Exception e) {
            logger.info(subUri);
            logger.error("参数错误", e);
            json = Units.objectToJson(-1, "输入参数错误!", e.toString());
        }

        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            out.print(json.replace("\\n", "").replace("\r", ""));
//            System.out.println(json);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String params = request.getParameter("params");
//        String params = new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
        processRequest(request, response, params);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String params = getRequestPostStr(request);
        processRequest(request, response, params);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    private byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }

        return new String(buffer, charEncoding);
    }// </editor-fold>

}
