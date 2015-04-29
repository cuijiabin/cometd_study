package com.xiaoma.kefu.util;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;

@SuppressWarnings("serial")
public class CheckCodeUtil extends HttpServlet{
	private static Logger logger = Logger.getLogger(CheckCodeUtil.class);

	/**
	 * 根据用户id和code值来检验改用户的页面是否有某个权限
	 * @param id
	 * @param code
	 * @return
	 */
	public static boolean isCheckFunc(Integer id ,String code) {
		try{
			
		    String codes = (String)CacheMan.getObject(CacheName.USERFUNCTION, id,String.class);
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