<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="coffeeDAO" class="xywf.LuckinCoffee.dao.CoffeeDAO"></jsp:useBean>
<%@ page import="java.util.*,xywf.LuckinCoffee.bean.*" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="bootstrap-3.4.1/css/bootstrap.min.css"/>
<title>瑞幸咖啡菜单主页</title>



</head>
<body>

<%
request.setCharacterEncoding("utf-8");
String keyword = request.getParameter("keyword");
request.setAttribute("keyword" , keyword);
ArrayList<Coffee> coffees = coffeeDAO.getByName(keyword);
request.setAttribute("coffees" , coffees);
%>


<form action="usermain.jsp" method="post">
<div class="col-xs-4">
    <input type="text" class="form-control" name="keyword" value="${requestScope.keyword}" placeholder=请输入咖啡名称关键词">
  </div>
<input type="submit" class="btn btn-info" value="搜素">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a type="button" class="btn btn-warning" href="cartmain.jsp">购物车 <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span></a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a type="button" class="btn btn-danger" href="logout.jsp">安全退出</span></a>
</form><br>


<div class="panel panel-default">
  <div class="panel-heading"><p><font size="Impact">Luckin Coffee菜单</font></p>
  <svg class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"><path d="M243.563947 745.354897a206.274 273.17 17.529 1 0 164.551315-520.970499 206.274 273.17 17.529 1 0-164.551315 520.970499Z" fill="#A56021"></path><path d="M205.424 446.87c39.982-126.576 141.478-210.514 239.282-205.488a158.428 158.428 0 0 0-36.59-16.96C299.484 190.108 174.582 278.912 129.14 422.774s5.782 288.3 114.412 322.614a158.452 158.452 0 0 0 39.694 7.136c-82.944-52.068-117.804-179.078-77.822-305.654z" fill="#8C4C17"></path><path d="M250.142 744.578a20 20 0 0 1-17.936-11.124c-40.148-81.016-23.114-143.648-1.75-181.924 22.952-41.122 55.516-63.002 56.89-63.914l0.208-0.136c65.77-42.42 103.624-88.658 112.508-137.428 9.22-50.606-16.422-85.954-16.684-86.306-6.574-8.876-4.708-21.4 4.168-27.976 8.874-6.574 21.4-4.71 27.976 4.166 1.482 2.002 36.184 49.816 23.89 117.284-10.938 60.044-54.688 115.146-130.03 163.776-4.932 3.342-102.368 71.55-41.34 194.696 4.904 9.898 0.858 21.896-9.04 26.8a19.854 19.854 0 0 1-8.86 2.086z" fill="#8C4C17"></path><path d="M324.96 670.402m-30 0a30 30 0 1 0 60 0 30 30 0 1 0-60 0Z" fill="#8C4C17"></path><path d="M548.660918 758.749176a273.17 206.274 30.692 1 0 210.573928-354.75974 273.17 206.274 30.692 1 0-210.573928 354.75974Z" fill="#E58D23"></path><path d="M589.496 689.906c-114.15-67.75-172.776-185.688-145.616-279.78a158.444 158.444 0 0 0-24.846 31.768c-58.144 97.966-0.106 239.806 129.63 316.808s282.046 60.006 340.19-37.96a158.476 158.476 0 0 0 15.986-37.026c-69.584 68.91-201.194 73.94-315.344 6.19z" fill="#BF6C0D"></path><path d="M819.432 735.792c-57.458 0.002-97.27-23.614-122.324-46.432-34.816-31.71-48.708-68.402-49.284-69.948a11.036 11.036 0 0 1-0.086-0.234c-26.194-73.31-62.366-120.592-107.508-140.536-46.802-20.674-86.958-4.366-88.646-3.664-10.136 4.38-21.856-0.31-26.236-10.45s0.342-21.934 10.48-26.316c2.292-0.988 56.746-23.894 119.644 3.438 55.976 24.322 99.672 79.466 129.874 163.9 2.132 5.564 46.374 115.968 180.17 84.58a19.994 19.994 0 0 1 24.04 14.904c2.524 10.752-4.148 21.516-14.902 24.04-19.972 4.682-38.362 6.718-55.222 6.718z" fill="#A56021"></path><path d="M787.1 522.702m-30 0a30 30 0 1 0 60 0 30 30 0 1 0-60 0Z" fill="#BF6C0D"></path><path d="M840.084 611.928m-30 0a30 30 0 1 0 60 0 30 30 0 1 0-60 0Z" fill="#BF6C0D"></path></svg>
  </div>
  
  <!-- Table -->
  <table class="table">
    <tr><th>ID</th><th>咖啡种类</th><th>杯型</th><th>温度</th><th>糖度</th><th>价格（元）</th></tr>
	<c:forEach items="${coffees}" var="coffee">
	<tr>
	<td>${coffee.id}</td><td>${coffee.coffeename}</td>
	<td>${coffee.size}</td><td>${coffee.temp}</td>
	<td>${coffee.sweet}</td><td>${coffee.price}</td>
	<!-- 添加到购物车 -->
	<td><a href="CartAddServlet? action=add&id=${coffee.id}"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a></td>
	</tr>
	</c:forEach>
	  
  </table>
</div>


</body>
</html>