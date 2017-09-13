package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.model.Diary;
import com.java.model.PageBean;
import com.java.util.DateUtil;
import com.java.util.StringUtil;

public class DiaryDao {
	
	/**
	 * 查询日记列表所有数据
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<Diary> diaryList(Connection con,PageBean pageBean,Diary s_diary) throws Exception{
		List<Diary> diaryList=new ArrayList<Diary>();
		StringBuffer sb=new StringBuffer("select * from t_diary t1,t_diarytype t2 where t1.typeId=t2.diaryTypeId");
		//搜索日志DAO
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		//按日期查询DAO
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
			sb.append(" and date_format(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
		}
		//查询所有时降续查询
		sb.append(" order by t1.releaseDate desc ");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstm = con.prepareStatement(sb.toString());
		ResultSet rs=pstm.executeQuery();
		while(rs.next()){
			Diary diary=new Diary();
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
			diaryList.add(diary);
		}
		return diaryList;
	}
	
	/**
	 * 总记录
	 * @throws Exception 
	 */
	public int diaryCount(Connection con,Diary s_diary) throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_diary t1,t_diarytype t2 where t1.typeId=t2.diaryTypeId");
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"'");
		}
		if(s_diary.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
			sb.append(" and date_format(releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
		}
		PreparedStatement pstm = con.prepareStatement(sb.toString());
		ResultSet rs=pstm.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	/**
	 * 按日期查询
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<Diary> diaryCounList(Connection con) throws SQLException{
		List<Diary> diaryCountList = new ArrayList<Diary>();
		String sql="select date_format(releaseDate,'%Y年%m月') as releaseDateStr ,count(*) as diaryCount from t_diary group by date_format(releaseDate,'%Y年%m月') order by date_format(releaseDate,'%Y年%m月') desc;";
		PreparedStatement pstm = con.prepareStatement(sql);
		ResultSet rs=pstm.executeQuery();
		while(rs.next()){
			Diary diary=new Diary();
			diary.setReleaseDateStr(rs.getString("releaseDateStr"));
			diary.setDiaryCount(rs.getInt("diaryCount"));
			diaryCountList.add(diary);
		}
		return diaryCountList;
	}
	//根据日志ID查询详细信息
	public Diary diaryShow(Connection con,String diaryId) throws Exception{
		String sql="select * from t_diary t1,t_diarytype t2 where t1.typeId=t2.diaryTypeId and t1.diaryId=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, diaryId);
		ResultSet rs=pstm.executeQuery();
		Diary diary=new Diary();
		if(rs.next()){
			diary.setDiaryId(rs.getInt("diaryId"));
			diary .setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setTypeId(rs.getInt("typeId"));
			diary.setTypeName(rs.getString("typeName"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
		}
		return diary;
	}
	//根据ID添加日记内容
	public int diaryAdd(Connection con,Diary diary) throws SQLException{
		String sql="insert into t_diary values(null,?,?,?,now())";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, diary.getTitle());
		pstm.setString(1, diary.getContent());
		pstm.setInt(1, diary.getTypeId());
		return pstm.executeUpdate();
	}
	//根据ID删除日记内容
	public int diaryDelete(Connection con,String diaryId) throws SQLException{
		String sql="delete from t_diary where diaryId=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, diaryId);
		return pstm.executeUpdate();
	}
	//根据ID修改日记内容
	public int diaryUpdate(Connection con,Diary diary) throws SQLException{
		String sql="update t_diary set title=?,content=?,typeId=? where diaryId=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, diary.getTitle());
		pstm.setString(1, diary.getContent());
		pstm.setInt(1, diary.getTypeId());
		return pstm.executeUpdate();
	}
}
