<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="bootstrap-3.4.1/css/login.css">
<title>管理员登录</title>
</head>
<body>
<form action="MagServlet?action=managlogin" method="post">
 <div class="from">
        <img class="bgc" src="./bootstrap-3.4.1/pic/mng.jpg" alt="">
        <div class="submit">
            <span class="form_title">Luckin Coffee管理员登录</span>
            <div class="form_input">
                <span>管理员：</span>
                <input class="inputs" type="text" name="manag_name" value="">
            </div>
            <div class="form_input">
                <span>密码:</span>
                <input class="inputs" type="password" name="manag_pass" value="">
            </div>
            
            <div class="btn_submit">
                    <button class="btn">登   录</button>
            </div>
        </div>
    </div>
</FORM>
</body>
</html>