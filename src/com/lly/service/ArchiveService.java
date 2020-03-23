package com.lly.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.lly.entity.Archive;

public interface ArchiveService {
	//通过用户id查找档案信息
	Archive findByUserId(Integer user_id);

	void updateInfo(Archive archive);
	
	void insertArchive(Archive archive);
	
	List<Archive> findByDoctor(Integer doctor_id);

	Archive findByID(Integer id);

	void deleteInfo(Integer id);
	
	List<Archive> findByChronic1(Integer doctor);
	
	List<Archive> findByChronic2(Integer doctor);

	List<Archive> findByChronic3(Integer doctor);
}
