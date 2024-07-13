
<%@ page language="java" contentType="text/html; charset=UTF-8"    
pageEncoding="UTF-8" import="xywf.LuckinCoffee.dao.*,xywf.LuckinCoffee.bean.*" %>
    <%request.setCharacterEncoding("utf-8"); %>
    
    <jsp:useBean id="managDAO" class="xywf.LuckinCoffee.dao.ManagDAO"></jsp:useBean>
    <jsp:useBean id="manag" class="xywf.LuckinCoffee.bean.Manager"></jsp:useBean>
    <jsp:setProperty property="*" name="manag" /> 
	<%
    String msg6 ="";
    boolean result = false;
    // 获取请求参数为action的内容
    String action = request.getParameter("action");
    if(action.equals("register")) {
    	result = managDAO.addmanag(manag);
    	if(result == true) {
    		msg6 = "管理员注册成功！";
    		session.setAttribute("msg6", msg6);
        	response.sendRedirect("mng_regis_res_succ.jsp");
        	return;
    	}else {
    		msg6 = "管理员注册失败！";
	    	session.setAttribute("msg6", msg6);
	    	response.sendRedirect("mng_regis_res_fail.jsp");
    	}
    }
    if(action.equals("managlogin")) {
        String manag_name=manag.getManag_name();
        String manag_pass=manag.getManag_pass();
        result = managDAO.managlogin(manag_name,manag_pass);
        if(result){
        	session.setAttribute("manag_name", manag_name);
        	response.sendRedirect("mng_login_res_succ.jsp");        	
        	return;
        }else {
        	session.setAttribute("manag_name", manag_name);
    		response.sendRedirect("mng_login_res_fail.jsp");
        }
    }
    %>