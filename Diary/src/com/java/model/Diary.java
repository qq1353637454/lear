package com.java.model;

import java.util.Date;


public class Diary {
	
	private int diaryId;
	private String title;
	private String content;
	private int typeId=-1;
	private String typeName;
	private Date releaseDate;
	private String releaseDateStr;
	private int diaryCount;
	public int getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public int getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Diary(int diaryId, String title, String content, int typeId,
			Date releaseDate,String releaseDateStr,int diaryCount) {
		super();
		this.diaryId = diaryId;
		this.title = title;
		this.content = content;
		this.typeId = typeId;
		this.releaseDate = releaseDate;
		this.releaseDateStr=releaseDateStr;
		this.diaryCount=diaryCount;
	}
	public Diary() {
		super();
	}
	
}
