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
		
		// 1.��ù��ﳵ Map
		// getAttribute�ķ���ֵ������Object,��Ҫ����ת��
		HashMap map = (HashMap)request.getSession().getAttribute("cart");
		String msg3 = "";
		
		Cart cart = new Cart();
		CartDAO cartDAO = new CartDAO();
		
		
		// 2.�жϹ��ﳵMap�Ƿ����,�������򴴽�
		if (map == null) {
			map = new HashMap();
		}
		String sp_cfid = "";
		// 3.�Ѳ�Ʒ��ӵ����ﳵ
		// 3.1 ���� sp_cfid ��ѯ��Ʒ
		if(request.getParameter("id") != null)
			sp_cfid = request.getParameter("id"); //action&id=${}
		CoffeeDAO cfdao = new CoffeeDAO();

		Coffee coffee = cfdao.getcfById(Integer.parseInt(sp_cfid)); //������id����Ʒ
		// 3.2�ж� Map ���Ƿ��д���Ʒ , ע������sp_cfid��������
		Cart cartItem = (Cart) map.get(sp_cfid);// map:{ "sp_cfid":cartItem } �磺map{ "2":cart2 }
		// ��--> ������+1
		// ��--> ����Ʒ���빺�ﳵ ��������Ϊ1
		if (cartItem != null) {
			cartItem.setSp_cup(cartItem.getSp_cup() + 1);
			if(cfdao.addnum(Integer.parseInt(sp_cfid))) 
				msg3 = "�ظ���ӽ����ﳵ�ɹ���";
			else
				msg3 = "�ظ���ӽ����ﳵʧ�ܣ�";
			request.getSession().setAttribute("msg3", msg3);
		} else {
			//����cart����
			cartItem = new Cart();
			//�����ȼ��빺�ﳵ���ݿ�
			//�����еĿ�������һһ����cart����,����֮����Ϊmap��ֵ
			cartItem.setSp_id(coffee.getId()); // �ؼ�����
			cartItem.setSp_cfname(coffee.getCoffeename());
			cartItem.setSp_size(coffee.getSize());
			cartItem.setSp_temp(coffee.getTemp());
			cartItem.setSp_sweet(coffee.getSweet());
			cartItem.setSp_price(coffee.getPrice());
			//�ö������������Ϊ1
			cartItem.setSp_cup(1);
			
			if(cfdao.addcart(coffee))
				msg3 = "��ӽ����ﳵ�ɹ���";
			else
				msg3 = "��ӽ����ﳵʧ�ܣ�";
			request.getSession().setAttribute("msg3", msg3);
		}
		// 3.3 ����Ʒ����map����,sp_cfid+""ƴ�ӳ��ַ�����
		map.put(sp_cfid, cartItem);
		// �Ѽ��ϴ洢��session������
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
