package com.lly.service;

import java.util.List;

import com.lly.entity.Doctor;

public interface DoctorService {

	Doctor findByUserId(Integer id);

	Integer findDocId(Integer id);

	List<Doctor> findAllDoctor();

	void addDoctor(Doctor doctor);
}
