package com.lly.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lly.dao.ChildDao;
import com.lly.dao.DoctorDao;
import com.lly.dao.YIMiaoDao;
import com.lly.entity.Child;
import com.lly.entity.Child_YiMIao;
import com.lly.entity.Doctor;
import com.lly.entity.YiMiao;

@Service
@Transactional
public class YiMiaoServiceImpl implements YiMiaoService{

	@Autowired
	private YIMiaoDao dao;
	@Autowired
	private ChildDao childdao;
	
	@Override
	public List<YiMiao> findAll() {
		List<YiMiao> list = this.dao.findAll();
		return list;
	}

	@Override
	public List<Child_YiMIao> findByzhonglei(String zhonglei,Integer doctor) {
		List<Child_YiMIao> findByzhonglei = new ArrayList<Child_YiMIao>();
		switch (zhonglei) {
		case "niudou":
			findByzhonglei = this.dao.findByzhonglei_niudou(doctor);
			break;
		case "kajiemiao":
			findByzhonglei = this.dao.findByzhonglei_kajiemiao(doctor);
			break;
		case "yigan":
			findByzhonglei = this.dao.findByzhonglei_yigan(doctor);
			break;
		case "xiaoermabi":
			findByzhonglei = this.dao.findByzhonglei_xiaoermabi(doctor);
			break;
		default:
			break;
		};
		return findByzhonglei;
	}

	@Override
	public void updateInfo(Integer id, String str) {
		switch (str) {
		case "niudou":
			this.dao.updateInfo_niudou(id);
			break;
		case "kajiemiao":
			this.dao.updateInfo_kajiemiao(id);
			break;
		case "yigan":
			this.dao.updateInfo_yigan(id);
			break;
		case "xiaoermabi":
			this.dao.updateInfo_xiaoermabi(id);
			break;
		default:
			break;
		};
		this.dao.updateInfo_niudou(id);
	}

	@Override
	public void insertYi(Child_YiMIao yiMIao) {
		List<Child> allDesc = this.childdao.findAllDesc();
		yiMIao.setChild_id(allDesc.get(0).getId());
		this.dao.insertYiMiao(yiMIao);
	}

}
