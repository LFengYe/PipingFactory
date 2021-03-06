<%-- 
    Document   : index
    Created on : 2016-8-10, 8:27:36
    Author     : LFeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="style/main_style.css" type="text/css" media="all" />
        <link rel="stylesheet" href="style/navbar_style.css" type="text/css" media="all" />
        <link rel="stylesheet" href="style/bootstrap.min.css" type="text/css" media="all" />
        <link rel="stylesheet" href="style/bootstrap-treeview.min.css" type="text/css" media="all" />
        <!--[if lt IE 9]>
        <script type="text/javascript" src="javascript/respond.min.js"></script>
        <script type="text/javascript" src="javascript/html5shiv.min.js"></script>
        <![endif]-->
        <script type="text/javascript" src="javascript/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="javascript/jquery.cookie.js"></script>
        <script type="text/javascript" src="javascript/JSON-js-master/json2.js"></script>
        <script type="text/javascript" src="javascript/bootstrap.min.js"></script>
        <script type="text/javascript" src="javascript/bootstrap-treeview.min.js"></script>
        <script type="text/javascript" src="javascript/script.js"></script>
        <script type="text/javascript" src="javascript/layer/layer.js"></script>
        <title>后台管理</title>
        <script type="text/javascript">
            window.onload = function () {
                initTheme();
                findDimensions();
                createTreeMenu();
                $("#password").bind('click', function () {
                    displayLayer('modify_pass.html', '修改密码', null, -1);
                });

                $("#theme_refresh").bind('click', function () {
                    var cookie_style = $.cookie("mystyle");
                    if (cookie_style === "normal") {
                        $("link[title='drak']").removeAttr("disabled");
                        $("link[title='normal']").attr("disabled", "disabled");
                        $(this).attr("title", "drak");
                        $.cookie("mystyle", "drak", {expires: 30});
                    } else {
                        $("link[title='normal']").removeAttr("disabled");
                        $("link[title='drak']").attr("disabled", "disabled");
                        $(this).attr("title", "normal");
                        $.cookie("mystyle", "normal", {expires: 30});
                    }
                    location.reload();
                });
            };
            function initTheme() {
                var cookie_style = $.cookie("mystyle");
                if (cookie_style == null) {
                    $("link[title='normal']").removeAttr("disabled");
                } else {
                    if (cookie_style === "normal") {
                        $("link[title='drak']").removeAttr("disabled");
                        $("link[title='normal']").attr("disabled", "disabled");
                        $(this).attr("title", "drak");
                    } else {
                        $("link[title='normal']").removeAttr("disabled");
                        $("link[title='drak']").attr("disabled", "disabled");
                        $(this).attr("title", "normal");
                    }
                }
            }

            function createTreeMenu() {
                var tree = ${sessionScope.user.userPermission};
                $('#tree_menu').treeview({
                    data: tree,
                    enableLinks: true
                });
                $("iframe").attr("src", "${sessionScope.user.userTypeInitPage}");
            }
            function hrefToIframe(url) {
                window.parent.content.location.href = url;
            }

            function displayLayer(url, title, callback, curPage) {
                layer.open({
                    type: 2,
                    title: title,
                    shadeClose: true,
                    shade: 0.3,
                    maxmin: true, //开启最大化最小化按钮
                    area: ['1200px', '500px'],
                    content: url,
                    end: function () {
                        if (callback) {
                            callback(curPage);
                        }
                    }
                });
                //layer.full(index);
            }
        </script>
    </head>
    <body>
        <div id="top">
            <div id="logo">
                <h2 >后台管理</h2>
            </div>
            <div class="navbar_info">
                <table>
                    <tr>
                        <td><a href="javascript:void(0)" id="theme_refresh" title="切换主题" class="glyphicon glyphicon-refresh"></a></td>
                        <td id="username"><a title="hello_username" href="#" class="largeimagespace">Hi,${sessionScope.user.username}</a></td>
                        <td ><a href="javascript:void(0)" id="password" title="修改密码"><img src="images/modify_password.png" alt="modify password"></img></a></td>
                        <td id="out"><a href="exit.jsp" title="退出登录" target="_top"><img src="images/login_out.png" alt="login out"></img></a></td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="left">
            <div id="tree_menu" style="overflow-y: auto;overflow-x: hidden;"></div>
        </div>
        <div id="right">
            <div style="height: 100%;">
                <iframe name="content" src="" frameborder="0" style="width: 100%;height: 100%;"></iframe>
            </div>
        </div>
    </body>
</html>
