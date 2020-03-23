package com.lly.entity;

import java.io.Serializable;

public class Child implements Serializable {
	private Integer id;
	private String name;
	private String age;
	private String sex;
	private Integer doctor;
	private Integer father_id;
	private Integer mother_id;
	private String father_name;
	private String mother_name;
	private String father_phone;
	private String mother_phone;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getDoctor() {
		return doctor;
	}
	public void setDoctor(Integer doctor) {
		this.doctor = doctor;
	}
	public Integer getFather_id() {
		return father_id;
	}
	public void setFather_id(Integer father_id) {
		this.father_id = father_id;
	}
	public Integer getMother_id() {
		return mother_id;
	}
	public void setMother_id(Integer mother_id) {
		this.mother_id = mother_id;
	}
	public String getFather_name() {
		return father_name;
	}
	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}
	public String getMother_name() {
		return mother_name;
	}
	public void setMother_name(String mother_name) {
		this.mother_name = mother_name;
	}
	public String getFather_phone() {
		return father_phone;
	}
	public void setFather_phone(String father_phone) {
		this.father_phone = father_phone;
	}
	public String getMother_phone() {
		return mother_phone;
	}
	public void setMother_phone(String mother_phone) {
		this.mother_phone = mother_phone;
	}
	public Child(Integer id, String name, String age, String sex, Integer doctor, Integer father_id, Integer mother_id,
			String father_name, String mother_name, String father_phone, String mother_phone) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.doctor = doctor;
		this.father_id = father_id;
		this.mother_id = mother_id;
		this.father_name = father_name;
		this.mother_name = mother_name;
		this.father_phone = father_phone;
		this.mother_phone = mother_phone;
	}
	
	
	public Child(Integer id, String name, String age, String sex, Integer doctor, Integer father_id,
			Integer mother_id) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.doctor = doctor;
		this.father_id = father_id;
		this.mother_id = mother_id;
	}
	public Child() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Child [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", doctor=" + doctor
				+ ", father_id=" + father_id + ", mother_id=" + mother_id + ", father_name=" + father_name
				+ ", mother_name=" + mother_name + ", father_phone=" + father_phone + ", mother_phone=" + mother_phone
				+ "]";
	}

}
