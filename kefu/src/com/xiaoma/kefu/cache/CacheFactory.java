package com.xiaoma.kefu.cache;

import com.xiaoma.kefu.common.SpringContextUtil;
import com.xiaoma.kefu.service.BusiGroupDetailService;
import com.xiaoma.kefu.service.FunctionService;
import com.xiaoma.kefu.service.InviteIconService;
import com.xiaoma.kefu.service.ServiceIconService;
import com.xiaoma.kefu.service.UserService;
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
			}else if(cacheName.equals(CacheName.DIVICONPCON)){
				ServiceIconService serviceIconService = (ServiceIconService)SpringContextUtil
						.getBean("serviceIconService");
				obj=serviceIconService.getDivOnline(Integer.valueOf((String) key), DeviceType.PC);
			}else if(cacheName.equals(CacheName.DIVICONPCOFF)){
				ServiceIconService serviceIconService = (ServiceIconService)SpringContextUtil
						.getBean("serviceIconService");
				obj=serviceIconService.getDivOffline(Integer.valueOf((String) key), DeviceType.PC);
			}else if(cacheName.equals(CacheName.DIVICONYDON)){
				ServiceIconService serviceIconService = (ServiceIconService)SpringContextUtil
						.getBean("serviceIconService");
				obj=serviceIconService.getDivOnline(Integer.valueOf((String) key), DeviceType.移动);
			}else if(cacheName.equals(CacheName.DIVICONYDOFF)){
				ServiceIconService serviceIconService = (ServiceIconService)SpringContextUtil
						.getBean("serviceIconService");
				obj=serviceIconService.getDivOffline(Integer.valueOf((String) key), DeviceType.移动);
			}else if(cacheName.equals(CacheName.DIVINVITEPC)){
				InviteIconService inviteIconService = (InviteIconService)SpringContextUtil
						.getBean("inviteIconService");
				obj=inviteIconService.getDivByStyleId(Integer.valueOf((String) key), DeviceType.PC);
			}else if(cacheName.equals(CacheName.DIVINVITEYD)){
				InviteIconService inviteIconService = (InviteIconService)SpringContextUtil
						.getBean("inviteIconService");
				obj=inviteIconService.getDivByStyleId(Integer.valueOf((String) key), DeviceType.移动);
			}else if(cacheName.equals(CacheName.ONLINE_USER_STYLEID)){
				BusiGroupDetailService busiGroupDetailService = (BusiGroupDetailService)SpringContextUtil
						.getBean("busiGroupDetailService");
				obj=busiGroupDetailService.findUserIdsByStyleId((Integer) key);
			}else if(cacheName.equals(CacheName.SUSER)){
				UserService userService = (UserService)SpringContextUtil
						.getBean("userService");
				obj=userService.getUserById(Integer.valueOf(String.valueOf(key)));
			}
		}
		return obj;
	}
}
