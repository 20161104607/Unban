package com.lly.entity;

public class Doctor {
	private Integer id;
	private Integer user_id;
	private String name;
	private Integer phone;
	private String address;
	private String id_number;
	private Integer age;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Doctor(Integer id, Integer user_id, String name, Integer phone, String address, String id_number,
			Integer age) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.id_number = id_number;
		this.age = age;
	}
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Doctor [id=" + id + ", user_id=" + user_id + ", name=" + name + ", phone=" + phone + ", address="
				+ address + ", id_number=" + id_number + ", age=" + age + "]";
	}

	
}
