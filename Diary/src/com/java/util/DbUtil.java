package com.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	
//	private String dbUrl="jdbc:mysql://localhost:3306/db_diary";
//	private String dbUserName="root";
//	private String dbPassword="123456";
//	private String jdbcName="com.mysql.jdbc.Driver";
	
	
	/**
	 * ��ȡ���ݿ�����
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() throws Exception{
		Class.forName(PropertiesUtil.getValue("jdbcName"));
		Connection con=DriverManager.getConnection(PropertiesUtil.getValue("dbUrl"), PropertiesUtil.getValue("userName"), PropertiesUtil.getValue("password"));
		return con;
	}
	
	/**
	 * �ر����ݿ�
	 * @param con
	 * @throws SQLException
	 */
	public void closeCon(Connection con) throws SQLException{
		if(con!=null){
			con.close();
		}
	}
	
	/**
	 * ����
	 */
	public static void main(String[] args) {
		DbUtil dbUtil=new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("���ݿ����ӳɹ���");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ݿ�����ʧ�ܣ�");
		}
	}
}
