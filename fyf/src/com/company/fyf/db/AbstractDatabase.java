package com.company.fyf.db;

import com.company.fyf.utils.FinalUtils;

import net.tsz.afinal.FinalDb;

public class AbstractDatabase {
	
	protected FinalDb db ;
	
	protected AbstractDatabase() {
		db = FinalUtils.getDb() ;
	}

}
