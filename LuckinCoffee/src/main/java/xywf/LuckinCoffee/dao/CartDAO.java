package xywf.LuckinCoffee.dao;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import xywf.LuckinCoffee.bean.Cart;
import xywf.LuckinCoffee.bean.Coffee;
import xywf.LuckinCoffee.bean.User;
import xywf.LuckinCoffee.util.DB;

import xywf.LuckinCoffee.dao.CoffeeDAO;

public class CartDAO {
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<Cart> carts = new ArrayList<Cart>();
	private Cart cart;
	private Connection conn = null;
	// ��ȡ���ݿ�����
	private Connection getConn() {
		try {
			if((conn == null) || conn.isClosed()) {
				DB db = new DB();
				conn = db.getConn();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public ArrayList<Cart> getAll(){
		// sql���
		sql = "select * from cart";
		try {
			// ��ȡԤ����SQL��ִ��SQL���󣺷�ֹSQLע�롪������ͨ���������޸����ȶ���õ�SQL��䣬�Է���������
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			// ִ��sql���
			rs = pstmt.executeQuery();
		} catch(SQLException e){
			e.printStackTrace();
		}	
		return getByRs(rs);
	}
	
	
	public ArrayList<Cart> getByName(String sp_cfname) {
		conn = this.getConn();
		
		if (sp_cfname == null)
			sp_cfname = "";
		try {
			CallableStatement cs = (CallableStatement) conn.prepareCall("{call getByNameProc(?)}");
			cs.setString(1, sp_cfname);
			rs = cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//close();
		}
		return getByRs(rs);
	}
	public ArrayList<Cart> getByRs(ResultSet rs) {
		try {
			if(rs == null || !rs.next())
				return null;
			carts.clear();
			do {
				cart = new Cart();
				Coffee cf = (Coffee) rs.getObject("sp_coffee");
				cart.setSp_id(rs.getInt("sp_id"));
				cart.setSp_cfname(cf.getCoffeename());
				cart.setSp_size(cf.getSize());
				cart.setSp_temp(cf.getTemp());
				cart.setSp_sweet(cf.getSweet());
				cart.setSp_price(cf.getPrice());
				cart.setSp_cup(rs.getInt("sp_cup"));
				carts.add(cart);
			}while (rs.next());
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return carts;
	}
	
	
	
	public void close() {
		try {
			if(rs != null)
				rs.close();
			if(pstmt != null) 
				pstmt.close();
			if(conn != null)
				conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			rs = null;
			pstmt = null;
			conn = null;
		}
	}
	

	
	public Cart getById(int id) {
		conn = this.getConn();
		sql = "select * from coffee where id =" + id;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery(sql);
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			close();
		}
		return getByRs(rs).get(0);
	}
	
	

	
	/**
	 * ɾ�����ﳵ����
	 * ������id
	 * @param 
	 * @return
	 */
	//���ж�һ�¿��������Ƿ����1
	public boolean one(int id) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1.�����ݿ�����
			conn = this.getConn();

			// 2.����ִ��sql������
			stmt = conn.createStatement();

			// 3.����sql��䵽mysql
			String sql = "select * from cart where sp_cup = 1 and sp_cfid =" + id;
			rs = stmt.executeQuery(sql);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close();
		}
		if(rs != null)
			return true;
		else
			return false;
	}
	
	public boolean delete(int id) {
		// ����ʣ������С�ڵ���1�Ļ�ֱ��ɾ��
		sql = "delete from cart where sp_cfid = ?";
		int result = 0;
		try {
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close();
		}
		if(result > 0)
			return true;
		else
			return false;
	}

	
		// �Կ�����������һ�Ĳ���
	public boolean minus(int id) {
		sql = "update cart set sp_cup = sp_cup - 1 where sp_cfid = ?";
		int result = 0;
		try {
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close();
		}
		if(result > 0)
			return true;
		else
			return false;
		
	}
	
	
	
	//��ʾ���е�cart�б�����
	
	public ArrayList<Cart> showcart(ArrayList<Cart> list) {
		sql = "select sp_cfid,sp_cfname,sp_size,sp_temp,sp_sweet,sp_price,sp_cup from cart";;
		int result = 0;
		ResultSet resultSet = null;
		try {
			// ��ȡ���ݿ����ӳض���
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			resultSet = pstmt.executeQuery(); 
			while (resultSet.next()) {
	            int sp_cfid = resultSet.getInt("sp_cfid");
	            String sp_cfname = resultSet.getString("sp_cfname");
	            String sp_size = resultSet.getString("sp_size");
	            String sp_temp = resultSet.getString("sp_temp");
	            String sp_sweet = resultSet.getString("sp_sweet");
	            float sp_price = resultSet.getFloat("sp_price");
	            int sp_cup = resultSet.getInt("sp_cup");
	            Cart cart = new Cart(sp_cfid,sp_cfname,sp_size,sp_temp,sp_sweet,sp_price,sp_cup);
	            list.add(cart);
	        }
	        for (Cart cart : list) {
	            System.out.println(cart.getSp_id());
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(); // �ͷ�����
		}
		return list;
	}
	
	
	public Cart getctById(int id) {
		
		Cart cart = new Cart();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1.�����ݿ�����
			conn = this.getConn();

			// 2.����ִ��sql������
			stmt = conn.createStatement();

			// 3.����sql��䵽mysql
			String sql = "select * from cart where sp_cfid =" + id;
			rs = stmt.executeQuery(sql);
			// rs�����--->����--->��װproduct--->����ArrayList
			
			while (rs.next())// ѭ��һ��ֻ����һ��
			{
				//Product p = new Product();
				cart.setSp_id(rs.getInt("sp_cfid"));
				cart.setSp_cfname(rs.getString("sp_cfname"));
				cart.setSp_size(rs.getString("sp_size"));
				cart.setSp_temp(rs.getString("sp_temp"));
				cart.setSp_sweet(rs.getString("sp_sweet"));
				cart.setSp_price(rs.getFloat("sp_price"));
				cart.setSp_cup(rs.getInt("sp_cup"));
				//System.out.println(p.getName());
				//list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cart;
	}
	
	public boolean clearCart() {
		// ��չ��ﳵ
		sql = "truncate table cart ";
		int result = 0;
		try {
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close();
		}
		if(result > 0)
			return true;
		else
			return false;
	}
}
