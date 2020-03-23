package com.lly.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lly.entity.Archive;

public interface ArchiveDao {
	@Select("select * from archives where user_id = #{user_id}")
	Archive findByUserId(@Param("user_id")Integer user_id);

	@Update("update archives set id = #{id}")
	void updateInfo(Archive archive);
	
	@Select("select * from archives where doctor = #{doctor}")
	List<Archive> findByDoctor(Integer doctor);
	
	@Insert("insert into archives values(#{id},#{user_id},#{name},#{sex},#{from},#{birthday},#{age},#{faith},#{native1},#{degree},#{postal_code},#{phone},#{email},#{address},#{high},#{weight},#{id_number},#{marital},#{chronic_disease},#{children},#{vaccine},#{doctor})")
	void intsetArchives(Archive archive);

	@Select("select * from archives where id=#{id}")
	Archive findByID(@Param("id")Integer id);

	@Delete("delete from archives where id = #{id}")
	void deleteInfo(@Param("id")Integer id);
	
	//查找慢性病1
	@Select("select * from archives where chronic_disease = 1 and doctor = #{doctor}")
	List<Archive> findByChronic1(Integer doctor);
	
	//查找慢性病2
	@Select("select * from archives where chronic_disease = 2 and doctor = #{doctor}")
	List<Archive> findByChronic2(Integer doctor);

	//查找慢性病3
	@Select("select * from archives where chronic_disease = 3 and doctor = #{doctor}")
	List<Archive> findByChronic3(Integer doctor);
	
}
