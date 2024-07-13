<%@ page language="java" contentType="text/html; charset=UTF-8"    
pageEncoding="UTF-8" import="xywf.LuckinCoffee.dao.*,xywf.LuckinCoffee.bean.*" %>
    <%request.setCharacterEncoding("utf-8"); %>
    
    <jsp:useBean id="userDAO" class="xywf.LuckinCoffee.dao.UserDAO"></jsp:useBean>
    <jsp:useBean id="user" class="xywf.LuckinCoffee.bean.User"></jsp:useBean>
    <jsp:setProperty property="*" name="user" /> 
	<%
    String msg4 ="";
    boolean result = false;
    // 获取请求参数为action的内容
    String action = request.getParameter("action");
    if(action.equals("register")) {
    	result = userDAO.add(user);
    	if(result == true) {
    		msg4 = "用户注册成功！";
	    	session.setAttribute("msg4", msg4);
	    	response.sendRedirect("user_regis_res_succ.jsp");
	    	return;
	    	
    	} else {
    		msg4 = "用户注册失败！";
	    	session.setAttribute("msg4", msg4);
	    	response.sendRedirect("user_regis_res_fail.jsp");
    	}
    }
    
    if(action.equals("login")) {
        String username=user.getUsername();
        String pwd=user.getPwd();
        result = userDAO.login(username,pwd);
        if(result){
        	session.setAttribute("username", username);
        	response.sendRedirect("user_login_res_succ.jsp");
        	return;
        }else {
        	msg4 = "登录失败！请确认用户名和密码是否正确输入。";
        	session.setAttribute("msg4", msg4);
        	response.sendRedirect("user_login_res_fail.jsp");
    	}
    }
    %>