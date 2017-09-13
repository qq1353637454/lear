package com.java.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginFsilter implements Filter{

	
	/**
	 * 拦截过
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse,
			FilterChain filterchain) throws IOException, ServletException {

		HttpServletResponse response=(HttpServletResponse) servletresponse;
		HttpServletRequest request=(HttpServletRequest) servletrequest;
		HttpSession session=request.getSession();
		Object object=session.getAttribute("currentUser");
		String path=request.getServletPath();
//		System.out.println(path);
		//过滤用户请求
		if(object==null&&path.indexOf("login")<0 && path.indexOf("bootstrap")<0 && path.indexOf("images")<0){
			response.sendRedirect("login.jsp");
		}else{
			filterchain.doFilter(servletrequest, servletresponse);
		}
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
