package com.lly.entity;

public class child_parents {
	private Integer id;
	private Integer child_id;
	private Integer father_id;
	private Integer mother_id;
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
	public child_parents(Integer id, Integer child_id, Integer father_id, Integer mother_id) {
		super();
		this.id = id;
		this.child_id = child_id;
		this.father_id = father_id;
		this.mother_id = mother_id;
	}
	public child_parents() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "child_parents [id=" + id + ", child_id=" + child_id + ", father_id=" + father_id + ", mother_id="
				+ mother_id + "]";
	}
	
	
	


}
