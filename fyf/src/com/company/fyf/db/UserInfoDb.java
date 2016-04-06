package com.company.fyf.db;

import java.util.List;

import net.tsz.afinal.db.table.TableInfo;

import com.company.fyf.model.UserInfo;
import com.company.fyf.notify.KeyList;
import com.company.fyf.notify.NotifyCenter;
import com.company.fyf.utils.Logger;
import com.lyx.utils.CommUtil;

public class UserInfoDb extends AbstractDatabase  {
	
	public static UserInfoDb INSTANCE = new UserInfoDb() ;
	
	private UserInfoDb() {
	}
	
	private UserInfo user = null ;
	
	public void update(UserInfo user){
		
		dropTable();
		db.save(user) ;
		
		Logger.focus(getClass(), "[--UserInfo update--]") ;
		Logger.focus(getClass(), "[new  UserInfo] = " + user) ;
		if(this.user == null){
			this.user = user;
		}else{
			try {
				CommUtil.objectCopy(user, this.user) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Logger.focus(getClass(), "[after update UserInfo]" + this.user) ;
		NotifyCenter.sendEmptyMsg(KeyList.KEY_USER_INFO_UPDATE) ;
		
	}
	
	public void clear(){
		this.user = null;
		dropTable();
		NotifyCenter.sendEmptyMsg(KeyList.KEY_USER_INFO_UPDATE) ;
	}

	private void dropTable() {
		db.deleteAll(UserInfo.class) ;
	}
	
	public UserInfo get(){
		
		if(user == null){
			List<UserInfo> list = db.findAll(UserInfo.class) ;
			if(list != null && list.size() > 0){
				user = list.get(0) ;
			}
		}
		
		return user ;
	}
	
//	public void update(UserInfo user){
//		List<UserInfo> list = db.findAll(UserInfo.class) ;
//		
//		boolean has = false ;
//		
//		if(list == null || list.size() == 0){
//			 has = false ;
//		}else{
//			for (UserInfo userInfo : list) {
//				if(userInfo.getUserid().equals(user.getUserid())){
//					has = true ;
//					break ;
//				}
//			}
//		}
//		
//		if(has){
//			db.update(user);
//		}else{
//			db.save(user);
//		}
//		
//		NotifyCenter.sendEmptyMsg(KeyList.KEY_USER_INFO_UPDATE) ;
//	}
	
	/*public UserInfo getLast(){
		
		String orderBy = " localLoginTime desc " ;
		List<UserInfo> list = db.findAll(UserInfo.class,orderBy) ;
		if(list == null || list.size() == 0){
			 return null;
		}
		
		return list.get(0) ;
	}
	
	public UserInfo getViableLast(){
		
		UserInfo userInfo = getLast() ;
		
		if(userInfo != null && userInfo.getAuto() == 1) {
			return userInfo ;
		}
		
		return null ;
	}
	
	public void setLastNotAuto(){
		UserInfo userInfo = getLast() ;
		userInfo.setAuto(0) ;
		update(userInfo) ;
	}*/

}
