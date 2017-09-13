package com.java.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.dao.DiaryDao;
import com.java.dao.DiaryTypeDao;
import com.java.model.Diary;
import com.java.model.PageBean;
import com.java.util.DbUtil;
import com.java.util.PropertiesUtil;
import com.java.util.StringUtil;

public class MainServlet extends HttpServlet{

	DiaryDao diarydao=new DiaryDao();
	DbUtil dbUtil=new DbUtil();
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	/**
	 * 查询分页
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
		//接收乱码
		req.setCharacterEncoding("UTF-8");
		HttpSession session=req.getSession();
		//获取页面传过来的值
		String typeId=req.getParameter("s_typeId");
		String releaseDateStr=req.getParameter("s_releaseDateStr");
		String s_title=req.getParameter("s_title");
		String all=req.getParameter("all");
		String page = req.getParameter("page");
		Diary diary=new Diary();
		if("true".equals(all)){
			if(StringUtil.isNotEmpty(s_title)){
				diary.setTitle(s_title);
			}
			session.removeAttribute("s_typeId");
			session.removeAttribute("s_releaseDateStr");
			session.setAttribute("s_title", s_title);
		}else{
			if(StringUtil.isNotEmpty(typeId)){
				diary.setTypeId(Integer.parseInt(typeId));
				session.setAttribute("s_typeId",typeId);
				//清理Session
				session.removeAttribute("s_releaseDateStr");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isNotEmpty(releaseDateStr)){
				releaseDateStr=new String(releaseDateStr.getBytes("ISO-8859-1"),"utf-8");
				diary.setReleaseDateStr(releaseDateStr);
				session.setAttribute("s_releaseDateStr",releaseDateStr);
				//清理Session
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isEmpty(typeId)){
				Object o=session.getAttribute("s_typeId");
				if(o!=null){
					diary.setTypeId(Integer.parseInt((String)o));
				}
			}
			if(StringUtil.isEmpty(releaseDateStr)){
				Object o=session.getAttribute("s_releaseDateStr");
				if(o!=null){
					diary.setReleaseDateStr((String)o);
				}
			}
			if(StringUtil.isEmpty(s_title)){
				Object o=session.getAttribute("s_title");
				if(o!=null){
					diary.setTitle((String)o);
				}
			}
		}
		if(StringUtil.isEmpty(page)){
			page="1";//当前页
		}
		Connection con=null;
		//当前页，总记录数
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		try {
			con=dbUtil.getCon();
			//查询所有数据
			List<Diary> diarylist=diarydao.diaryList(con,pageBean,diary);
			//查询总记录数（分页）
			int total=diarydao.diaryCount(con,diary);//总记录数
			
			String pageCode=this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			req.setAttribute("pageCode", pageCode);
			req.setAttribute("diarylist", diarylist);
			session.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(con));
			session.setAttribute("diaryCounList", diarydao.diaryCounList(con));
			req.setAttribute("mainPage", "diary/diaryList.jsp");
			req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 算法求分页
	 * @param totalNum  总记录数（10）
	 * @param currentPage 当前页
	 * @param pageSize  显示每页页数
	 * @return
	 */
	public String genPagation(int totalNum,int currentPage,int pageSize){
		int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		pageCode.append("<li><a href='main?page=1'>首页</a></li>");
		if(currentPage==1){
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else{
			pageCode.append("<li><a href='main?page="+(currentPage-1)+"'>上一页</a></li>");
		}
		//循环显示五排数字
		for(int i=currentPage-2;i<=currentPage+2;i++){
			if(i<1||i>totalPage){
				continue;
			}
			if(i==currentPage){
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			}else{
				pageCode.append("<li><a href='main?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage){
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		}else{
			pageCode.append("<li><a href='main?page="+(currentPage+1)+"'>下一页</a></li>");
		}
		pageCode.append("<li><a href='main?page="+totalPage+"'>尾页</a></li>");
		return pageCode.toString();
	}
}
