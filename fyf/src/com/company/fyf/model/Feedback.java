package com.company.fyf.model;

public class Feedback {
	
	/*"id": "5", 
    "username": "13444444443", 
    "content": "fewfewfew", 
    "addtime": "1445256156", 
    "isview": "0", 
    "reply": "", 
    "reply_time": "0"*/
	
	private String id ;
	private String username;
	private String content;
	private String addtime ;
	private String isview;
	private String reply;
	private String reply_time ;

	public Feedback() {
	}

	public Feedback(String id, String username, String content,
			String addtime, String isview, String reply, String reply_time) {
		this.id = id;
		this.username = username;
		this.content = content;
		this.addtime = addtime;
		this.isview = isview;
		this.reply = reply;
		this.reply_time = reply_time;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getIsview() {
		return isview;
	}

	public void setIsview(String isview) {
		this.isview = isview;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getReply_time() {
		return reply_time;
	}

	public void setReply_time(String reply_time) {
		this.reply_time = reply_time;
	}
	
	

}
