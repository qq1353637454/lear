package com.java.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.dao.UserDao;
import com.java.model.User;
import com.java.util.DbUtil;

public class LoginServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	UserDao userDao=new UserDao();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		String userName=req.getParameter("userName");
		String password=req.getParameter("password");
		String remember=req.getParameter("remember");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			User user=new User(userName, password);
			User currentUser=userDao.getLogin(con, user);
			if(currentUser == null){
				req.setAttribute("user", user);
				req.setAttribute("error", "用户名或密码错误");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}else{
				if("remember-me".equals(remember)){
					rememberMe(userName,password,resp);
				}
				session.setAttribute("currentUser", currentUser);
				System.out.println(currentUser);
				resp.sendRedirect("main");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 记住密码
	 * @param userName
	 * @param password
	 * @param resp
	 */
	private void rememberMe(String userName,String password,HttpServletResponse resp){
		Cookie user = new Cookie("user", userName+"-"+password);
		user.setMaxAge(1*60*60*24*7);
		resp.addCookie(user);
	}
	
}
