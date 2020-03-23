package com.lly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lly.dao.ArchiveDao;
import com.lly.entity.Archive;
@Service
@Transactional
public class ArchiveServiceImpl implements ArchiveService{

	@Autowired
	private ArchiveDao archivedao;
	
	@Override
	public Archive findByUserId(Integer user_id) {
		Archive archive = this.archivedao.findByUserId(user_id);
		return archive;
	}

	@Override
	public void updateInfo(Archive archive) {
		this.archivedao.updateInfo(archive);
	}

	@Override
	public void insertArchive(Archive archive) {
		this.archivedao.intsetArchives(archive);
	}

	@Override
	public List<Archive> findByDoctor(Integer doctor_id) {
		List<Archive> findByDoctor = this.archivedao.findByDoctor(doctor_id);
		return findByDoctor;
	}

	@Override
	public Archive findByID(Integer id) {
		Archive achive = this.archivedao.findByID(id);
		return achive;
	}

	@Override
	public void deleteInfo(Integer id) {
		this.archivedao.deleteInfo(id);
	}

	@Override
	public List<Archive> findByChronic1(Integer doctor) {
		List<Archive> byChronic1 = this.archivedao.findByChronic1(doctor);
		return byChronic1;
	}

	@Override
	public List<Archive> findByChronic2(Integer doctor) {
		List<Archive> byChronic2 = this.archivedao.findByChronic2(doctor);
		return byChronic2;
	}

	@Override
	public List<Archive> findByChronic3(Integer doctor) {
		List<Archive> byChronic3 = this.archivedao.findByChronic3(doctor);
		return byChronic3;
	}

}
