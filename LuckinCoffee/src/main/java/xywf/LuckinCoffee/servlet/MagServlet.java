package xywf.LuckinCoffee.servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xywf.LuckinCoffee.bean.Manager;
import xywf.LuckinCoffee.dao.ManagDAO;

/**
 * Servlet implementation class MagRegisServlet
 */
public class MagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MagServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManagDAO managDAO = new ManagDAO();
		Manager manag = new Manager();
		
		String msg6 ="";
	    boolean result = false;
	    // 获取请求参数为action的内容
	    String action = request.getParameter("action");
	    if(action.equals("register")) {
	    	manag.setManag_name(request.getParameter("manag_name"));
	    	manag.setManag_pass(request.getParameter("manag_pass"));
	    	result = managDAO.addmanag(manag);
	    	if(result == true) {
	    		msg6 = "管理员注册成功！";
	    		request.getSession().setAttribute("msg6", msg6);
	        	response.sendRedirect("mng_regis_res_succ.jsp");
	        	return;
	    	}else {
	    		msg6 = "管理员注册失败！";
	    		request.getSession().setAttribute("msg6", msg6);
		    	response.sendRedirect("mng_regis_res_fail.jsp");
	    	}
	    }
	    if(action.equals("managlogin")) {
	        String manag_name = request.getParameter("manag_name");
	        String manag_pass = request.getParameter("manag_pass");
	        try {
				result = managDAO.managlogin(manag_name,manag_pass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(result){
	        	request.getSession().setAttribute("manag_name", manag_name);
	        	response.sendRedirect("mng_login_res_succ.jsp");        	
	        	return;
	        }else {
	        	request.getSession().setAttribute("manag_name", manag_name);
	    		response.sendRedirect("mng_login_res_fail.jsp");
	        }
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
