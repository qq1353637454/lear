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

import com.java.dao.DiaryDao;
import com.java.dao.UserDao;
import com.java.model.Diary;
import com.java.model.User;
import com.java.util.DbUtil;

public class DiaryServlet extends HttpServlet {
	DbUtil dbUtil = new DbUtil();
	DiaryDao diaryDao = new DiaryDao();
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
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		if ("show".equals(action)) {
			diaryShow(req,resp);
		}else if("preSave".equals(action)){
			diarySave(req,resp);
		}else if("delete".equals(action)){
			diaryDelete(req,resp);
		}
	}
	
	//����ռ���Ϣ
	private void diarySave(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setAttribute("mainPage", "diary/diarySave.jsp");
			req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//����IDɾ����־��Ϣ
	private void diaryDelete(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String diaryId=req.getParameter("diaryId");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			diaryDao.diaryDelete(con, diaryId);
			req.getRequestDispatcher("main?all=true").forward(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	//����ID��ѯ�ռ���Ϣ����
	private void diaryShow(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String diaryId=req.getParameter("diaryId");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			Diary diaryShow=diaryDao.diaryShow(con, diaryId);
			req.setAttribute("diaryShow", diaryShow);
			req.setAttribute("mainPage", "diary/diaryShow.jsp");
			req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

}
