package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.FunctionDao;

/**
 * 
 * @author yangxiaofeng
 * @time 2015年4月3日上午 10:24:18
 *
 */
@Service
public class FunctionService {
	@Autowired
	private FunctionDao funcDao;

	public List findFuncOne() {
		
		return funcDao.findBylevel(1);
	}

	public List findTree(int tid) {
		
		return funcDao.findTree(tid);
	}

	public List findFunction() {
		
		return funcDao.findAll();
	}

}
