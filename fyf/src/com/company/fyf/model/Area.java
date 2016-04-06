package com.company.fyf.model;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "Area")
public class Area {
	
	@Id
	private String areaid ;
	private String name ;
	
	private String areaPath ;

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaPath() {
		return areaPath;
	}

	public void setAreaPath(String areaPath) {
		this.areaPath = areaPath;
	}
	
	
	
	

}
