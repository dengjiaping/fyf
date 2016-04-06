package com.company.fyf.db;

import java.util.List;

import com.company.fyf.model.User;

public class UserDb extends AbstractDatabase {

	public static UserDb INSTANCE = new UserDb();

	private UserDb() {
	}

	public void update(User user) {
		List<User> list = db.findAll(User.class);

		boolean has = false;

		if (list == null || list.size() == 0) {
			has = false;
		} else {
			for (User u : list) {
				if (u.getUsername().equals(user.getUsername())) {
					has = true;
					break;
				}
			}
		}

		if (has) {
			db.update(user);
		} else {
			db.save(user);
		}
	}
	
	public User getVisiableLast(){
		String orderBy = " localLoginTime desc " ;
		String strWhere = " rememberName = '1' " ;
		List<User> list = db.findAllByWhere(User.class, strWhere, orderBy);
		if(list == null || list.size() == 0){
			 return null;
		}
		return list.get(0) ;
	}
}
