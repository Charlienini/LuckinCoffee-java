package xywf.LuckinCoffee.dao;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import xywf.LuckinCoffee.bean.Cart;
import xywf.LuckinCoffee.bean.Coffee;

import xywf.LuckinCoffee.util.DB;

public class CoffeeDAO {
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<Coffee> coffees = new ArrayList<Coffee>();
	private Coffee coffee;
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
	
	public ArrayList<Coffee> getAll(){
		// sql语句
		sql = "select * from coffee";
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
	
	
	public ArrayList<Coffee> getByName(String coffeename) {
		conn = this.getConn();
		
		if (coffeename == null)
			coffeename = "";
		try {
			//主要用于存储过程
			CallableStatement cs = (CallableStatement) conn.prepareCall("{call getByNameProc(?)}");
			cs.setString(1, coffeename);
			rs = cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//close();
		}
		return getByRs(rs);
	}
	
	public ArrayList<Coffee> getByRs(ResultSet rs) {
		try {
			if(rs == null || !rs.next())
				return null;
			coffees.clear();
			do {
				coffee = new Coffee();
				coffee.setId(rs.getInt("id"));
				coffee.setCoffeename(rs.getString("coffeename"));
				coffee.setSize(rs.getString("size"));
				coffee.setTemp(rs.getString("temp"));
				coffee.setSweet(rs.getString("sweet"));
				coffee.setPrice(rs.getFloat("price"));
				coffees.add(coffee);
			}while (rs.next());
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return coffees;
	}
	
	public Coffee getById(int id) {
		conn = this.getConn();
		sql = "select * from coffee where id = ?";
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			close();
		}
		return getByRs(rs).get(0);
	}
	
	
	public Coffee getcfById(int id) {
		
		Coffee coffee = new Coffee();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1.打开数据库连接
			conn = this.getConn();

			// 2.创建执行sql语句对象
			stmt = conn.createStatement();

			// 3.发送sql语句到mysql
			String sql = "select * from coffee where id =" + id;
			rs = stmt.executeQuery(sql);
			// rs结果集--->遍历--->封装product--->放入ArrayList
			
			while (rs.next())// 循环一次只代表一行
			{
				//Product p = new Product();
				coffee.setId(rs.getInt("id"));
				coffee.setCoffeename(rs.getString("coffeename"));
				coffee.setSize(rs.getString("size"));
				coffee.setTemp(rs.getString("temp"));
				coffee.setSweet(rs.getString("sweet"));
				coffee.setPrice(rs.getFloat("price"));
				//System.out.println(p.getName());
				//list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return coffee;
	}
	
	
	
	/**
	 * 添加咖啡
	 * 参数：除id以外的所有参数都让用户自己输入
	 * @param book
	 * @return
	 */
	public boolean add(Coffee coffee) {
		sql = "insert into coffee(coffeename , size , temp , sweet , price) values(?,?,?,?,?)";
		int result = 0;
		try {
			// 获取数据库连接池对象
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1, coffee.getCoffeename());
			pstmt.setString(2, coffee.getSize());
			pstmt.setString(3, coffee.getTemp());
			pstmt.setString(4, coffee.getSweet());
			pstmt.setFloat(5, coffee.getPrice());
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
	 * 修改咖啡
	 * 参数：所有参数
	 * @param book
	 * @return
	 */
	public boolean updateCoffee(Coffee coffee) {
		sql = "update coffee set coffeename=?, size=?, temp=?, sweet=?, price=?  where id=?";
		int result = 0;
		try {
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1,  coffee.getCoffeename());
			pstmt.setString(2,  coffee.getSize());
			pstmt.setString(3,  coffee.getTemp());
			pstmt.setString(4,  coffee.getSweet());
			pstmt.setFloat(5,  coffee.getPrice());
			pstmt.setInt(6,  coffee.getId());
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 删除咖啡
	 * 参数：id
	 * @param book
	 * @return
	 */
	public boolean delete(int id) {
		sql = "delete from coffee where id = ?";
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
	
	// 购物车

	public boolean addnum(int sp_cfid) {
		sql = "update cart set sp_cup = sp_cup + 1 where sp_cfid = ?";
		int result = 0;
		try {
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setInt(1, sp_cfid);
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
	
	public boolean addcart(Coffee coffee) {
		sql = "insert into cart(sp_cfname , sp_size , sp_temp , sp_sweet , sp_price, sp_cup , sp_cfid) values(?,?,?,?,?,?,?)";
		
		int result = 0;
		try {
			// 获取数据库连接池对象
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1, coffee.getCoffeename());
			pstmt.setString(2, coffee.getSize());
			pstmt.setString(3, coffee.getTemp());
			pstmt.setString(4, coffee.getSweet());
			pstmt.setFloat(5, coffee.getPrice());
			pstmt.setInt(6, 1);
			pstmt.setInt(7, coffee.getId());
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
	 * 修改咖啡
	 * 参数：所有参数
	 * @param book
	 * @return
	 */
	public boolean update(Coffee coffee, int id) {
		sql = "update coffee set coffeename=?, size=?, temp=?, sweet=?, price=? where id=?";
		int result = 0;
		try {
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1,  coffee.getCoffeename());
			pstmt.setString(2,  coffee.getSize());
			pstmt.setString(3,  coffee.getTemp());
			pstmt.setString(4,  coffee.getSweet());
			pstmt.setFloat(5,  coffee.getPrice());
			pstmt.setInt(6,  id);
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0)
			return true;
		else
			return false;
	}
}
