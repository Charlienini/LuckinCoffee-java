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
		
		//��ù��ﳵ Map
		// getAttribute�ķ���ֵ������Object,��Ҫ����ת��
		HashMap map = (HashMap)request.getSession().getAttribute("cart");
		String msg1 = "";
		String delete_id = "";

		//�ѹ��ﳵ��Ʒɾ��
		//���� sp_cfid ��ѯ��Ʒ
		if(request.getParameter("id") != null)
			delete_id = request.getParameter("id"); //action&id=${}
			//����cart���У���sp_cfidΪdelete_id��sp_cup����ֵΪ����
			CartDAO ctdao = new CartDAO();
			Cart ct = ctdao.getctById(Integer.parseInt(delete_id));//map{ "sp_cfid":ct }
			int sp_cup = ct.getSp_cup();

			
		//�ж���Ʒ�����Ƿ�Ϊ1

		// 1--> ֱ��ɾ��
		// ����1--> �ѹ��ﳵ����Ʒ��������-1
		if (sp_cup <= 1) {
			
			if(ctdao.delete(Integer.parseInt(delete_id))) 
				msg1 = "ɾ�����ﳵ�ɹ���";
			else
				msg1 = "ɾ�����ﳵʧ�ܣ�";
			map.remove(delete_id);//map.remove(key)
			request.getSession().setAttribute("msg1", msg1);
		} else {
			if(ctdao.minus(Integer.parseInt(delete_id))) 
				msg1 = "���ٹ��ﳵ��Ʒ�ɹ���";
			else
				msg1 = "���ٹ��ﳵ��Ʒʧ�ܣ�";
			ct.setSp_cup(sp_cup-1);// ��Ҫɾ����id��Ӧ��cart�����sp_cupֵ��1
			request.getSession().setAttribute("msg1", msg1);
		}
		// 4.�Ѽ��ϸ��´洢��session������
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
