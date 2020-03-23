package com.lly.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lly.entity.User;


public interface UserService {
	
	//验证身份登录
	Integer login_role(String username,String password);

	User login_user(String username,String password);
	
	void updatePsword(String password,Integer id);

	void addUser(User u);

	void updatelltime(String listtime,Integer id);

	List<User> findOrder();
	
}
