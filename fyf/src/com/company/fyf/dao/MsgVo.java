package com.company.fyf.dao;

import java.io.Serializable;

import com.company.fyf.utils.FyfUtils;

public class MsgVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 680414487796338918L;
	
	
	private String id ;
	private String catid ;
	private String catname ;
	private String title ;
	private String thumb ;
	private String description ;
	private String updatetime ;
	private String content ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatid() {
		return catid;
	}
	public void setCatid(String catid) {
		this.catid = catid;
	}
	public String getCatname() {
		return catname;
	}
	public void setCatname(String catname) {
		this.catname = catname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public String getJavaUpdatetime() {
		return FyfUtils.getJavaTimeTemp(updatetime);
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
