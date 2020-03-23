package com.lly.entity;

public class User {
	private Integer id;
	private String username;
	private String password;
	private Integer role;
	private String lasttime;
	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User(Integer id, String username, String password, Integer role, String lasttime, String address) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.lasttime = lasttime;
		this.address = address;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", lasttime="
				+ lasttime + ", address=" + address + "]";
	}

}
