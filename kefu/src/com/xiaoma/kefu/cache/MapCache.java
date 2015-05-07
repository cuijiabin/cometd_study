/*
 * Created on 2006-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiaoma.kefu.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Administrator TODO To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class MapCache {
	private static Log log = LogFactory.getLog(MapCache.class);
	private static Map mapDict = null;
	private static MapCache instance;

	public static synchronized MapCache getInstance() {
		try {
			if (instance == null) {
				mapDict = new HashMap();
				instance = new MapCache();
			} else
				return instance;
		} catch (Exception e) {
			log.error("DictCache::getInstance:" + e.getMessage(),e);
			instance = null;
		}
		return instance;
	}

	/**
	 * @return Returns the mapDict.
	 */
	public Map getMapDict() {
		return mapDict;
	}

	/**
	 * @param mapDict
	 *            The mapDict to set.
	 */
	public void setMapDict(Map mapDict) {
		MapCache.mapDict = mapDict;
	}
}
