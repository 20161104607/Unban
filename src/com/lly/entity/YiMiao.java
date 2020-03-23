package com.lly.entity;

import java.io.Serializable;

public class YiMiao implements Serializable{
	private Integer id;
	private String name;
	private String gongxiao;
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
	public String getGongxiao() {
		return gongxiao;
	}
	public void setGongxiao(String gongxiao) {
		this.gongxiao = gongxiao;
	}
	public YiMiao(Integer id, String name, String gongxiao) {
		super();
		this.id = id;
		this.name = name;
		this.gongxiao = gongxiao;
	}
	public YiMiao() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "YiMiao [id=" + id + ", name=" + name + ", gongxiao=" + gongxiao + "]";
	}
	
	
}
