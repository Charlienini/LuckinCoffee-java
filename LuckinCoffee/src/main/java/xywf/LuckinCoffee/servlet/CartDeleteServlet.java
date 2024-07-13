package xywf.LuckinCoffee.servlet;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import xywf.LuckinCoffee.bean.Cart;
import xywf.LuckinCoffee.dao.CartDAO;

/**
 * Servlet implementation class CartDeleteServlet
 */
public class CartDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CartDeleteServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Cart cart = new Cart();
		CartDAO cartDAO = new CartDAO();
		
		//获得购物车 Map
		// getAttribute的返回值类型是Object,需要向下转型
		HashMap map = (HashMap)request.getSession().getAttribute("cart");
		String msg1 = "";
		String delete_id = "";

		//把购物车商品删除
		//根据 sp_cfid 查询商品
		if(request.getParameter("id") != null)
			delete_id = request.getParameter("id"); //action&id=${}
			//查找cart表中，列sp_cfid为delete_id的sp_cup属性值为多少
			CartDAO ctdao = new CartDAO();
			Cart ct = ctdao.getctById(Integer.parseInt(delete_id));//map{ "sp_cfid":ct }
			int sp_cup = ct.getSp_cup();

			
		//判断商品数量是否为1

		// 1--> 直接删除
		// 大于1--> 把购物车中商品数量设置-1
		if (sp_cup <= 1) {
			
			if(ctdao.delete(Integer.parseInt(delete_id))) 
				msg1 = "删除购物车成功！";
			else
				msg1 = "删除购物车失败！";
			map.remove(delete_id);//map.remove(key)
			request.getSession().setAttribute("msg1", msg1);
		} else {
			if(ctdao.minus(Integer.parseInt(delete_id))) 
				msg1 = "减少购物车商品成功！";
			else
				msg1 = "减少购物车商品失败！";
			ct.setSp_cup(sp_cup-1);// 对要删除的id对应的cart对象的sp_cup值减1
			request.getSession().setAttribute("msg1", msg1);
		}
		// 4.把集合更新存储到session作用域
		request.getSession().setAttribute("cart", map);
		response.sendRedirect("delete_cart_res.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
