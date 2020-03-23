package com.lly.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lly.dao.ArchiveDao;
import com.lly.dao.ChildDao;
import com.lly.dao.DoctorDao;
import com.lly.dao.UserDao;
import com.lly.entity.Child;
import com.lly.entity.Child_YiMIao;
import com.lly.entity.Doctor;
import com.lly.entity.child_parents;

@Service
@Transactional
public class ChildServiceImpl implements ChildService{

	@Autowired
	private ChildDao childdao;
	@Autowired
	private UserDao userdao;
	@Autowired
	private ArchiveDao archiverDao;
	
	@Override
	public List<Child> findByDoctor(Integer doctor) {
		List<Child> list = this.childdao.findByDoctor(doctor);
		List<Child> list2 = new ArrayList<Child>();
		for (int i = 0; i < list.size(); i++) {
			Child child = new Child();
			child_parents child_parents = this.childdao.findParents(list.get(i).getId());
			child.setFather_name(this.archiverDao.findByID(child_parents.getFather_id()).getName());
			child.setMother_name(this.archiverDao.findByID(child_parents.getMother_id()).getName());
			child.setFather_phone(this.archiverDao.findByID(child_parents.getFather_id()).getPhone());
			child.setMother_phone(this.archiverDao.findByID(child_parents.getMother_id()).getPhone());
			child.setName(list.get(i).getName());
			child.setSex(list.get(i).getSex());
			child.setAge(list.get(i).getAge());
			list2.add(child);
		}
		return list2;
	}

	@Override
	public void insertChild(Child child) {
		this.childdao.insertChild(child);
	}

	@Override
	public void insertGuanlian(Child child) {
		List<Child> allDesc = this.childdao.findAllDesc();
		child_parents child_parents = new child_parents(0, allDesc.get(0).getId(), child.getFather_id(), child.getMother_id());
		this.childdao.insertGuanlian(child_parents);
	}

	@Override
	public Child findById(Integer id) {
		Child child = this.childdao.findByid(id);
		child_parents child_parents = this.childdao.findParents(id);
		child.setFather_name(this.archiverDao.findByID(child_parents.getFather_id()).getName());
		child.setMother_name(this.archiverDao.findByID(child_parents.getMother_id()).getName());
		child.setFather_phone(this.archiverDao.findByID(child_parents.getFather_id()).getPhone());
		child.setMother_phone(this.archiverDao.findByID(child_parents.getMother_id()).getPhone());
		return child;
	}

	@Override
	public Integer countInfo(Integer doctor) {
		Integer integer = this.childdao.countInfo(doctor);
		return integer;
	}

}
