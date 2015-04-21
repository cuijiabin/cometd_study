package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Keyboard;

public interface KeyboardDao extends BaseDao<Keyboard> {

	List<Keyboard> findByUesrId(Integer id);

	void deleteByUserId(Integer id);

}
