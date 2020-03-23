package com.lly.entity;

import java.io.Serializable;

public class Archive implements Serializable {

	private Integer id;
	private Integer user_id;
	private String name;
	private String sex;
	private String from;
	private String birthday;
	private Integer age;
	private String faith;
	private String native1;
	private String degree;
	private Integer postal_code;
	private String phone;
	private String email;
	private String address;
	private Double high;
	private Double weight;
	private Integer id_number;
	private String marital;
	private Integer chronic_disease;
	private Integer children;
	private Integer vaccine;
	private Integer doctor;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getFaith() {
		return faith;
	}
	public void setFaith(String faith) {
		this.faith = faith;
	}
	public String getNative1() {
		return native1;
	}
	public void setNative1(String native1) {
		this.native1 = native1;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Integer getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(Integer postal_code) {
		this.postal_code = postal_code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getId_number() {
		return id_number;
	}
	public void setId_number(Integer id_number) {
		this.id_number = id_number;
	}
	public String getMarital() {
		return marital;
	}
	public void setMarital(String marital) {
		this.marital = marital;
	}
	public Integer getChronic_disease() {
		return chronic_disease;
	}
	public void setChronic_disease(Integer chronic_disease) {
		this.chronic_disease = chronic_disease;
	}
	public Integer getChildren() {
		return children;
	}
	public void setChildren(Integer children) {
		this.children = children;
	}
	public Integer getVaccine() {
		return vaccine;
	}
	public void setVaccine(Integer vaccine) {
		this.vaccine = vaccine;
	}
	public Integer getDoctor() {
		return doctor;
	}
	public void setDoctor(Integer doctor) {
		this.doctor = doctor;
	}
	public Archive(Integer id, Integer user_id, String name, String sex, String from, String birthday, Integer age,
			String faith, String native1, String degree, Integer postal_code, String phone, String email,
			String address, Double high, Double weight, Integer id_number, String marital, Integer chronic_disease,
			Integer children, Integer vaccine, Integer doctor) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.name = name;
		this.sex = sex;
		this.from = from;
		this.birthday = birthday;
		this.age = age;
		this.faith = faith;
		this.native1 = native1;
		this.degree = degree;
		this.postal_code = postal_code;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.high = high;
		this.weight = weight;
		this.id_number = id_number;
		this.marital = marital;
		this.chronic_disease = chronic_disease;
		this.children = children;
		this.vaccine = vaccine;
		this.doctor = doctor;
	}
	public Archive() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Archive [id=" + id + ", user_id=" + user_id + ", name=" + name + ", sex=" + sex + ", from=" + from
				+ ", birthday=" + birthday + ", age=" + age + ", faith=" + faith + ", native1=" + native1 + ", degree="
				+ degree + ", postal_code=" + postal_code + ", phone=" + phone + ", email=" + email + ", address="
				+ address + ", high=" + high + ", weight=" + weight + ", id_number=" + id_number + ", marital="
				+ marital + ", chronic_disease=" + chronic_disease + ", children=" + children + ", vaccine=" + vaccine
				+ ", doctor=" + doctor + "]";
	}
	
}
