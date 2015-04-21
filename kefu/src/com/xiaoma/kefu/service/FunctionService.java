package com.xiaoma.kefu.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.cache.CacheFactory;
import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.controller.FunctionController;
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
	private Logger logger = Logger.getLogger(FunctionService.class);
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
    /**
     * 查询出所拥有的权限串
     * @param roleId
     * @param deptId
     * @return
     */
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
	/**
	 * 将所有用的权限拼接成一个字符串
	 * @param id
	 * @return
	 */
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
    
    /**
     * 查询头部信息权限对比返回
     * @param list
     * @param codes
     * @return
     */
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
    
	/**
	 * 根据用户id和code值来检验改用户的页面是否有某个权限
	 * @param id
	 * @param code
	 * @return
	 */
	public boolean isCheckFunc(Integer id, String code) {
		try{
		String codes = (String)CacheMan.getObject(CacheName.USERFUNCTION, id);
			int count = codes.indexOf(","+code+",");
			if(count>=0){
				return true;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		 return false;
	}
	

}
