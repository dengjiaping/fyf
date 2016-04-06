package com.company.fyf.dao;

public class AreaVo {
	
	private String areaId ;
	private String name ;
	
	public AreaVo() {
	}
	
	public AreaVo(String areaId, String name) {
		this.areaId = areaId;
		this.name = name;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	

}
