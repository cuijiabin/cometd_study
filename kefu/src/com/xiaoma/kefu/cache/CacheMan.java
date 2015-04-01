/*
 * Created on 2006-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiaoma.kefu.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Administrator TODO To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class CacheMan {
	private static Log log = LogFactory.getLog(CacheMan.class);
	
	public static void addCache(String cacheName,Object object) {
		MapCache.getInstance().getMapDict().put(cacheName, object);
	}
	public static void updateCache(String cacheName,Object object) {
		MapCache.getInstance().getMapDict().put(cacheName, object);
	}
	public static void removeCache(String cacheName) {
		MapCache.getInstance().getMapDict().remove(cacheName);
	}
	public static Object getCache(String cacheName) {
		return MapCache.getInstance().getMapDict().get(cacheName); 
	}
}
