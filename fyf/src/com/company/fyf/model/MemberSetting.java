package com.company.fyf.model;

import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "MemberSetting")
public class MemberSetting {
	
	/**
	 *   "allowregister": "1",
        "regprotocol": " 欢迎您注册成为APP用户\r\n请仔细阅读下面的协议，只有接受协议才能继续进行注册。 ",
        "login_times": "10",
        "lock_hour": "0",
        "smscode_overtime": "300",
        "scanCredits": "1|2|3",
        "scanCredits_text": "",
        "admin_scan_times_oneday": "3"
	 */
	
	private int id ;
	
	private String allowregister ;
	private String regprotocol ;
	private String login_times ;
	private String lock_hour ;
	private String smscode_overtime ;
	private String scanCredits ;
	private String scanCredits_text ;
	private String admin_scan_times_oneday ;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAllowregister() {
		return allowregister;
	}
	public void setAllowregister(String allowregister) {
		this.allowregister = allowregister;
	}
	public String getRegprotocol() {
		return regprotocol;
	}
	public void setRegprotocol(String regprotocol) {
		this.regprotocol = regprotocol;
	}
	public String getLogin_times() {
		return login_times;
	}
	public void setLogin_times(String login_times) {
		this.login_times = login_times;
	}
	public String getLock_hour() {
		return lock_hour;
	}
	public void setLock_hour(String lock_hour) {
		this.lock_hour = lock_hour;
	}
	public String getSmscode_overtime() {
		return smscode_overtime;
	}
	public void setSmscode_overtime(String smscode_overtime) {
		this.smscode_overtime = smscode_overtime;
	}
	public String getScanCredits() {
		return scanCredits;
	}
	public void setScanCredits(String scanCredits) {
		this.scanCredits = scanCredits;
	}
	public String getScanCredits_text() {
		return scanCredits_text;
	}
	public void setScanCredits_text(String scanCredits_text) {
		this.scanCredits_text = scanCredits_text;
	}
	public String getAdmin_scan_times_oneday() {
		return admin_scan_times_oneday;
	}
	public void setAdmin_scan_times_oneday(String admin_scan_times_oneday) {
		this.admin_scan_times_oneday = admin_scan_times_oneday;
	}
	
	

}
