/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.servlet;

import com.alibaba.fastjson.JSONObject;
import com.cn.bean.OrderInfo;
import com.cn.bean.StationInfo;
import com.cn.bean.UserInfo;
import com.cn.bean.Version;
import com.cn.controller.OrderController;
import com.cn.controller.StationController;
import com.cn.controller.UserController;
import com.cn.controller.VersionController;
import com.cn.util.Units;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class AppInterfaceServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AppInterfaceServlet.class);

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
        String json = null;
        try {
            System.out.println(subUri + ",params:" + params);
            JSONObject paramsJson = JSONObject.parseObject(params);
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
                            if (info.getStationIndex() == 1) {//第一个工序
                                OrderController controller1 = new OrderController();
                                ArrayList<OrderInfo> orderInfos;
                                if (info.getUserType() == -1) {//自动下料
                                    orderInfos = controller1.getAutoXiaLiaoOrderInfo(info.getUserId(), 1, 1);
                                    if (null != orderInfos && orderInfos.size() > 0) {
                                        json = Units.objectToJson(-99, "", orderInfos.get(0));
                                        String finishedSerial = controller1.getFinishedSerial(orderInfos.get(0).getOrderId(), info.getStationId());
                                        if (null != finishedSerial && finishedSerial.length() > 0) {
                                            json = String.format(json, finishedSerial);
                                        } else {
                                            json = String.format(json, "暂无已完成序号");
                                        }
                                        controller1.updateReadUser(orderInfos.get(0).getOrderId(), info.getUserId());
                                    } else {
                                        json = Units.objectToJson(-99, "暂无需要执行的计划单!", null);
                                    }
                                } else {//下料
                                    orderInfos = controller1.getFirstStationOrderInfo(Units.getNowDate(), -1, info.getUserId(), 0, 1, 1);
                                    if (null != orderInfos && orderInfos.size() > 0) {
                                        json = Units.objectToJson(0, "", orderInfos.get(0));
                                        String finishedSerial = controller1.getFinishedSerial(orderInfos.get(0).getOrderId(), info.getStationId());
                                        if (null != finishedSerial && finishedSerial.length() > 0) {
                                            json = String.format(json, finishedSerial);
                                        } else {
                                            json = String.format(json, "暂无已完成序号");
                                        }
                                        controller1.updateReadUser(orderInfos.get(0).getOrderId(), info.getUserId());
                                    } else {
                                        json = Units.objectToJson(-1, "暂无需要执行的计划单!", null);
                                    }
                                }
                            } else {//后续工序
                                json = Units.objectToJson(1, "", info);
                            }
                        } else {
                            json = Units.objectToJson(-1, "用户名或密码输入错误!", null);
                        }
                    } else {
                        json = Units.objectToJson(-1, "用户名不存在!", null);
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
                            ArrayList<OrderInfo> orderInfos;
                            if (userInfo.getUserType() == -1) {
                                orderInfos = controller.getAutoXiaLiaoOrderInfo(userInfo.getUserId(), 1, 1);
                            } else {
                                orderInfos = controller.getFirstStationOrderInfo(Units.getNowDate(), -1, userInfo.getUserId(), -1, 1, 1);
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
                        json = Units.objectToJson(-1, "订单数据已提交, 请勿重复提交!", null);
                    } else {
                        json = Units.objectToJson(-1, "数据添加失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getOrderInfo(根据周转卡号获取订单信息)">
                case "getOrderInfo": {
                    String cardNum = paramsJson.getString("cardNum");
                    int stationId = paramsJson.getIntValue("stationId");
                    OrderController controller = new OrderController();
                    ArrayList<OrderInfo> orderInfos = controller.getOrderInfo(cardNum, stationId, Units.getNowDate());
                    if (null != orderInfos && orderInfos.size() > 0) {
                        json = Units.objectToJson(0, "", orderInfos.get(0));
                        String finishedSerial = controller.getFinishedSerial(orderInfos.get(0).getOrderId(), stationId);
                        if (null != finishedSerial && finishedSerial.length() > 0) {
                            json = String.format(json, finishedSerial);
                        } else {
                            json = String.format(json, "暂无已完成序号");
                        }
                    } else {
                        json = Units.objectToJson(-1, "没有对应的周转卡号计划单, 请核对!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getOrderInfoWithId(根据订单ID, 获取订单信息)">
                case "getOrderInfoWithId": {
                    int orderId = paramsJson.getIntValue("orderId");
                    int stationId = paramsJson.getIntValue("stationId");
                    OrderController controller = new OrderController();
                    OrderInfo info = controller.getOrderInfoWithId(orderId);
                    if (null != info) {
                        json = Units.objectToJson(0, "", info);
                        String finishedSerial = controller.getFinishedSerial(info.getOrderId(), stationId);
                        if (null != finishedSerial && finishedSerial.length() > 0) {
                            json = String.format(json, finishedSerial);
                        } else {
                            json = String.format(json, "暂无已完成序号");
                        }
                    } else {
                        json = Units.objectToJson(-1, "没有对应的计划单, 请核对!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getCouldAutoXiaLiaoList(获取可能自动下料订单)">
                case "getCouldAutoXiaLiaoList": {
                    int pageSize = paramsJson.getIntValue("pageSize");
                    int pageIndex = paramsJson.getIntValue("pageIndex");
                    OrderController controller = new OrderController();
                    ArrayList<com.cn.bean.pc.OrderInfo> orderInfos = controller.getCouldAutoXiaLiaoOrder(pageSize, pageIndex);
                    if (null != orderInfos && orderInfos.size() > 0) {
                        json = Units.listToJson(orderInfos, com.cn.bean.pc.OrderInfo.getRecordCount());
                    } else {
                        json = Units.objectToJson(-1, "记录为空!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getHistoryOrderList(管束未完成订单)">
                case "getHistoryOrderList": {
                    int pageSize = paramsJson.getIntValue("pageSize");
                    int pageIndex = paramsJson.getIntValue("pageIndex");
                    OrderController controller = new OrderController();
                    ArrayList<com.cn.bean.pc.OrderInfo> orderInfos = controller.getUnFinishedList(pageSize, pageIndex);
                    if (null != orderInfos && orderInfos.size() > 0) {
                        json = Units.listToJson(orderInfos, com.cn.bean.pc.OrderInfo.getRecordCount());
                    } else {
                        json = Units.objectToJson(-1, "记录为空!", null);
                    }
//                    System.out.println(json);
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="getUserInfo(根据用户名获取用户信息)">
                case "getUserInfo": {
                    String username = paramsJson.getString("username");
                    UserController controller = new UserController();
                    ArrayList<UserInfo> result = controller.getUserInfo(username, null, -1, null, 1, 1);
                    if (null != result && result.size() > 0) {
                        UserInfo info = result.get(0);
                        json = Units.objectToJson(0, "", info);
                    } else {
                        json = Units.objectToJson(-1, "用户名不存在!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="orderIsGuanShu(订单管束状态修改)">
                case "orderIsGuanShu": {
                    int orderId = paramsJson.getIntValue("orderId");
                    int stationId = paramsJson.getIntValue("stationId");
                    int isGuanShu = paramsJson.getIntValue("isGuanShu");
                    OrderController controller = new OrderController();
                    int result = controller.updateGuanShuOrder(orderId, isGuanShu);
                    if (result == 0) {
                        /*管束暂未考虑自动下料*/
                        ArrayList<OrderInfo> orderInfos = controller.getFirstStationOrderInfo(Units.getNowDate(), orderId, 0, stationId, 1, 1);
                        if (null != orderInfos && orderInfos.size() > 0) {
                            json = Units.objectToJson(0, "", orderInfos.get(0));
                            String finishedSerial = controller.getFinishedSerial(orderId, stationId);
                            if (null != finishedSerial && finishedSerial.length() > 0) {
                                json = String.format(json, finishedSerial);
                            } else {
                                json = String.format(json, "暂无已完成序号");
                            }
                            controller.updateReadUser(orderInfos.get(0).getOrderId(), 0);
                        } else {
                            json = Units.objectToJson(-1, "暂无需要执行的计划单!", null);
                        }
                    } else if (result == 1) {
                        json = Units.objectToJson(1, "管束状态已确定, 不能修改!", null);
                    } else {
                        json = Units.objectToJson(-1, "数据获取失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="updateXiaLiaoState(下料状态修改)">
                case "updateXiaLiaoState": {
                    int orderId = paramsJson.getIntValue("orderId");
                    int xiaLiaoState = paramsJson.getIntValue("xiaLiaoState");
                    OrderController controller = new OrderController();
                    int result = controller.updateXiaLiaoState(orderId, xiaLiaoState);
                    if (result == 0) {
                        json = Units.objectToJson(0, "更新成功!", null);
                    } else {
                        json = Units.objectToJson(-1, "更新失败!", null);
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="getNewestVersion">
                case "getNewestVersion": {
                    int updateType = Integer.valueOf((null != paramsJson.getString("updateType")) ? paramsJson.getString("updateType") : "-1");
                    VersionController controller = new VersionController();
                    Version v = controller.getNewestVersion(updateType);
                    if (null != v) {
                        json = Units.objectToJson(0, "", v);
                    } else {
                        json = Units.objectToJson(-1, "服务器无版本更新", null);
                    }
//                    System.out.println("json:" + json);
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
            out.print(json);
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
    }

    /**
     * 根据request获取Post参数
     *
     * @param request
     * @return
     * @throws IOException (最大只能读取1133个字符, 原因还未找到)
     */
    private String getPostParameter(HttpServletRequest request) throws IOException {
        BufferedInputStream buf = null;
        int iContentLen = request.getContentLength();
        if (iContentLen > 0) {
            byte sContent[] = new byte[iContentLen];
            String sContent2 = null;
            try {
                buf = new BufferedInputStream(request.getInputStream(), iContentLen);
                buf.read(sContent, 0, sContent.length);
                sContent2 = new String(sContent, 0, iContentLen, "UTF-8");
//                System.out.println("content:" + sContent2 + ",len:" + sContent2.length());
            } catch (IOException e) {
                throw new IOException("Parse data error!", e);
            } finally {
                if (null != buf) {
                    buf.close();
                }
            }
            return sContent2;
        }
        return null;
    }// </editor-fold>

}
