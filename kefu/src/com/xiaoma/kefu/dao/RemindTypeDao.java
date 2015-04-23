package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.RemindType;

public interface RemindTypeDao extends BaseDao<RemindType> {

	List<RemindType> findRemindByUesrId(Integer id);

	void deleteRemindByUserId(Integer id);

}
