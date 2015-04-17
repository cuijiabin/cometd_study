package com.xiaoma.kefu.dao;

import com.xiaoma.kefu.model.RoleDept;

public interface RoleDeptDao extends BaseDao<RoleDept> {

	RoleDept findRoleDeptBy(Integer roleId, Integer deptId);

	Integer deleteRD(Integer id);

}
