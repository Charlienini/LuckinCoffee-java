<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content = "2;usermain.jsp">
<title>结果信息</title>
</head>
<body>
	<%request.setCharacterEncoding("utf-8"); %>
	<h1>${sessionScope.username}</h1><h1>登录成功！欢迎您使用！</h1>
</body>
</html>