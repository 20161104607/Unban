package com.lly.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.lly.entity.Child;
import com.lly.entity.child_parents;

public interface ChildDao {
	// 根据医生id查找儿童
	@Select("select * from child where doctor = #{doctor}")
	List<Child> findByDoctor(@Param("doctor") Integer doctor);

	// 根据儿童id查找其父母
	@Select("select * from parents_child where child_id = #{child_id}")
	child_parents findParents(@Param("child_id") Integer child_id);

	@Insert("insert into child values(#{id},#{name},#{age},#{doctor},#{sex})")
	void insertChild(Child child);

	@Select("select count(*) from child where doctor = #{doctor}")
	Integer countInfo(Integer doctor);
	
	@Select("SELECT * from child ORDER BY id DESC")
	List<Child> findAllDesc();

	@Insert("insert into parents_child values(#{id},#{child_id},#{father_id},#{mother_id})")
	void insertGuanlian(child_parents child_parents);

	@Select("select * from child where id = #{id}")
	Child findByid(Integer id);
}
