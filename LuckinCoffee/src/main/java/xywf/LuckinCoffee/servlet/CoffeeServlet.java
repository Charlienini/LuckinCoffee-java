package xywf.LuckinCoffee.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xywf.LuckinCoffee.bean.Coffee;
import xywf.LuckinCoffee.dao.CoffeeDAO;

/**
 * Servlet implementation class CoffeeServlet
 */
public class CoffeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CoffeeServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    // »ñÈ¡ÇëÇó²ÎÊýÎªactionµÄÄÚÈÝ
	    String action = request.getParameter("action");
		
		Coffee coffee = new Coffee();
		CoffeeDAO coffeeDAO = new CoffeeDAO();
		
		String msg ="";
		String msg8 = "";
	    boolean result = false;
	    int id = 0;
	    if(request.getParameter("id") != null)
	    	id = Integer.parseInt(request.getParameter("id"));
	    if(action.equals("add")) {
	    	coffee.setCoffeename(request.getParameter("coffeename"));
	    	coffee.setSize(request.getParameter("size"));
	    	coffee.setTemp(request.getParameter("temp"));
	    	coffee.setSweet(request.getParameter("sweet"));
	    	coffee.setPrice(Float.parseFloat(request.getParameter("price")));
	    	result = coffeeDAO.add(coffee);
	    	if(result == true) 
	    		msg = "¿§·ÈÌí¼Ó³É¹¦£¡";
	    	else
	    		msg = "¿§·ÈÌí¼ÓÊ§°Ü£¡";
	    }else if(action.equals("delete")){
	    	result = coffeeDAO.delete(id);
		    if(result == true)
		    	msg = "¿§·ÈÉ¾³ý³É¹¦£¡";
		    else
		    	msg = "¿§·ÈÉ¾³ýÊ§°Ü£¡";
	    }else if(action.equals("update")){
	    	coffee.setId(id);
	    	coffee.setCoffeename(request.getParameter("coffeename"));
	    	coffee.setSize(request.getParameter("size"));
	    	coffee.setTemp(request.getParameter("temp"));
	    	coffee.setSweet(request.getParameter("sweet"));
	    	coffee.setPrice(Float.parseFloat(request.getParameter("price")));
	    	result = coffeeDAO.updateCoffee(coffee);
	    	if(result == true) 
	    		msg = "¿§·ÈÐÞ¸Ä³É¹¦£¡";
	    	else
	    		msg = "¿§·ÈÐÞ¸ÄÊ§°Ü£¡";
	    	
	    	
	    }
	    request.getSession().setAttribute("msg", msg);
		response.sendRedirect("coffee_result.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
