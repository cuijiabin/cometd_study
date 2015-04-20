package com.xiaoma.kefu.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.cache.CacheFactory;
import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dao.FunctionDao;
import com.xiaoma.kefu.dao.RoleDeptDao;
import com.xiaoma.kefu.dao.RoleDeptFuncDao;
import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.Function;
import com.xiaoma.kefu.model.RoleDept;
import com.xiaoma.kefu.model.RoleDeptFunc;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.StringHelper;

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
	@Autowired
	private UserDao userDao;

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
	 * 保存权限
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
	
    public Object userFuncs(Integer id){
    	User user = userDao.findById(User.class, id);
    	RoleDept roleDept = roleDeptDao.findRoleDeptBy(user.getRoleId(), user.getDeptId());
		List<Function> list= funcDao.getUserFuc(roleDept.getId());
		String userFunc="";
		for (Function func : list) {
			userFunc+=","+func.getCode()+",";
		}
		return userFunc;
    }

	public List checkFuncOne(List<Function> list, String codes) {
		List listFunc = new ArrayList();
		if(list == null){
			return listFunc;
		}
		for (Function func : list) {
			int count = codes.indexOf(","+func.getCode()+",");
			if(count>=0){
				listFunc.add(func);
			}
		}
		return listFunc;
	}
    
//	public boolean checkedFunc(Integer id, String code) {
//		boolean flag=false;
//		String codes = (String)CacheMan.getObject(CacheName.USERFUNCTION, id);
//		if(StringHelper.isEmpty(codes)){
//			CacheFactory.factory(CacheName.USERFUNCTION, id);
//		
//			for (Function func : list) {
//				if(code.equals(func.getCode())){
//					flag=true;
//				}
//			}
//		}else{
//			int count = codes.indexOf(code);
//			if(count>=0){
//				return true;
//			}
//		}
//		return flag;
//	}
}
