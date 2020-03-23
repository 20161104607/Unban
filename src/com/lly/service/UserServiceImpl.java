package com.lly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lly.dao.UserDao;
import com.lly.entity.User;
@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	@Override
	public Integer login_role(String username, String password) {
		Integer role = this.dao.login(username, password);
		return role;
	}
	public User login_user(String username, String password) {
		User role = this.dao.login_user(username, password);
		return role;
	}
	@Override
	public void updatePsword(String password,Integer id) {
		this.dao.updatePassword(password,id);
	}
	@Override
	public void addUser(User u) {
		this.dao.addUser(u);
	}
	@Override
	public void updatelltime(String listtime,Integer id) {
		this.dao.updateLasttime(listtime,id);
	}
	@Override
	public List<User> findOrder() {
		List<User> oreder = this.dao.findOreder();
		return oreder;
	}
	
}
