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
	
	public ArrayList<Coffee> getAll(){
		// sql���
		sql = "select * from coffee";
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
			//��Ҫ���ڴ洢����
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
			// 1.�����ݿ�����
			conn = this.getConn();

			// 2.����ִ��sql������
			stmt = conn.createStatement();

			// 3.����sql��䵽mysql
			String sql = "select * from coffee where id =" + id;
			rs = stmt.executeQuery(sql);
			// rs�����--->����--->��װproduct--->����ArrayList
			
			while (rs.next())// ѭ��һ��ֻ����һ��
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
	 * ��ӿ���
	 * ��������id��������в��������û��Լ�����
	 * @param book
	 * @return
	 */
	public boolean add(Coffee coffee) {
		sql = "insert into coffee(coffeename , size , temp , sweet , price) values(?,?,?,?,?)";
		int result = 0;
		try {
			// ��ȡ���ݿ����ӳض���
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1, coffee.getCoffeename());
			pstmt.setString(2, coffee.getSize());
			pstmt.setString(3, coffee.getTemp());
			pstmt.setString(4, coffee.getSweet());
			pstmt.setFloat(5, coffee.getPrice());
			// ִ��SQL���
			result = pstmt.executeUpdate(); // Ӱ������
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(); // �ͷ�����
		}
		if (result > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * �޸Ŀ���
	 * ���������в���
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
	 * ɾ������
	 * ������id
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
	
	// ���ﳵ

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
			// ��ȡ���ݿ����ӳض���
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1, coffee.getCoffeename());
			pstmt.setString(2, coffee.getSize());
			pstmt.setString(3, coffee.getTemp());
			pstmt.setString(4, coffee.getSweet());
			pstmt.setFloat(5, coffee.getPrice());
			pstmt.setInt(6, 1);
			pstmt.setInt(7, coffee.getId());
			// ִ��SQL���
			result = pstmt.executeUpdate(); // Ӱ������
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(); // �ͷ�����
		}
		if (result > 0)
			return true;
		else
			return false;
	}
	
	
	
	/**
	 * �޸Ŀ���
	 * ���������в���
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
