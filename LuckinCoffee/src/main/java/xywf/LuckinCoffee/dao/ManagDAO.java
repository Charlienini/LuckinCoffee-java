package xywf.LuckinCoffee.dao;


import xywf.LuckinCoffee.bean.Coffee;
import xywf.LuckinCoffee.bean.Manager;
import xywf.LuckinCoffee.bean.User;
import xywf.LuckinCoffee.util.DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.*;

import jakarta.servlet.http.HttpSession;

public class ManagDAO{
	
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<User> users = new ArrayList<User>();
	private User user;
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
	 * ע�����Ա
	 * �������û���������
	 * @param book
	 * @return
	 */
	public boolean addmanag(Manager manag) {
		sql = "insert into cf_manag(manag_name, manag_pass) values(?,?)";
		int result = 0;
		try {
			// ��ȡ���ݿ����ӳض���
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1, manag.getManag_name());
			pstmt.setString(2, manag.getManag_pass());
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
	public boolean managlogin(String manag_name,String manag_pass) throws SQLException{
		getConn();
		sql = "select manag_pass from cf_manag where manag_name=? and manag_pass=?";
		try {
			// ��ȡ���ݿ����ӳض���
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, manag_name);
			pstmt.setString(2, manag_pass);
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
	
	
	
}


