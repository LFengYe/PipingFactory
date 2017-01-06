/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.controller;

import com.cn.bean.TechnicalChengXing;
import com.cn.bean.TechnicalGaoYaGuan;
import com.cn.bean.TechnicalGuanShu;
import com.cn.bean.TechnicalYaZhuang;
import com.cn.bean.TechnicalZhuan;
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
public class TechnicalController {

    private static final Logger logger = Logger.getLogger(TechnicalController.class);

    /**
     * 删除工艺
     * @param productLineId
     * @param technicalID
     * @return 
     */
    public int deleteTechnical(int productLineId, int technicalID) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbDeleteTechnical](?, ?, ?)}");
            statement.setInt("productLineId", productLineId);
            statement.setInt("technicalId", technicalID);
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
     * 添加压装工艺
     *
     * @param productCode
     * @param xiaLiaoNextStation
     * @param caiBiaoNextStation
     * @param daMaNextStation
     * @param yuZhuangNextStation
     * @param huaXianNextStation
     * @param huGuanNextStation
     * @param baoZhuangNextStation
     * @return
     */
    public int addTechnical_YaZhuang(String productCode, int xiaLiaoNextStation, int caiBiaoNextStation, int daMaNextStation, int yuZhuangNextStation,
            int huaXianNextStation, int huGuanNextStation, int baoZhuangNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbAddTechnicalYaZhuang](?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setString("productCode", productCode);
            statement.setInt("xiaLiaoNextStation", xiaLiaoNextStation);
            statement.setInt("caiBiaoNextStation", caiBiaoNextStation);
            statement.setInt("daMaNextStation", daMaNextStation);
            statement.setInt("yuZhuangNextStation", yuZhuangNextStation);
            statement.setInt("huaXianNextStation", huaXianNextStation);
            statement.setInt("huGuanNextStation", huGuanNextStation);
            statement.setInt("baoZhuangNextStation", baoZhuangNextStation);
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
     * 压装工艺更新
     *
     * @param technicalId
     * @param xiaLiaoNextStation
     * @param caiBiaoNextStation
     * @param daMaNextStation
     * @param yuZhuangNextStation
     * @param huaXianNextStation
     * @param huGuanNextStation
     * @param baoZhuangNextStation
     * @return
     */
    public int updateTechnical_YaZhuang(int technicalId, int xiaLiaoNextStation, int caiBiaoNextStation, int daMaNextStation, int yuZhuangNextStation,
            int huaXianNextStation, int huGuanNextStation, int baoZhuangNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbUpdateTechnicalYaZhuang](?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt("technicalId", technicalId);
            statement.setInt("xiaLiaoNextStation", xiaLiaoNextStation);
            statement.setInt("caiBiaoNextStation", caiBiaoNextStation);
            statement.setInt("daMaNextStation", daMaNextStation);
            statement.setInt("yuZhuangNextStation", yuZhuangNextStation);
            statement.setInt("huaXianNextStation", huaXianNextStation);
            statement.setInt("huGuanNextStation", huGuanNextStation);
            statement.setInt("baoZhuangNextStation", baoZhuangNextStation);
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
     * 获取压装工艺
     *
     * @param productCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<TechnicalYaZhuang> getTechnicalList_YaZhuang(String productCode, int pageIndex, int pageSize) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<TechnicalYaZhuang> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetTechnicalList](?, ?, ?, ?, ?)}");
            statement.setInt("productLineId", 2);
            statement.setString("productCode", productCode);
            statement.setInt("pageIndex", pageIndex);
            statement.setInt("pageSize", pageSize);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                TechnicalYaZhuang info = new TechnicalYaZhuang();
                info.setTechnicalID(set.getInt("TechnicalID"));
                info.setProductCode(set.getString("ProductCode"));
                info.setProductLength(set.getString("ProductLength"));
                info.setProductHuGuang(set.getString("HuGuangLength"));
                info.setFirstStation(set.getInt("FirstStation"));
                info.setXiaLiaoNextStation(set.getInt("XiaLiaoNextStation"));
                info.setCaiBiaoNextStation(set.getInt("CaiBiaoNextStation"));
                info.setDaMaNextStation(set.getInt("DaMaNextStation"));
                info.setYuZhuangNextStation(set.getInt("YuZhuangNextStation"));
                info.setHuaXianNextStation(set.getInt("HuaXianNextStation"));
                info.setHuGuanNextStation(set.getInt("HuGuanNextStation"));
                info.setBaoZhuangNextStation(set.getInt("BaoZhuangNextStation"));
                result.add(info);
            }
            TechnicalYaZhuang.setRecordCount(statement.getInt("recordCount"));
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
     * 添加高压管工艺
     *
     * @param productCode
     * @param xiaLiaoNextStation
     * @param jianYanNextStation
     * @param daMaNextStation
     * @param yuZhuangNextStation
     * @param kouYaNextStation
     * @param daYaNextStation
     * @param seBiaoNextStation
     * @param baoZhuangNextStation
     * @return
     */
    public int addTechnical_GaoYaGuan(String productCode, int xiaLiaoNextStation, int jianYanNextStation, int daMaNextStation, int yuZhuangNextStation,
            int kouYaNextStation, int daYaNextStation, int seBiaoNextStation, int baoZhuangNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbAddTechnicalGaoYaGuan](?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setString("productCode", productCode);
            statement.setInt("xiaLiaoNextStation", xiaLiaoNextStation);
            statement.setInt("jianYanNextStation", jianYanNextStation);
            statement.setInt("daMaNextStation", daMaNextStation);
            statement.setInt("yuZhuangNextStation", yuZhuangNextStation);
            statement.setInt("kouYaNextStation", kouYaNextStation);
            statement.setInt("daYaNextStation", daYaNextStation);
            statement.setInt("seBiaoNextStation", seBiaoNextStation);
            statement.setInt("baoZhuangNextStation", baoZhuangNextStation);
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
     * 更新高压管工艺
     *
     * @param technicalId
     * @param xiaLiaoNextStation
     * @param jianYanNextStation
     * @param daMaNextStation
     * @param yuZhuangNextStation
     * @param kouYaNextStation
     * @param daYaNextStation
     * @param seBiaoNextStation
     * @param baoZhuangNextStation
     * @return
     */
    public int updateTechnical_GaoYaGuan(int technicalId, int xiaLiaoNextStation, int jianYanNextStation, int daMaNextStation, int yuZhuangNextStation,
            int kouYaNextStation, int daYaNextStation, int seBiaoNextStation, int baoZhuangNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbUpdateTechnicalGaoYaGuan](?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt("technicalId", technicalId);
            statement.setInt("xiaLiaoNextStation", xiaLiaoNextStation);
            statement.setInt("jianYanNextStation", jianYanNextStation);
            statement.setInt("daMaNextStation", daMaNextStation);
            statement.setInt("yuZhuangNextStation", yuZhuangNextStation);
            statement.setInt("kouYaNextStation", kouYaNextStation);
            statement.setInt("daYaNextStation", daYaNextStation);
            statement.setInt("seBiaoNextStation", seBiaoNextStation);
            statement.setInt("baoZhuangNextStation", baoZhuangNextStation);
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
     * 获取高压管工艺
     *
     * @param productCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<TechnicalGaoYaGuan> getTechnicalList_GaoYaGuan(String productCode, int pageIndex, int pageSize) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<TechnicalGaoYaGuan> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetTechnicalList](?, ?, ?, ?, ?)}");
            statement.setInt("productLineId", 1);
            statement.setString("productCode", productCode);
            statement.setInt("pageIndex", pageIndex);
            statement.setInt("pageSize", pageSize);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                TechnicalGaoYaGuan info = new TechnicalGaoYaGuan();
                info.setTechnicalID(set.getInt("TechnicalID"));
                info.setProductCode(set.getString("ProductCode"));
                info.setProductLength(set.getString("ProductLength"));
                info.setProductHuGuang(set.getString("HuGuangLength"));
                info.setFirstStation(set.getInt("FirstStation"));
                info.setXiaLiaoNextStation(set.getInt("XiaLiaoNextStation"));
                info.setJianYanNextStation(set.getInt("JianYanNextStation"));
                info.setDaMaNextStation(set.getInt("DaMaNextStation"));
                info.setYuZhuangNextStation(set.getInt("YuZhuangNextStation"));
