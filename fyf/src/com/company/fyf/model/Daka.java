package com.company.fyf.model;

import com.company.fyf.utils.FyfUtils;

public class Daka {
	
	 /*"id": "7", 
     "username": "13444444444", 
     "worktime": "1445319325", 
     "workofftime": "0"
      {"result":{"id":"7","username":"13200000000","worktime":"1446991230","workofftime":"0","day":"20151108"},"success":1}
     *
     */
	private String id;
	private String username ;
	private String worktime;
	private String workofftime ;
	private String day ;

	public Daka() {
	}


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

	public String getWorktime() {
		return worktime;
	}
	
	public String getJavaWorktime() {
		return FyfUtils.getJavaTimeTemp(worktime);
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	public String getWorkofftime() {
		return workofftime;
	}
	
	public String getJavaWorkofftime() {
		return FyfUtils.getJavaTimeTemp(workofftime);
	}

	public void setWorkofftime(String workofftime) {
		this.workofftime = workofftime;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}
	
	
	
	

}
