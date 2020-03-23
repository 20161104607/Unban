package com.lly.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lly.entity.Archive;
import com.lly.entity.Doctor;

public interface DoctorDao {
	//通过用户id查找医生
	@Select("select * from doctor where user_id = #{user_id}")
	Doctor findByUserId(Integer user_id);
	
	@Select("select id from doctor where user_id = #{user_id}")
	Integer findDocID(@Param("user_id")Integer user_id);

	@Select("select * from doctor")
	List<Doctor> findAllDoc();

	@Insert("insert into doctor values(#{id},#{user_id},#{name},#{phone},#{address},#{id_number},#{age})")
	void addDoctor(Doctor doctor);
}
