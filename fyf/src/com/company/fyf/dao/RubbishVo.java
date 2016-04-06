package com.company.fyf.dao;

import java.io.Serializable;

import com.company.fyf.utils.FyfUtils;

public class RubbishVo implements Serializable{

	private static final long serialVersionUID = 287272655358386011L;
	private String id;
	private String username;
	private String typeid;
	private String name;
	private String complete;
	private String pic_url;
	private String note;
	private String addtime;
	private String updatetime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAddtime() {
		return addtime;
	}
	public String getJavaAddtime() {
		return FyfUtils.getJavaTimeTemp(addtime);
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
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
	
	
}
