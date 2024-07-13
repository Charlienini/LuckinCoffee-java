<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="./bootstrap-3.4.1/css/register.css">
<title>用户注册</title>
</head>
<body>
<form action="UserServlet?action=register" method="post">
<div class="from">
        <img class="bgc" src="./bootstrap-3.4.1/pic/imgs.jpg" alt="Luckin Coffee Register">
        <div class="submit">
            <span class="form_title">Luckin Coffee用户注册</span>
            <div class="form_input">
                <span>用户名：</span>
                <input class="inputs" type="text" name="username" placeholder="用户名称">
            </div>
            <div class="form_input">
                <span>密码:</span>
                <input class="inputs" type="password" name="pwd" placeholder="密码">
            </div>
        	<div class="form_input">
                <span>所在地区：</span>
                <input class="inputs" type="text" name="locate" placeholder="所在地区">
            </div>
            <div class="btn_submit">
                <a href="login.jsp">
                    <button type="submit" class="btn">注	  册</button></a>
            </div>
        </div>
    </div>
</FORM>
<a href="login.jsp"><button class="home_href">登   录</button></a>
</body>
</html>