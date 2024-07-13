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
	// 获取数据库连接
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
		// sql语句
		sql = "select * from cart";
		try {
			// 获取预编译SQL的执行SQL对象：防止SQL注入————通过输入来修改事先定义好的SQL语句，对服务器攻击
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			// 执行sql语句
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
	 * 删除购物车咖啡
	 * 参数：id
	 * @param 
	 * @return
	 */
	//先判断一下咖啡数量是否等于1
	public boolean one(int id) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1.打开数据库连接
			conn = this.getConn();

			// 2.创建执行sql语句对象
			stmt = conn.createStatement();

			// 3.发送sql语句到mysql
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
		// 咖啡剩余数量小于等于1的话直接删除
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

	
		// 对咖啡数量做减一的操作
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
	
	
	
	//显示所有的cart列表内容
	
	public ArrayList<Cart> showcart(ArrayList<Cart> list) {
		sql = "select sp_cfid,sp_cfname,sp_size,sp_temp,sp_sweet,sp_price,sp_cup from cart";;
		int result = 0;
		ResultSet resultSet = null;
		try {
			// 获取数据库连接池对象
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
			close(); // 释放连接
		}
		return list;
	}
	
	
	public Cart getctById(int id) {
		
		Cart cart = new Cart();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1.打开数据库连接
			conn = this.getConn();

			// 2.创建执行sql语句对象
			stmt = conn.createStatement();

			// 3.发送sql语句到mysql
			String sql = "select * from cart where sp_cfid =" + id;
			rs = stmt.executeQuery(sql);
			// rs结果集--->遍历--->封装product--->放入ArrayList
			
			while (rs.next())// 循环一次只代表一行
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
		// 清空购物车
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
