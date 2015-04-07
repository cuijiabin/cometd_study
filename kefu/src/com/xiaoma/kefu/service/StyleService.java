package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.StyleDao;
import com.xiaoma.kefu.model.WaitList;

/**
 **********************************
* @Description: 风格	业务实现类
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:21:23
**********************************
 */
@Service
public class StyleService {
	
	@Autowired
	private StyleDao styleDaoImpl;

	public List<WaitList> findByNameLike(String styleName) {
		return styleDaoImpl.findByNameLike(styleName);
	}

}