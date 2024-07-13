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
	 * ����Աע��
	 * �������û���������
	 * @param book
	 * @return
	 */
	public boolean mgr_register(Mgr mgr) {
		sql = "insert into mgr(mgr_name, mgr_pwd) values(?,?)";
		int result = 0;
		try {
			// ��ȡ���ݿ����ӳض���
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1, mgr.getMgr_name());
			pstmt.setString(2, mgr.getMgr_pwd());
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
	 * ����Ա��¼
	 * �������û���������
	 * @param book
	 * @return
	 */
	public boolean mgr_login(String mgr_name,String mgr_pwd) throws SQLException{
		getConn();
		sql = "select mgr_pwd from mgr where mgr_name=? and mgr_pwd=?";
		try {
			// ��ȡ���ݿ����ӳض���
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mgr_name);
			pstmt.setString(2, mgr_pwd);
			rs = pstmt.executeQuery();
			// ִ��SQL���
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next())
			return true;
		else
			return false;
	}
	/**
	 * ����Ա��ѯ
	 * �������û���������
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
			//��Ҫ���ڴ洢����
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
			// 1.�����ݿ�����
			conn = this.getConn();

			// 2.����ִ��sql������
			stmt = conn.createStatement();

			// 3.����sql��䵽mysql
			String sql = "select * from cart where mgr_id =" + id;
			rs = stmt.executeQuery(sql);
			// rs�����--->����--->��װproduct--->����ArrayList
			
			while (rs.next())// ѭ��һ��ֻ����һ��
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
//	//��ʾ���е�cart�б�����
//	
//	public ArrayList<Cart> showcart(ArrayList<Cart> list) {
//		sql = "select sp_cfid,sp_cfname,sp_size,sp_temp,sp_sweet,sp_price,sp_cup from cart";;
//		int result = 0;
//		ResultSet resultSet = null;
//		try {
//			// ��ȡ���ݿ����ӳض���
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
//			close(); // �ͷ�����
//		}
//		return list;
//	}
	
	
	/**
	 * ����Աɾ��
	 * �������û���������
	 * @param book
	 * @return
	 */
	
	
	
	

}
