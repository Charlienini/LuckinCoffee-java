package xywf.LuckinCoffee.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import xywf.LuckinCoffee.bean.Cart;
import xywf.LuckinCoffee.bean.Coffee;
import xywf.LuckinCoffee.bean.Mgr;
import xywf.LuckinCoffee.util.DB;

public class MgrDAO {
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<Mgr> mgrs = new ArrayList<Mgr>();
	private Mgr mgr;
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
	
	/**
	 * 管理员注册
	 * 参数：用户名和密码
	 * @param book
	 * @return
	 */
	public boolean mgr_register(Mgr mgr) {
		sql = "insert into mgr(mgr_name, mgr_pwd) values(?,?)";
		int result = 0;
		try {
			// 获取数据库连接池对象
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1, mgr.getMgr_name());
			pstmt.setString(2, mgr.getMgr_pwd());
			// 执行SQL语句
			result = pstmt.executeUpdate(); // 影响条数
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(); // 释放连接
		}
		if (result > 0)
			return true;
		else
			return false;
	}
	/**
	 * 管理员登录
	 * 参数：用户名和密码
	 * @param book
	 * @return
	 */
	public boolean mgr_login(String mgr_name,String mgr_pwd) throws SQLException{
		getConn();
		sql = "select mgr_pwd from mgr where mgr_name=? and mgr_pwd=?";
		try {
			// 获取数据库连接池对象
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mgr_name);
			pstmt.setString(2, mgr_pwd);
			rs = pstmt.executeQuery();
			// 执行SQL语句
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next())
			return true;
		else
			return false;
	}
	/**
	 * 管理员查询
	 * 参数：用户名和密码
	 * @param book
	 * @return
	 */
	public ArrayList<Mgr> getByRs(ResultSet rs) {
		try {
			if(rs == null || !rs.next())
				return null;
			mgrs.clear();
			do {
				mgr = new Mgr();
				mgr.setMgr_id(rs.getInt("mgr_id"));
				mgr.setMgr_name(rs.getString("mgr_name"));
				mgr.setMgr_pwd(rs.getString("mgr_pwd"));
				mgrs.add(mgr);
			}while (rs.next());
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return mgrs;
	}
	
	public ArrayList<Mgr> getByName(String mgr_name) {
		conn = this.getConn();
		
		if (mgr_name == null)
			mgr_name = "";
		try {
			//主要用于存储过程
			CallableStatement cs = (CallableStatement) conn.prepareCall("{call getByNameProc(?)}");
			cs.setString(1, mgr_name);
			rs = cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//close();
		}
		return getByRs(rs);
	}
	
	public Mgr getmgrById(int id) {
		
		Mgr mgr = new Mgr();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1.打开数据库连接
			conn = this.getConn();

			// 2.创建执行sql语句对象
			stmt = conn.createStatement();

			// 3.发送sql语句到mysql
			String sql = "select * from cart where mgr_id =" + id;
			rs = stmt.executeQuery(sql);
			// rs结果集--->遍历--->封装product--->放入ArrayList
			
			while (rs.next())// 循环一次只代表一行
			{
				//Product p = new Product();
				mgr.setMgr_id(rs.getInt("mgr_id"));
				mgr.setMgr_name(rs.getString("mgr_name"));
				mgr.setMgr_pwd(rs.getString("mgr_pwd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return mgr;
	}
//	//显示所有的cart列表内容
//	
//	public ArrayList<Cart> showcart(ArrayList<Cart> list) {
//		sql = "select sp_cfid,sp_cfname,sp_size,sp_temp,sp_sweet,sp_price,sp_cup from cart";;
//		int result = 0;
//		ResultSet resultSet = null;
//		try {
//			// 获取数据库连接池对象
//			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
//			resultSet = pstmt.executeQuery(); 
//			while (resultSet.next()) {
//	            int sp_cfid = resultSet.getInt("sp_cfid");
//	            String sp_cfname = resultSet.getString("sp_cfname");
//	            String sp_size = resultSet.getString("sp_size");
//	            String sp_temp = resultSet.getString("sp_temp");
//	            String sp_sweet = resultSet.getString("sp_sweet");
//	            float sp_price = resultSet.getFloat("sp_price");
//	            int sp_cup = resultSet.getInt("sp_cup");
//	            Cart cart = new Cart(sp_cfid,sp_cfname,sp_size,sp_temp,sp_sweet,sp_price,sp_cup);
//	            list.add(cart);
//	        }
//	        for (Cart cart : list) {
//	            System.out.println(cart.getSp_id());
//	        }
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(); // 释放连接
//		}
//		return list;
//	}
	
	
	/**
	 * 管理员删除
	 * 参数：用户名和密码
	 * @param book
	 * @return
	 */
	
	
	
	

}
