package xywf.LuckinCoffee.servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xywf.LuckinCoffee.bean.User;
import xywf.LuckinCoffee.dao.UserDAO;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UserServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = new User();
		UserDAO userDAO = new UserDAO();
		
		String msg4 ="";
	    boolean result = false;
	    // 获取请求参数为action的内容
	    String action = request.getParameter("action");
	    if(action.equals("register")) {

	    	user.setUsername(request.getParameter("username"));
	    	user.setPwd(request.getParameter("pwd"));
	    	user.setLocate(request.getParameter("locate"));
	    	result = userDAO.add(user);
	    	if(result == true) {
	    		msg4 = "用户注册成功！";
	    		request.getSession().setAttribute("msg4", msg4);
		    	response.sendRedirect("user_regis_res_succ.jsp");
		    	return;
		    	
	    	} else {
	    		msg4 = "用户注册失败！";
	    		request.getSession().setAttribute("msg4", msg4);
		    	response.sendRedirect("user_regis_res_fail.jsp");
	    	}
	    }
	    
	    if(action.equals("login")) {
	        String username = request.getParameter("username");
	        String pwd = request.getParameter("pwd");
	        try {
				result = userDAO.login(username,pwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(result){
	        	request.getSession().setAttribute("username", username);
	        	response.sendRedirect("user_login_res_succ.jsp");
	        	return;
	        }else {
	        	msg4 = "登录失败！请确认用户名和密码是否正确输入。";
	        	request.getSession().setAttribute("msg4", msg4);
	        	response.sendRedirect("user_login_res_fail.jsp");
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