//                info.setHuaXianNextStation(set.getInt("HuaXianNextStation"));
                info.setKouYaNextStation(set.getInt("KouYaNextStation"));
                info.setDaYaNextStation(set.getInt("DaYaNextStation"));
//                info.setChuiSaoNextStation(set.getInt("ChuiSaoNextStation"));
                info.setSeBiaoNextStation(set.getInt("SeBiaoNextStation"));
                info.setBaoZhuangNextStation(set.getInt("BaoZhuangNextStation"));
                result.add(info);
            }
            TechnicalGaoYaGuan.setRecordCount(statement.getInt("recordCount"));
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
     * 添加成型工艺
     *
     * @param productCode
     * @param xiaLiaoNextStation
     * @param daMaNextStation
     * @param huaXianNextStation
     * @param chengXingNextStation
     * @param jiaReLengQueNextStation
     * @param jieChangNextStation
     * @return
     */
    public int addTechnical_ChengXing(String productCode, int xiaLiaoNextStation, int daMaNextStation,
            int huaXianNextStation, int chengXingNextStation, int jiaReLengQueNextStation, int jieChangNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbAddTechnicalChengXing](?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setString("productCode", productCode);
            statement.setInt("xiaLiaoNextStation", xiaLiaoNextStation);
            statement.setInt("daMaNextStation", daMaNextStation);
            statement.setInt("huaXianNextStation", huaXianNextStation);
            statement.setInt("chengXingNextStation", chengXingNextStation);
            statement.setInt("jiaReLengQueNextStation", jiaReLengQueNextStation);
            statement.setInt("jieChangNextStation", jieChangNextStation);
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
     * 更新成型工艺
     *
     * @param technicalId
     * @param xiaLiaoNextStation
     * @param daMaNextStation
     * @param huaXianNextStation
     * @param chengXingNextStation
     * @param jiaReLengQueNextStation
     * @param jieChangNextStation
     * @return
     */
    public int updateTechnical_ChengXing(int technicalId, int xiaLiaoNextStation, int daMaNextStation,
            int huaXianNextStation, int chengXingNextStation, int jiaReLengQueNextStation, int jieChangNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbUpdateTechnicalChengXing](?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt("technicalId", technicalId);
            statement.setInt("xiaLiaoNextStation", xiaLiaoNextStation);
            statement.setInt("daMaNextStation", daMaNextStation);
            statement.setInt("chengXingNextStation", chengXingNextStation);
            statement.setInt("huaXianNextStation", huaXianNextStation);
            statement.setInt("jiaReLengQueNextStation", jiaReLengQueNextStation);
            statement.setInt("jieChangNextStation", jieChangNextStation);
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
     * 获取成型工艺
     *
     * @param productCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<TechnicalChengXing> getTechnicalList_ChengXing(String productCode, int pageIndex, int pageSize) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<TechnicalChengXing> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetTechnicalList](?, ?, ?, ?, ?)}");
            statement.setInt("productLineId", 3);
            statement.setString("productCode", productCode);
            statement.setInt("pageIndex", pageIndex);
            statement.setInt("pageSize", pageSize);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                TechnicalChengXing info = new TechnicalChengXing();
                info.setTechnicalID(set.getInt("TechnicalID"));
                info.setProductCode(set.getString("ProductCode"));
                info.setProductLength(set.getString("ProductLength"));
                info.setProductHuGuang(set.getString("HuGuangLength"));
                info.setFirstStation(set.getInt("FirstStation"));
                info.setXiaLiaoNextStation(set.getInt("XiaLiaoNextStation"));
                info.setDaMaNextStation(set.getInt("DaMaNextStation"));
                info.setHuaXianNextStation(set.getInt("HuaXianNextStation"));
                info.setChengXingNextStation(set.getInt("ChengXingNextStation"));
                info.setJiaReLengQueNextStation(set.getInt("JiaReLengQueNextStation"));
                info.setJieChangNextStation(set.getInt("JieChangNextStation"));
                result.add(info);
            }
            TechnicalChengXing.setRecordCount(statement.getInt("recordCount"));
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
     * 添加转线工艺
     *
     * @param productCode
     * @param chengXinginputInfoNextStation
     * @param chengXingxiaLiaoNextStation
     * @param chengXingdaMaNextStation
     * @param chengXinghuaXianNextStation
     * @param chengXingchengXingNextStation
     * @param chengXingjiaReLengQueNextStation
     * @param chengXingjieChangNextStation
     * @param yaZhuangxiaLiaoNextStation
     * @param yaZhuangcaiBiaoNextStation
     * @param yaZhuangdaMaNextStation
     * @param yaZhuangyuZhuangNextStation
     * @param yaZhuanghuaXianNextStation
     * @param yaZhuanghuGuanNextStation
     * @param yaZhuangbaoZhuangNextStation
     * @return
     */
    public int addTechnical_Zhuan(String productCode, int chengXinginputInfoNextStation, int chengXingxiaLiaoNextStation,
            int chengXingdaMaNextStation,
            int chengXinghuaXianNextStation,
            int chengXingchengXingNextStation,
            int chengXingjiaReLengQueNextStation,
            int chengXingjieChangNextStation,
            int yaZhuangxiaLiaoNextStation,
            int yaZhuangcaiBiaoNextStation,
            int yaZhuangdaMaNextStation,
            int yaZhuangyuZhuangNextStation,
            int yaZhuanghuaXianNextStation,
            int yaZhuanghuGuanNextStation,
            int yaZhuangbaoZhuangNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbAddTechnicalZhuan](?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setString("productCode", productCode);
            statement.setInt("chengXinginputInfoNextStation", chengXinginputInfoNextStation);
            statement.setInt("chengXingxiaLiaoNextStation", chengXingxiaLiaoNextStation);
            statement.setInt("chengXingdaMaNextStation", chengXingdaMaNextStation);
            statement.setInt("chengXinghuaXianNextStation", chengXinghuaXianNextStation);
            statement.setInt("chengXingchengXingNextStation", chengXingchengXingNextStation);
            statement.setInt("chengXingjiaReLengQueNextStation", chengXingjiaReLengQueNextStation);
            statement.setInt("chengXingjieChangNextStation", chengXingjieChangNextStation);
            statement.setInt("yaZhuangxiaLiaoNextStation", yaZhuangxiaLiaoNextStation);
            statement.setInt("yaZhuangcaiBiaoNextStation", yaZhuangcaiBiaoNextStation);
            statement.setInt("yaZhuangdaMaNextStation", yaZhuangdaMaNextStation);
            statement.setInt("yaZhuangyuZhuangNextStation", yaZhuangyuZhuangNextStation);
            statement.setInt("yaZhuanghuaXianNextStation", yaZhuanghuaXianNextStation);
            statement.setInt("yaZhuanghuGuanNextStation", yaZhuanghuGuanNextStation);
            statement.setInt("yaZhuangbaoZhuangNextStation", yaZhuangbaoZhuangNextStation);
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
     * 更新转线工艺
     * @param technicalId
     * @param chengXinginputInfoNextStation
     * @param chengXingxiaLiaoNextStation
     * @param chengXingdaMaNextStation
     * @param chengXinghuaXianNextStation
     * @param chengXingchengXingNextStation
     * @param chengXingjiaReLengQueNextStation
     * @param chengXingjieChangNextStation
     * @param yaZhuangxiaLiaoNextStation
     * @param yaZhuangcaiBiaoNextStation
     * @param yaZhuangdaMaNextStation
     * @param yaZhuangyuZhuangNextStation
     * @param yaZhuanghuaXianNextStation
     * @param yaZhuanghuGuanNextStation
     * @param yaZhuangbaoZhuangNextStation
     * @return 
     */
    public int updateTechnical_Zhuan(int technicalId, int chengXinginputInfoNextStation, int chengXingxiaLiaoNextStation,
            int chengXingdaMaNextStation,
            int chengXinghuaXianNextStation,
            int chengXingchengXingNextStation,
            int chengXingjiaReLengQueNextStation,
            int chengXingjieChangNextStation,
            int yaZhuangxiaLiaoNextStation,
            int yaZhuangcaiBiaoNextStation,
            int yaZhuangdaMaNextStation,
            int yaZhuangyuZhuangNextStation,
            int yaZhuanghuaXianNextStation,
            int yaZhuanghuGuanNextStation,
            int yaZhuangbaoZhuangNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbUpdateTechnicalZhuan](?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt("technicalId", technicalId);
            statement.setInt("chengXinginputInfoNextStation", chengXinginputInfoNextStation);
            statement.setInt("chengXingxiaLiaoNextStation", chengXingxiaLiaoNextStation);
            statement.setInt("chengXingdaMaNextStation", chengXingdaMaNextStation);
            statement.setInt("chengXinghuaXianNextStation", chengXinghuaXianNextStation);
            statement.setInt("chengXingchengXingNextStation", chengXingchengXingNextStation);
            statement.setInt("chengXingjiaReLengQueNextStation", chengXingjiaReLengQueNextStation);
            statement.setInt("chengXingjieChangNextStation", chengXingjieChangNextStation);
            statement.setInt("yaZhuangxiaLiaoNextStation", yaZhuangxiaLiaoNextStation);
            statement.setInt("yaZhuangcaiBiaoNextStation", yaZhuangcaiBiaoNextStation);
            statement.setInt("yaZhuangdaMaNextStation", yaZhuangdaMaNextStation);
            statement.setInt("yaZhuangyuZhuangNextStation", yaZhuangyuZhuangNextStation);
            statement.setInt("yaZhuanghuaXianNextStation", yaZhuanghuaXianNextStation);
            statement.setInt("yaZhuanghuGuanNextStation", yaZhuanghuGuanNextStation);
            statement.setInt("yaZhuangbaoZhuangNextStation", yaZhuangbaoZhuangNextStation);
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
     * 获取转线工艺
     *
     * @param productCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<TechnicalZhuan> getTechnicalList_Zhuan(String productCode, int pageIndex, int pageSize) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<TechnicalZhuan> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetTechnicalList](?, ?, ?, ?, ?)}");
            statement.setInt("productLineId", 99);
            statement.setString("productCode", productCode);
            statement.setInt("pageIndex", pageIndex);
            statement.setInt("pageSize", pageSize);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                TechnicalZhuan info = new TechnicalZhuan();
                info.setTechnicalID(set.getInt("TechnicalID"));
                info.setProductCode(set.getString("ProductCode"));
                info.setProductLength(set.getString("ProductLength"));
                info.setProductHuGuang(set.getString("HuGuangLength"));
                info.setFirstStation(set.getInt("FirstStation"));
                info.setChengXingInputInfoNextStation(set.getInt("ChengXingInputInfoNextStation"));
                info.setChengXingXiaLiaoNextStation(set.getInt("ChengXingXiaLiaoNextStation"));
                info.setChengXingDaMaNextStation(set.getInt("ChengXingDaMaNextStation"));
                info.setChengXingHuaXianNextStation(set.getInt("ChengXingHuaXianNextStation"));
                info.setChengXingChengXingNextStation(set.getInt("ChengXingChengXingNextStation"));
                info.setChengXingJiaReLengQueNextStation(set.getInt("ChengXingJiaReLengQueNextStation"));
                info.setChengXingjieChangNextStation(set.getInt("ChengXingJieChangNextStation"));

                info.setYaZhuangXiaLiaoNextStation(set.getInt("YaZhuangXiaLiaoNextStation"));
                info.setYaZhuangCaiBiaoNextStation(set.getInt("YaZhuangCaiBiaoNextStation"));
                info.setYaZhuangDaMaNextStation(set.getInt("YaZhuangDaMaNextStation"));
                info.setYaZhuangYuZhuangNextStation(set.getInt("YaZhuangYuZhuangNextStation"));
                info.setYaZhuangHuaXianNextStation(set.getInt("YaZhuangHuaXianNextStation"));
                info.setYaZhuangHuGuanNextStation(set.getInt("YaZhuangHuGuanNextStation"));
                info.setYaZhuangBaoZhuangNextStation(set.getInt("YaZhuangBaoZhuangNextStation"));
                result.add(info);
            }
            TechnicalZhuan.setRecordCount(statement.getInt("recordCount"));
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
     * 获取管束工艺
     * @param productCode
     * @param pageIndex
     * @param pageSize
     * @return 
     */
    public ArrayList<TechnicalGuanShu> getTechnicalList_GuanShu(String productCode, int pageIndex, int pageSize) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        ArrayList<TechnicalGuanShu> result = null;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            result = new ArrayList<>();
            statement = conn.prepareCall("{call [tbGetTechnicalList](?, ?, ?, ?, ?)}");
            statement.setInt("productLineId", 4);
            statement.setString("productCode", productCode);
            statement.setInt("pageIndex", pageIndex);
            statement.setInt("pageSize", pageSize);
            statement.registerOutParameter("recordCount", Types.INTEGER);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                TechnicalGuanShu info = new TechnicalGuanShu();
                info.setTechnicalID(set.getInt("TechnicalID"));
                info.setProductCode(set.getString("ProductCode"));
                info.setProductLength(set.getString("ProductLength"));
                info.setProductHuGuang(set.getString("HuGuangLength"));
                info.setFirstStation(set.getInt("FirstStation"));
                info.setXiaLiaoNextStation(set.getInt("XiaLiaoNextStation"));
                info.setCaiBiaoNextStation(set.getInt("CaiBiaoNextStation"));
                info.setYaZhuangNextStation(set.getInt("YaZhuangNextStation"));
                info.setHuaXianNextStation(set.getInt("HuaXianNextStation"));
                result.add(info);
            }
            TechnicalGuanShu.setRecordCount(statement.getInt("recordCount"));
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
     * 添加管束工艺
     * @param productCode
     * @param xiaLiaoNextStation
     * @param caiBiaoNextStation
     * @param yaZhuangNextStation
     * @param huaXianNextStation
     * @return 
     */
    public int addTechnical_GuanShu(String productCode, int xiaLiaoNextStation, int caiBiaoNextStation, int yaZhuangNextStation, int huaXianNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbAddTechnicalGuanShu](?, ?, ?, ?, ?, ?)}");
            statement.setString("productCode", productCode);
            statement.setInt("xiaLiaoNextStation", xiaLiaoNextStation);
            statement.setInt("caiBiaoNextStation", caiBiaoNextStation);
            statement.setInt("yaZhuangNextStation", yaZhuangNextStation);
            statement.setInt("huaXianNextStation", huaXianNextStation);
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
     * 更新管束工艺
     * @param technicalId
     * @param xiaLiaoNextStation
     * @param caiBiaoNextStation
     * @param yaZhuangNextStation
     * @param huaXianNextStation
     * @return 
     */
    public int updateTechnical_GuanShu(int technicalId, int xiaLiaoNextStation, int caiBiaoNextStation, int yaZhuangNextStation, int huaXianNextStation) {
        DatabaseOpt opt;
        Connection conn = null;
        CallableStatement statement = null;
        int result = -1;
        try {
            opt = new DatabaseOpt();
            conn = opt.getConnect();
            statement = conn.prepareCall("{call [tbUpdateTechnicalGuanShu](?, ?, ?, ?, ?, ?)}");
            statement.setInt("technicalId", technicalId);
            statement.setInt("xiaLiaoNextStation", xiaLiaoNextStation);
            statement.setInt("caiBiaoNextStation", caiBiaoNextStation);
            statement.setInt("yaZhuangNextStation", yaZhuangNextStation);
            statement.setInt("huaXianNextStation", huaXianNextStation);
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
