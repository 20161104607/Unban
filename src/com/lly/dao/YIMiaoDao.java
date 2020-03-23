package com.lly.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lly.entity.Child_YiMIao;
import com.lly.entity.YiMiao;

public interface YIMiaoDao {
	//查找所有疫苗种类
	@Select("select * from yimiao")
	List<YiMiao> findAll();
	
	//已接种，未接种，总数，百分比，默认查找未接种
	@Select("select * from yimiao_child where niudou = 0 and doctor = #{doctor}")
	List<Child_YiMIao> findByzhonglei_niudou(@Param("doctor")Integer doctor);
	
	@Select("select * from yimiao_child where kajiemiao = 0 and doctor = #{doctor}")
	List<Child_YiMIao> findByzhonglei_kajiemiao(@Param("doctor")Integer doctor);
	
	@Select("select * from yimiao_child where yigan = 0 and doctor = #{doctor}")
	List<Child_YiMIao> findByzhonglei_yigan(@Param("doctor")Integer doctor);
	
	@Select("select * from yimiao_child where xiaoermabi = 0 and doctor = #{doctor}")
	List<Child_YiMIao> findByzhonglei_xiaoermabi(@Param("doctor")Integer doctor);

	@Update("update yimiao_child set niudou = 1 where id=#{id}")
	void updateInfo_niudou(@Param("id")Integer id);
	
	@Update("update yimiao_child set kajiemiao = 1 where id=#{id}")
	void updateInfo_kajiemiao(@Param("id")Integer id);
	
	@Update("update yimiao_child set yigan = 1 where id=#{id}")
	void updateInfo_yigan(@Param("id")Integer id);
	
	@Update("update yimiao_child set xiaoermabi = 1 where id=#{id}")
	void updateInfo_xiaoermabi(@Param("id")Integer id);

	@Insert("insert into yimiao_child values(#{id},#{child_id},#{doctor},#{niudou},#{kajiemiao},#{yigan},#{xiaoermabi})")
	void insertYiMiao(Child_YiMIao yiMIao);
}

