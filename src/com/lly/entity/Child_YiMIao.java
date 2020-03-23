package com.lly.entity;

import java.io.Serializable;

public class Child_YiMIao implements Serializable{
	private Integer id;
	private Integer child_id;
	private Integer doctor;
	private Integer niudou;
	private Integer kajiemiao;
	private Integer yigan;
	private Integer xiaoermabi;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getChild_id() {
		return child_id;
	}
	public void setChild_id(Integer child_id) {
		this.child_id = child_id;
	}
	public Integer getDoctor() {
		return doctor;
	}
	public void setDoctor(Integer doctor) {
		this.doctor = doctor;
	}
	public Integer getNiudou() {
		return niudou;
	}
	public void setNiudou(Integer niudou) {
		this.niudou = niudou;
	}
	public Integer getKajiemiao() {
		return kajiemiao;
	}
	public void setKajiemiao(Integer kajiemiao) {
		this.kajiemiao = kajiemiao;
	}
	public Integer getYigan() {
		return yigan;
	}
	public void setYigan(Integer yigan) {
		this.yigan = yigan;
	}
	public Integer getXiaoermabi() {
		return xiaoermabi;
	}
	public void setXiaoermabi(Integer xiaoermabi) {
		this.xiaoermabi = xiaoermabi;
	}
	public Child_YiMIao(Integer id, Integer child_id, Integer doctor, Integer niudou, Integer kajiemiao, Integer yigan,
			Integer xiaoermabi) {
		super();
		this.id = id;
		this.child_id = child_id;
		this.doctor = doctor;
		this.niudou = niudou;
		this.kajiemiao = kajiemiao;
		this.yigan = yigan;
		this.xiaoermabi = xiaoermabi;
	}
	public Child_YiMIao() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Child_YiMIao [id=" + id + ", child_id=" + child_id + ", doctor=" + doctor + ", niudou=" + niudou
				+ ", kajiemiao=" + kajiemiao + ", yigan=" + yigan + ", xiaoermabi=" + xiaoermabi + "]";
	}
	
}
