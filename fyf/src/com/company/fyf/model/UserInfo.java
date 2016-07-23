package com.company.fyf.model;

import com.company.fyf.utils.FyfUtils;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name="UserInfo")
public class UserInfo{

	@Id
	private String userid ;
	private String username ;
	private String nickname ;
	private String regdate ;
	private String lastdate ;
	private String birthday ;
	private String sex ;
	private String areaid ;
	private String fjy_quyu ;
	private String fjy_banshichu ;
	private String checkin_month_total ;
	private String checkin_lasted_time ;
	private String daka_lasted_work ;
	private String daka_lasted_workoff ;
	private String address ;
	private String groupid ;
	private String groupname ;
	private String credit ;
	private String islock ;
	private String connectid ;
	private String credit_rank ;
	private String[] cookies ;
	
//	private long localLoginTime ;
//	private int auto = 1; //1 = 自动登录 0 = 非自动登录
//	
//	private String psd;
//	private int rememberPsd = 0;  // 1 = 是 0 = 否
	
	public UserInfo() {
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
	
	public String getEncryptUsername() {
		return FyfUtils.encryptPhone(username);
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

	public String getRegdate() {
		return regdate;
	}
	public String getJavaRegdate() {
		return FyfUtils.getJavaTimeTemp(regdate);
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getLastdate() {
		return lastdate;
	}
	public String getJavaLastdate() {
		return FyfUtils.getJavaTimeTemp(lastdate);
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getFjy_quyu() {
		return fjy_quyu;
	}

	public void setFjy_quyu(String fjy_quyu) {
		this.fjy_quyu = fjy_quyu;
	}

	public String getFjy_banshichu() {
		return fjy_banshichu;
	}

	public void setFjy_banshichu(String fjy_banshichu) {
		this.fjy_banshichu = fjy_banshichu;
	}

	public String getCheckin_month_total() {
		return checkin_month_total;
	}

	public void setCheckin_month_total(String checkin_month_total) {
		this.checkin_month_total = checkin_month_total;
	}

	public String getCheckin_lasted_time() {
		return checkin_lasted_time;
	}
	public String getJavaCheckin_lasted_time() {
		return FyfUtils.getJavaTimeTemp(checkin_lasted_time);
	}

	public void setCheckin_lasted_time(String checkin_lasted_time) {
		this.checkin_lasted_time = checkin_lasted_time;
	}

	public String getDaka_lasted_work() {
		return daka_lasted_work;
	}
	public String getJavaDaka_lasted_work() {
		return FyfUtils.getJavaTimeTemp(daka_lasted_work);
	}

	public void setDaka_lasted_work(String daka_lasted_work) {
		this.daka_lasted_work = daka_lasted_work;
	}

	public String getDaka_lasted_workoff() {
		return daka_lasted_workoff;
	}
	public String getJavaDaka_lasted_workoff() {
		return FyfUtils.getJavaTimeTemp(daka_lasted_workoff);
	}

	public void setDaka_lasted_workoff(String daka_lasted_workoff) {
		this.daka_lasted_workoff = daka_lasted_workoff;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getIslock() {
		return islock;
	}

	public void setIslock(String islock) {
		this.islock = islock;
	}

	public String getConnectid() {
		return connectid;
	}

	public void setConnectid(String connectid) {
		this.connectid = connectid;
	}

//	public long getLocalLoginTime() {
//		return localLoginTime;
//	}
//
//	public void setLocalLoginTime(long localLoginTime) {
//		this.localLoginTime = localLoginTime;
//	}

	public String getCredit_rank() {
		return credit_rank;
	}

	public void setCredit_rank(String credit_rank) {
		this.credit_rank = credit_rank;
	}

	@Override
	public String toString() {
		return "UserInfo [userid=" + userid + ", username=" + username
				+ ", nickname=" + nickname + ", regdate=" + regdate
				+ ", lastdate=" + lastdate + ", birthday=" + birthday
				+ ", sex=" + sex + ", areaid=" + areaid + ", fjy_quyu="
				+ fjy_quyu + ", fjy_banshichu=" + fjy_banshichu
				+ ", checkin_month_total=" + checkin_month_total
				+ ", checkin_lasted_time=" + checkin_lasted_time
				+ ", daka_lasted_work=" + daka_lasted_work
				+ ", daka_lasted_workoff=" + daka_lasted_workoff + ", address="
				+ address + ", groupid=" + groupid + ", groupname=" + groupname
				+ ", credit=" + credit + ", islock=" + islock + ", connectid="
				+ connectid + ", credit_rank=" + credit_rank + "]";
	}

//	public int getAuto() {
//		return auto;
//	}
//
//	public void setAuto(int auto) {
//		this.auto = auto;
//	}
//
//	public String getPsd() {
//		return psd;
//	}
//
//	public void setPsd(String psd) {
//		this.psd = psd;
//	}
//
//	public int getRememberPsd() {
//		return rememberPsd;
//	}
//
//	public void setRememberPsd(int rememberPsd) {
//		this.rememberPsd = rememberPsd;
//	}
	
	

}
