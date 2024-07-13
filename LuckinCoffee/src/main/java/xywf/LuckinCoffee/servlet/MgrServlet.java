package xywf.LuckinCoffee.servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xywf.LuckinCoffee.bean.Manager;
import xywf.LuckinCoffee.bean.Mgr;
import xywf.LuckinCoffee.dao.ManagDAO;
import xywf.LuckinCoffee.dao.MgrDAO;

/**
 * Servlet implementation class MgrServlet
 */
public class MgrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MgrServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MgrDAO mgrDAO = new MgrDAO();
		Mgr mgr = new Mgr();
		
		String mgr_msg ="";
	    boolean result = false;
	    
	    // 获取请求参数为action的内容
	    String action = request.getParameter("action");
	    if(action.equals("register")) {
	    	mgr.setMgr_name(request.getParameter("mgr_name"));
	    	mgr.setMgr_pwd(request.getParameter("mgr_pwd"));
	    	result = mgrDAO.mgr_register(mgr);
	    	if(result == true) {
	    		mgr_msg = "管理员注册成功！";
	    		request.getSession().setAttribute("mgr_msg", mgr_msg);
	        	response.sendRedirect("mng_regis_res_succ.jsp");
	        	return;
	    	}else {
	    		mgr_msg = "管理员注册失败！";
	    		request.getSession().setAttribute("mgr_msg", mgr_msg);
		    	response.sendRedirect("mng_regis_res_fail.jsp");
	    	}
	    }
	    
	    if(action.equals("mgr_login")) {
	        String mgr_name = request.getParameter("mgr_name");
	        String mgr_pwd = request.getParameter("mgr_pwd");
	        try {
				result = mgrDAO.mgr_login(mgr_name,mgr_pwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(result){
	        	request.getSession().setAttribute("mgr_name", mgr_name);
	        	response.sendRedirect("mng_login_res_succ.jsp");        	
	        	return;
	        }else {
	        	request.getSession().setAttribute("mgr_name", mgr_name);
	    		response.sendRedirect("mng_login_res_fail.jsp");
	        }
	    }
	    
	    if(action.equals("delete")) {
	        String mgr_id = request.getParameter("mgr_id");
	        try {
				result = mgrDAO.getmgrById(mgr_id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(result){
	        	request.getSession().setAttribute("mgr_name", mgr_name);
	        	response.sendRedirect("mng_login_res_succ.jsp");        	
	        	return;
	        }else {
	        	request.getSession().setAttribute("mgr_name", mgr_name);
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
