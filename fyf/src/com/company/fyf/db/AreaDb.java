package com.company.fyf.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.text.TextUtils;

import com.company.fyf.dao.AreaVo;
import com.company.fyf.model.Area;
import com.company.fyf.notify.KeyList;
import com.company.fyf.notify.NotifyCenter;
import com.company.fyf.utils.Logger;

public class AreaDb extends AbstractDatabase {
	
	public static AreaDb INSTANCE = new AreaDb() ;
	
	private AreaDb() {
	}
	
	public void update(HashMap<String, String> map){
		clear() ;
		
		Set<Entry<String, String>> set= map.entrySet() ;
		Iterator<Entry<String, String>> ite = set.iterator() ;
		while(ite.hasNext()){
			Entry<String, String> entry = ite.next() ;
			String areaId = entry.getKey() ;
			Area area = new Area() ;
			if(areaId.contains("@")){
				String pathId = areaId.substring(0, areaId.lastIndexOf("@")) ;
				String id = areaId.substring(areaId.lastIndexOf("@") + 1) ;
				area.setAreaid(id) ;
				area.setAreaPath(pathId) ;
			}else{
				area.setAreaid(areaId) ;
			}
			area.setName(entry.getValue()) ;
			db.save(area) ;
		}
		NotifyCenter.sendEmptyMsg(KeyList.KEY_AREA_LIST_UPDATE) ;
	}
	
	private void clear(){
		db.deleteAll(Area.class) ;
	}
	
	//二级办事处
	public List<AreaVo> getAgencyList(String regionId ){
		Logger.d("AreaDb", "[getAgencyList] regionId = " + regionId) ;
		if(TextUtils.isEmpty(regionId)){
			return null ;
		}
		
		List<Area> list = db.findAllByWhere(Area.class, " areaPath IS NOT NULL ") ;
		List<AreaVo> vos = new ArrayList<>() ;
		AreaVo vo = null ;
		for (Area area : list) {
			String areaPath = area.getAreaPath();
			String areaName = "" ;
			String ss[] = areaPath.split("@") ;
			if(!regionId.equals(ss[0])){
				continue ;
			}
			for (int i = 0; i < ss.length; i++) {
				if(1 == ss.length) continue ;
				
				String s = ss[i] ;
				List<Area> _list = db.findAllByWhere(Area.class, " areaid = '"+s+"' ") ;
				if(_list.size() == 0){
					continue ;
				}
				areaName += _list.get(0).getName() ;
			}
			areaName += area.getName() ;
			vo = new AreaVo(area.getAreaid(), areaName) ;
			vos.add(vo) ;
		}
		return vos ;
	}
	
	//一级区域
	public List<AreaVo> getRegionList(){
		List<Area> list = db.findAllByWhere(Area.class, " areaPath IS NULL ") ;
		List<AreaVo> vos = new ArrayList<>() ;
		AreaVo vo = null ;
		for (Area area : list) {
			vo = new AreaVo(area.getAreaid(), area.getName()) ;
			vos.add(vo) ;
		}
		return vos ;
	}
	
	public String rootAreaIdById(String areaId){
		List<Area> list = db.findAllByWhere(Area.class, " areaId = '"+areaId+"' ") ;
		if(list == null || list.size() == 0){
			return "";
		}
		Area a = list.get(0) ;
		if(TextUtils.isEmpty(a.getAreaPath())){
			return areaId ;
		}
		return a.getAreaPath().split("@")[0] ;
	}

}
