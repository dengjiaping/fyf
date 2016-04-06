package com.company.fyf.model;

import net.tsz.afinal.annotation.sqlite.Table;

public class Credit {
	/*
	 "id":"10",
     "username":"13444444434",
     "amount":"10",
     "balance":"10",
     "addtime":"1445000418",
     "reason":"注册奖励",
     "note":"127.0.0.1",
     "editor":"system"*/
    	 
    private String id ;
	private String username;
	private String amount;
	private String balance;
	private String addtime;
	private String reason;
	private String note;
	private String editor;

	public Credit() {
	}

	public Credit(String id, String username, String amount, String balance,
			String addtime, String reason, String note, String editor) {
		this.id = id;
		this.username = username;
		this.amount = amount;
		this.balance = balance;
		this.addtime = addtime;
		this.reason = reason;
		this.note = note;
		this.editor = editor;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	
	

}
