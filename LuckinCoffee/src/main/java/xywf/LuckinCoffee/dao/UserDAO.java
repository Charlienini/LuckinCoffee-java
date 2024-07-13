package xywf.LuckinCoffee.dao;


import xywf.LuckinCoffee.bean.Coffee;
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

public class UserDAO{
	
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
	 * ע���û�
	 * �������û���������
	 * @param book
	 * @return
	 */
	public boolean add(User user) {
		sql = "insert into cf_user(username, pwd, locate) values(?,?,?)";
		int result = 0;
		try {
			// ��ȡ���ݿ����ӳض���
			pstmt = (PreparedStatement) getConn().prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPwd());
			pstmt.setString(3, user.getLocate());
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
	
	
	public boolean login(String username,String pwd) throws SQLException{
		getConn();
		sql = "select pwd from cf_user where username=? and pwd=?";
		try {
			// ��ȡ���ݿ����ӳض���
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, pwd);
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


