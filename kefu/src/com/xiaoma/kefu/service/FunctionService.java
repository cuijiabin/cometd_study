package com.xiaoma.kefu.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.FunctionDao;
import com.xiaoma.kefu.dao.RoleDeptDao;
import com.xiaoma.kefu.dao.RoleDeptFuncDao;
import com.xiaoma.kefu.model.Function;
import com.xiaoma.kefu.model.RoleDept;
import com.xiaoma.kefu.model.RoleDeptFunc;

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
	@Autowired
	private RoleDeptDao roleDeptDao;
	@Autowired
	private RoleDeptFuncDao roleDeptFuncDao;

	public List findFuncOne() {

		return funcDao.findBylevel(1);
	}

	public List findTree(int tid) {

		return funcDao.findTree(tid);
	}

	public List findFunction() {

		return funcDao.findAll();
	}

	public Function findById(Integer id) {
		return funcDao.findById(Function.class, id);
	}

	/**
	 * 找到唯一的部门角色对应的id
	 * 
	 * @param roleId
	 * @param deptId
	 * @param ids
	 * @return
	 */
	public Integer saveFunc(Integer roleId, Integer deptId, String ids) {
		RoleDept roleDept = roleDeptDao.findRoleDeptBy(roleId, deptId);
		Integer success = roleDeptDao.deleteRD(roleDept.getId());
		if (success == 1) {
			if (roleDept != null) {
				int isSuccess = 0;
				String[] rdids = ids.split(",");
				for (String str : rdids) {
					RoleDeptFunc rdf = new RoleDeptFunc();
					rdf.setFuncId(Integer.parseInt(str));
					rdf.setRoleDeptId(roleDept.getId());
					isSuccess = (Integer) roleDeptFuncDao.add(rdf);
				}
				return isSuccess;
			}
		}
		return 0;
	}

	public String checkedFunc(Integer roleId, Integer deptId) {
		RoleDept roleDept = roleDeptDao.findRoleDeptBy(roleId, deptId);
		if (roleDept != null) {
			List<RoleDeptFunc> list = roleDeptFuncDao.getFuncByRD(roleDept
					.getId());
			if (list.isEmpty()) {
				return 0 + ",";
			} else {
				String str = "";
				for (RoleDeptFunc func : list) {
					str += func.getFuncId() + ",";
				}
				return str;
			}
		}
		return 0 + ",";
	}
}
