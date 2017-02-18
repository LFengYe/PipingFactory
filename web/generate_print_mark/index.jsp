<%-- 
    Document   : index
    Created on : 2016-8-10, 8:59:51
    Author     : LFeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=EDGE"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="../style/detail_style.css" type="text/css" media="all" />
        <link rel="stylesheet" href="../style/style.css" type="text/css" media="all" />
        <link rel="stylesheet" href="../style/bootstrap.min.css" />

        <script type="text/javascript" src="../javascript/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="../javascript/JSON-js-master/json2.js"></script>
        <script type="text/javascript" src="../javascript/bootstrap.min.js"></script>
        <script type="text/javascript" src="../javascript/script.js"></script>
        <script type="text/javascript" src="../javascript/projectScript.js"></script>
        <script type="text/javascript">
            window.onload = function () {
                $("#stationId").val(${sessionScope.user.stationId});
                $("#submit_mark").bind("click", function() {
                    var inputObj = new Object();
                    inputObj.orderId = $("#orderId").val();
                    inputObj.username = '${sessionScope.user.username}';
                    inputObj.cardNum = $("#cardNum").val();
                    inputObj.data = '[{"fieldName":"CjiguangValue","fieldType":3,"fieldValue":"' + $("#print_mark").val() + '"},{"fieldName":"CcaozuozheValue","fieldType":3,"fieldValue":"' + inputObj.username + '"}]';
                    var sendBody = JSON.stringify(inputObj);
                    getDataInterface("inputOrder.pc", sendBody, function (result) {
                        var inputRes = JSON.parse(result);
                        alert(inputRes.message);
                        if (inputRes.status == 0) {
                            $("#cardNum").val("");
                            $("#productCode").val("");
                            $("#orderId").val("");
                        }
                    });
                });
                $("#generate_mark").bind("click", function () {
                    var mark = "";
                    var productCode = $("#productCode").val();
                    var isOilPipe = $("#isOilPipe").val();
                    if (isOilPipe == -1) {
                        alert("请选择产品类型!");
                        return;
                    }
                    if (isOilPipe == 1) {
                        mark += "";
                        mark += productCode + "  ";
                        mark += getNowDateWithProduct();
                    }
                    if (isOilPipe == 2) {
                        mark += "CCC  ";
                        mark += productCode + "  ";
                        mark += getNowDateWithProduct();
                    }
                    if (isOilPipe == 3) {
                        mark += "*110276*";
                        mark += getNowDateWithDaYun();
                        mark += "*";
                        mark += productCode;
                        mark += "*CCC  A082009*";
                    }
                    if (isOilPipe == 4) {
                        mark += "H152 " + productCode + "  ";
                        mark += getNowDateWithProduct() + "  ";
                        mark += "D";
                    }
                    if (isOilPipe == 5) {
                        mark += "H152 CCC ";
                        mark += productCode + " ";
                        mark += getNowDateWithProduct();
                    }
                    $("#print_mark").val(mark);
                    $("#print_mark").focus();
                    $("#print_mark").select();
                });

                $("#query_order").bind("click", function () {
                    $("#print_mark").val("");
                    if ($.trim($("#cardNum").val()) === "") {
                        alert("周转卡号不能为空!");
                        return;
                    }
                    var sendBody = serializeForm("search_form");
                    getDataInterface("getOrderInfo.pc", sendBody, function (result) {
                        var obj = JSON.parse(result);
                        if (obj.status === 0) {
                            var htmlStr = "<table class='detail_data'><tr class='table_header'><td>物料编码</td><td>物料名称</td><td>计划完工数量</td><td>计划完工日期</td><td>客户名称</td></tr>";
                            htmlStr += "<tr class='table_content'>";
                            htmlStr += "<td>" + (obj.data.productCode ? obj.data.productCode : "") + "</td>";
                            htmlStr += "<td>" + (obj.data.productName ? obj.data.productName : "") + "</td>";
                            htmlStr += "<td>" + (obj.data.planNum ? obj.data.planNum : "") + "</td>";
                            htmlStr += "<td>" + (obj.data.planFinishedTime ? obj.data.planFinishedTime : "") + "</td>";
                            htmlStr += "<td>" + (obj.data.customerName ? obj.data.customerName : "") + "</td>";
                            htmlStr += "</tr>";
                            htmlStr += "</table>";
                            
                            $("#orderId").val(obj.data.orderId);
                            $("#productCode").val(obj.data.productCode);
                            $("#message_list").html(htmlStr);
                        } else {
                            alert(obj.message);
                        }
                    });
                });
            };

            function getNowDateWithProduct() {
                var t = new Date();
                var nowDate = [t.getFullYear().toString().substr(2,2),
                    ((t.getMonth() + 1) < 10) ? ("0" + (t.getMonth() + 1)) : (t.getMonth() + 1 + ""),
                    (t.getDate() < 10) ? ("0" + t.getDate()) : ("" + t.getDate())].join('/');
                return nowDate;
            }
            function getNowDateWithDaYun() {
                var t = new Date();
                var nowDate = getCarYearCode(t.getFullYear() + "") +
                        (((t.getMonth() + 1) < 10) ? ("0" + (t.getMonth() + 1)) : (t.getMonth() + 1 + "")) +
                        ((t.getDate() < 10) ? ("0" + t.getDate()) : (t.getDate() + ""));
                return nowDate;
            }
        </script>
    </head>
    <body style="background-color: #e9f0fa;">
        <div class="titleName"><div class="vline"></div>打印标识生成</div>
        <div class="myline"></div>

        <div class="function">
        </div>

        <div class="search">

            <form id="search_form" class="form-inline">
                <div class="form-group">
                    <label for="cardNum">周转卡号：</label>
                    <input type="text" name="cardNum" id="cardNum" value="" /> 
                </div>
                <input type="hidden" name="stationId" id="stationId" value="" />
                <a class="btn btn-default" role="button" id="query_order">查询</a>
                <div class="form-group">
                    <label for="oilPipeSelect">产品类型：</label>
                    <select onchange="document.getElementById('isOilPipe').value = this.options[this.selectedIndex].value;" id="oilPipeSelect">
                        <option value="-1">--请选择--</option>
                        <option value="1">油管产品</option>
                        <option value="2">非油管产品</option>
                        <option value="3">大运产品</option>
                        <option value="4">高压管产品</option>
                        <option value="5">十堰产品</option>
                    </select>
                    <input type="hidden" name="isOilPipe" id="isOilPipe" value="-1" />
                    <a class="btn btn-default" role="button" id="generate_mark">生成标识</a>
                </div>
            </form>
            
            <div class="form-group">
                <label for="print_mark">打码标识:</label>
                <input id="print_mark" style="margin-left: 10px;"/>
                <a class="btn btn-default" role="button" id="submit_mark">提交</a>
            </div>
            <input type="hidden" name="orderId" id="orderId" />
            <input type="hidden" name="productCode" id="productCode" />
        </div>

        <div>
            <div id="message_list">
                <table class="detail_data">
                    <tr class='table_header'><td>物料编码</td><td>物料名称</td><td>计划完工数量</td><td>计划完工日期</td><td>客户名称</td></tr>
                </table>
            </div>
        </div>
    </body>
</html>
