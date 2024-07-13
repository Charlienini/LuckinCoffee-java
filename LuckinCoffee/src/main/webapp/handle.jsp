<%@ page language="java" contentType="text/html; charset=UTF-8"    
pageEncoding="UTF-8" import="xywf.LuckinCoffee.dao.*,xywf.LuckinCoffee.bean.*" %>
    <%request.setCharacterEncoding("utf-8"); %>
    <jsp:useBean id="coffeeDAO" class="xywf.LuckinCoffee.dao.CoffeeDAO"></jsp:useBean>
    <jsp:useBean id="coffee" class="xywf.LuckinCoffee.bean.Coffee"></jsp:useBean> 
    <jsp:setProperty property="*" name="coffee" />
    <%
    String msg ="";
	String msg8 = "";
    boolean result = false;
    int id = 0;
    // 获取请求参数为action的内容
    String action = request.getParameter("action");
    if(request.getParameter("id") != null)
    	id = Integer.parseInt(request.getParameter("id"));
    if(action.equals("add")) {
    	result = coffeeDAO.add(coffee);
    	if(result == true) 
    		msg = "咖啡添加成功！";
    	else
    		msg = "咖啡添加失败！";
    }else if(action.equals("delete")){
    	result = coffeeDAO.delete(id);
	    if(result == true)
	    	msg = "咖啡删除成功！";
	    else
	    	msg = "咖啡删除失败！";
    }else if(action.equals("update")){
    
    	result = coffeeDAO.updateCoffee(coffee);
    	if(result == true) 
    		msg = "咖啡修改成功！";
    	else
    		msg = "咖啡修改失败！";
    	
    	
    }
    session.setAttribute("msg", msg);
	response.sendRedirect("coffee_result.jsp");
    
 %>
