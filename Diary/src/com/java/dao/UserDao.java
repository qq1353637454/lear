package com.java.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java.model.User;
import com.java.util.MD5Util;
import com.java.util.PropertiesUtil;

public class UserDao {
	
	/**
	 * µÇÂ¼
	 * @throws SQLException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public User getLogin(Connection con,User user) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException{
		User resultUser=null;
		String sql="select * from t_user where userName=? and password=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, user.getUserName());
		pstm.setString(2, MD5Util.EncoderPwdByMd5(user.getPassword()));
		ResultSet rs=pstm.executeQuery();
		if(rs.next()){
			resultUser=new User();
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setNickName(rs.getString("nickName"));
			resultUser.setImageName(PropertiesUtil.getValue("imageFile")+rs.getString("imageName"));
			resultUser.setMood(rs.getString("mood"));
		}
		return resultUser;
	}
}
