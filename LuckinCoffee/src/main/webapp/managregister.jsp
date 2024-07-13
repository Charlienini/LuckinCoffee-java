<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="./bootstrap-3.4.1/css/register.css">
<title>管理员注册</title>
</head>
<body>
<form action="MagServlet?action=register" method="post">
<div class="from">
        <img class="bgc" src="./bootstrap-3.4.1/pic/imgs.jpg" alt="Luckin Coffee Register">
        <div class="submit">
            <span class="form_title">Luckin Coffee管理员注册</span>
            <div class="form_input">
                <span>管理员：</span>
                <input class="inputs" type="text" name="manag_name" placeholder="管理员名称">
            </div>
            <div class="form_input">
                <span>密码:</span>
                <input class="inputs" type="password" name="manag_pass" placeholder="密码">
            </div>
            <div class="btn_submit">
                    <button type="submit" class="btn">注	  册</button></a>
                    
                
            </div>
            
        </div>
        
    </div>
</FORM>
<a href="managlogin.jsp"><button class="home_href">登   录</button></a>
</body>
</html>