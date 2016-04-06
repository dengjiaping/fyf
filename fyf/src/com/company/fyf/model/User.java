package com.company.fyf.model;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name="user")
public class User {
	
	@Id
	private String username ;
	private String psd ;
	private int rememberName ;//1 = 自动登录 0 = 非自动登录
	private long localLoginTime ;
	private int auto = 1; //1 = 自动登录 0 = 非自动登录
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPsd() {
		return psd;
	}
	public void setPsd(String psd) {
		this.psd = psd;
	}
	public long getLocalLoginTime() {
		return localLoginTime;
	}
	public void setLocalLoginTime(long localLoginTime) {
		this.localLoginTime = localLoginTime;
	}
	public int getAuto() {
		return auto;
	}
	public void setAuto(int auto) {
		this.auto = auto;
	}

	public int getRememberName() {
		return rememberName;
	}

	public void setRememberName(int rememberName) {
		this.rememberName = rememberName;
	}
	
	
	

}
