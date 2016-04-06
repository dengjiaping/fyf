package com.company.fyf.db;

import java.util.List;

import com.company.fyf.model.MemberSetting;

public class MemberSettingDao extends AbstractDatabase {
	
	public static MemberSettingDao INSTANCE = new MemberSettingDao();

	private MemberSettingDao() {
	}
	
	public void update(MemberSetting setting){
		db.deleteAll(MemberSetting.class) ;
		db.save(setting) ;
	}
	
	public String getRegprotocol(){
		List<MemberSetting> list = db.findAll(MemberSetting.class) ;
		if(list == null || list.size() == 0){
			return "" ;
		}
		MemberSetting setting = list.get(0) ;
		if(setting == null){
			return "" ;
		}
		
		return setting.getRegprotocol() ;
	}
	
	public String getScanCredits(){
		List<MemberSetting> list = db.findAll(MemberSetting.class) ;
		if(list == null || list.size() == 0){
			return "" ;
		}
		MemberSetting setting = list.get(0) ;
		if(setting == null){
			return "" ;
		}
		
		return setting.getScanCredits() ;
	}

}
