package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.model.Diary;
import com.java.model.DiaryType;

public class DiaryTypeDao {
	
	
	/**
	 * 按日志类别查询
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<DiaryType> diaryTypeCountList(Connection con) throws SQLException{
		List<DiaryType> diaryTypeCountList = new ArrayList<DiaryType>();
		String sql="select diaryTypeId,typeName,count(diaryId) as diaryCount from t_diary RIGHT JOIN t_diaryType ON t_diary.typeId=t_diaryType.diaryTypeId GROUP BY typeName;";
		PreparedStatement pstm=con.prepareStatement(sql);
		ResultSet rs=pstm.executeQuery();
		while(rs.next()){
			DiaryType diaryType=new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
			diaryType.setDiaryCount(rs.getInt("diaryCount"));
			diaryTypeCountList.add(diaryType);
		}
		
		return diaryTypeCountList;
	}
	
}
