package com.poc.application.poc1.service;

import com.poc.application.poc1.dao.UserDao;
import com.poc.application.poc1.entity.User;

public class UserService {
     
    UserDao userDao = new UserDao();
	
	public void addUser(User user) {
		userDao.addUser(user);
		System.out.println("============");
		return ;
	}
	
	
	

}
