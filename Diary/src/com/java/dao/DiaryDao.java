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
	 * ��ѯ�ռ��б���������
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<Diary> diaryList(Connection con,PageBean pageBean,Diary s_diary) throws Exception{
		List<Diary> diaryList=new ArrayList<Diary>();
		StringBuffer sb=new StringBuffer("select * from t_diary t1,t_diarytype t2 where t1.typeId=t2.diaryTypeId");
		//������־DAO
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		//�����ڲ�ѯDAO
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
			sb.append(" and date_format(t1.releaseDate,'%Y��%m��')='"+s_diary.getReleaseDateStr()+"'");
		}
		//��ѯ����ʱ������ѯ
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
	 * �ܼ�¼
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
			sb.append(" and date_format(releaseDate,'%Y��%m��')='"+s_diary.getReleaseDateStr()+"'");
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
	 * �����ڲ�ѯ
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<Diary> diaryCounList(Connection con) throws SQLException{
		List<Diary> diaryCountList = new ArrayList<Diary>();
		String sql="select date_format(releaseDate,'%Y��%m��') as releaseDateStr ,count(*) as diaryCount from t_diary group by date_format(releaseDate,'%Y��%m��') order by date_format(releaseDate,'%Y��%m��') desc;";
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
	//������־ID��ѯ��ϸ��Ϣ
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
	//����ID����ռ�����
	public int diaryAdd(Connection con,Diary diary) throws SQLException{
		String sql="insert into t_diary values(null,?,?,?,now())";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, diary.getTitle());
		pstm.setString(1, diary.getContent());
		pstm.setInt(1, diary.getTypeId());
		return pstm.executeUpdate();
	}
	//����IDɾ���ռ�����
	public int diaryDelete(Connection con,String diaryId) throws SQLException{
		String sql="delete from t_diary where diaryId=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, diaryId);
		return pstm.executeUpdate();
	}
	//����ID�޸��ռ�����
	public int diaryUpdate(Connection con,Diary diary) throws SQLException{
		String sql="update t_diary set title=?,content=?,typeId=? where diaryId=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, diary.getTitle());
		pstm.setString(1, diary.getContent());
		pstm.setInt(1, diary.getTypeId());
		return pstm.executeUpdate();
	}
}
