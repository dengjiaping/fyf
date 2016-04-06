package com.company.fyf.model;

import net.tsz.afinal.annotation.sqlite.Table;

public class CreditRank {
	
	/*"userid": "9", 
    "username": "13444444434", 
    "nickname": "", 
    "sex": "0", 
    "avatar": "", 
    "credit": "17"*/
	
	private String userid ;
	private String username ;
	private String nickname ;
	private String sex;
	private String avatar;
	private String credit;

	public CreditRank() {
		// TODO Auto-generated constructor stub
	}

	public CreditRank(String userid, String username, String nickname,
			String sex, String avatar, String credit) {
		this.userid = userid;
		this.username = username;
		this.nickname = nickname;
		this.sex = sex;
		this.avatar = avatar;
		this.credit = credit;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	
	
	

}
