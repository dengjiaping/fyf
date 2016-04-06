package com.company.fyf.model;

import net.tsz.afinal.annotation.sqlite.Table;

import com.company.fyf.utils.FyfUtils;

public class Checkin {
	
	/*
	 *  "id": "7", 
                "username": "13444444434", 
                "checkintime": "1445087601", 
                "content": "API返回格式为JSON，开发者可以根据实际需求选择是否将JSON反序列化成对应类型的对象。", 
                "credit_add": "1", 
                "credit_add_reason": "签到奖励"
                onSuccess : jsonStr = {"result":{"mdays":1,"credit_add":1,"credit_add_reason":"\u7b7e\u5230\u5956\u52b1","username":"13811111111","content":"","checkintime":1446962546,"day":"20151108","credit":"11"},"success":1}
	 */
	
	private String mdays;
	private String credit_add;
	private String credit_add_reason;
	private String username;
	private String content ;
	private String checkintime;
	private String day ;
	private String credit;
	

	public Checkin() {
		// TODO Auto-generated constructor stub
	}


	public String getMdays() {
		return mdays;
	}


	public void setMdays(String mdays) {
		this.mdays = mdays;
	}


	public String getCredit_add() {
		return credit_add;
	}


	public void setCredit_add(String credit_add) {
		this.credit_add = credit_add;
	}


	public String getCredit_add_reason() {
		return credit_add_reason;
	}


	public void setCredit_add_reason(String credit_add_reason) {
		this.credit_add_reason = credit_add_reason;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getCheckintime() {
		return checkintime;
	}
	public String getJavaCheckintime() {
		return FyfUtils.getJavaTimeTemp(checkintime);
	}


	public void setCheckintime(String checkintime) {
		this.checkintime = checkintime;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public String getCredit() {
		return credit;
	}


	public void setCredit(String credit) {
		this.credit = credit;
	}

	
	
	
	

}
