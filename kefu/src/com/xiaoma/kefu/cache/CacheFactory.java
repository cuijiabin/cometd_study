package com.xiaoma.kefu.cache;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.service.FunctionService;
import com.xiaoma.kefu.service.ServiceIconService;
import com.xiaoma.kefu.service.WaitListService;
import com.xiaoma.kefu.util.StringHelper;
import com.xiaoma.kefu.util.SysConst.DeviceType;


public class CacheFactory {
	public static Object factory(String cacheName, Object key) throws Exception {
		Object obj = null;
		if (StringHelper.isNotEmpty(cacheName)) {
			if(cacheName.equals(CacheName.FUNCTION)){
				//查询单条功能
				FunctionService functionService = (FunctionService) SpringContextUtil
						.getBean("functionService");
				obj=functionService.findById((Integer)key);
				
			}else if(cacheName.equals(CacheName.USERFUNCTION)){
				FunctionService functionService = (FunctionService) SpringContextUtil
						.getBean("functionService");
				obj=functionService.userFuncs((Integer)key);
			}else if(cacheName.equals(CacheName.SYSFUNCTIONONE)){
				FunctionService functionService = (FunctionService) SpringContextUtil
						.getBean("functionService");
				obj=functionService.findFuncOne();
			}else if(cacheName.equals(CacheName.FUNCTIONTREEBYID)){
				FunctionService functionService = (FunctionService) SpringContextUtil
						.getBean("functionService");
				obj=functionService.findTree((Integer)key);
			}else if(cacheName.equals(CacheName.STYLEWAITLIST)){
				WaitListService waitListService = (WaitListService) SpringContextUtil
						.getBean("waitListService");
				obj=waitListService.findListById((String)key);
			}else if(cacheName.equals(CacheName.DIVICONPC)){
				ServiceIconService serviceIconService = (ServiceIconService)SpringContextUtil
						.getBean("serviceIconService");
				obj=serviceIconService.getDivByStyleId(Integer.valueOf((String) key), DeviceType.PC);
			}
		}
		return obj;
	}
}
