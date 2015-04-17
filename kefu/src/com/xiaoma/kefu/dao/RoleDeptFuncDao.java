package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.RoleDeptFunc;

public interface RoleDeptFuncDao extends BaseDao<RoleDeptFunc> {

	List<RoleDeptFunc> getFuncByRD(Integer id);

}
