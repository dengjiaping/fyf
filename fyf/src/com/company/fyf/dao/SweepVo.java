package com.company.fyf.dao;

import java.io.Serializable;

import com.company.fyf.model.UserInfo;
import com.company.fyf.utils.FyfUtils;

public class SweepVo implements Serializable {
	
	private static final long serialVersionUID = -2174497667245702465L;
	private String userid ;
	private String username ;
	private String credit ;
	private String nickname ;
	
	public SweepVo(UserInfo info) {
		this.userid = info.getUserid() ;
		this.username = info.getUsername() ;
		this.credit = info.getCredit() ;
		this.nickname = info.getNickname() ;
	}
	
	public String getUserid() {
		return userid;
	}
	public String getUsername() {
		return username;
	}
	public String getEncryptUsername() {
		return FyfUtils.encryptPhone(username);
	}
	public String getCredit() {
		return credit;
	}
	public String getNickname() {
		return nickname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	

}
