package com.java.model;

public class DiaryType {
	
	private int diaryTypeId;
	private String typeName;
	private int diaryCount;
	public int getDiaryTypeId() {
		return diaryTypeId;
	}
	public void setDiaryTypeId(int diaryTypeId) {
		this.diaryTypeId = diaryTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}
	public DiaryType(int diaryTypeId, String typeName, int diaryCount) {
		super();
		this.diaryTypeId = diaryTypeId;
		this.typeName = typeName;
		this.diaryCount = diaryCount;
	}
	public DiaryType() {
		super();
	}
	
}
