<%@ page language="java" contentType="text/html; charset=UTF-8"    
pageEncoding="UTF-8" import="java.util.*,xywf.LuckinCoffee.dao.*,xywf.LuckinCoffee.bean.*" %>
    <%request.setCharacterEncoding("utf-8"); %>
    <jsp:useBean id="cartDAO" class="xywf.LuckinCoffee.dao.CartDAO"></jsp:useBean>
    <jsp:useBean id="cart" class="xywf.LuckinCoffee.bean.Cart"></jsp:useBean> 
    <jsp:setProperty property="*" name="cart" />
    
<%
// 1.获得购物车 Map
// getAttribute的返回值类型是Object,需要向下转型
HashMap map = (HashMap)session.getAttribute("cart");
String msg1 = "";
CartDAO ctdao = new CartDAO();
//判断商品数量是否为1
//清空购物车
ctdao.clearCart();
response.sendRedirect("userregister.jsp");
%>
  