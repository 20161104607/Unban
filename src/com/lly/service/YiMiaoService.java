package com.lly.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lly.entity.Child_YiMIao;
import com.lly.entity.YiMiao;

public interface YiMiaoService {

	List<YiMiao> findAll();
	
	List<Child_YiMIao> findByzhonglei(String zhonglei,Integer doctor);

	void updateInfo(Integer id, String str);

	void insertYi(Child_YiMIao yiMIao);
}
