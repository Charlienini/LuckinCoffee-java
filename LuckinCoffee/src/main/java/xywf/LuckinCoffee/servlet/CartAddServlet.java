package xywf.LuckinCoffee.servlet;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xywf.LuckinCoffee.bean.Cart;
import xywf.LuckinCoffee.bean.Coffee;
import xywf.LuckinCoffee.dao.CartDAO;
import xywf.LuckinCoffee.dao.CoffeeDAO;

/**
 * Servlet implementation class CartServlet
 */
public class CartAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CartAddServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 1.获得购物车 Map
		// getAttribute的返回值类型是Object,需要向下转型
		HashMap map = (HashMap)request.getSession().getAttribute("cart");
		String msg3 = "";
		
		Cart cart = new Cart();
		CartDAO cartDAO = new CartDAO();
		
		
		// 2.判断购物车Map是否存在,不存在则创建
		if (map == null) {
			map = new HashMap();
		}
		String sp_cfid = "";
		// 3.把产品添加到购物车
		// 3.1 根据 sp_cfid 查询商品
		if(request.getParameter("id") != null)
			sp_cfid = request.getParameter("id"); //action&id=${}
		CoffeeDAO cfdao = new CoffeeDAO();

		Coffee coffee = cfdao.getcfById(Integer.parseInt(sp_cfid)); //创建该id的商品
		// 3.2判断 Map 中是否有此商品 , 注意这里sp_cfid不加引号
		Cart cartItem = (Cart) map.get(sp_cfid);// map:{ "sp_cfid":cartItem } 如：map{ "2":cart2 }
		// 有--> 把数量+1
		// 无--> 把商品放入购物车 数量设置为1
		if (cartItem != null) {
			cartItem.setSp_cup(cartItem.getSp_cup() + 1);
			if(cfdao.addnum(Integer.parseInt(sp_cfid))) 
				msg3 = "重复添加进购物车成功！";
			else
				msg3 = "重复添加进购物车失败！";
			request.getSession().setAttribute("msg3", msg3);
		} else {
			//创建cart对象
			cartItem = new Cart();
			//将咖啡加入购物车数据库
			//将所有的咖啡属性一一赋给cart对象,用于之后作为map的值
			cartItem.setSp_id(coffee.getId()); // 关键步骤
			cartItem.setSp_cfname(coffee.getCoffeename());
			cartItem.setSp_size(coffee.getSize());
			cartItem.setSp_temp(coffee.getTemp());
			cartItem.setSp_sweet(coffee.getSweet());
			cartItem.setSp_price(coffee.getPrice());
			//该对象的数量设置为1
			cartItem.setSp_cup(1);
			
			if(cfdao.addcart(coffee))
				msg3 = "添加进购物车成功！";
			else
				msg3 = "添加进购物车失败！";
			request.getSession().setAttribute("msg3", msg3);
		}
		// 3.3 将产品加入map集合,sp_cfid+""拼接成字符串型
		map.put(sp_cfid, cartItem);
		// 把集合存储到session作用域
		request.getSession().setAttribute("cart", map);
		response.sendRedirect("cart_add_res.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
