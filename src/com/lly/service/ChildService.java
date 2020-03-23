package com.lly.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lly.entity.Child;

public interface ChildService {

	List<Child> findByDoctor(@Param("doctor")Integer doctor);

	void insertChild(Child child);

	void insertGuanlian(Child child);

	Child findById(Integer id);

	Integer countInfo(Integer doctor);


}
