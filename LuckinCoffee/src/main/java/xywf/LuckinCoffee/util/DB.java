package xywf.LuckinCoffee.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DB {
	private Properties p;
	private String driver;
	private String url;
	private String username;
	private String password;
	private Connection conn;
	public Connection getConn() {
		return conn;
	}
	
	public DB() {
		// 获取数据库配置信息，连接数据库
		try {
			p = new Properties();
			InputStream is = this.getClass().getResourceAsStream("/config.ini");
			p.load(is);
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			username = p.getProperty("username");
			password = p.getProperty("password");
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
