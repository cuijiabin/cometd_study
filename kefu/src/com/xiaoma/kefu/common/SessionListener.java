package com.xiaoma.kefu.common;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.User;

public class SessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
//		System.out.println("session created");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		User user = (User) arg0.getSession().getAttribute(CacheName.USER);
		if(user!=null){
			user.setOnLineStatus(2);//设置为离线
			CacheMan.update(CacheName.SUSER, user.getId(), user);//更新缓存中的用户状态
		}
	}
	
}
