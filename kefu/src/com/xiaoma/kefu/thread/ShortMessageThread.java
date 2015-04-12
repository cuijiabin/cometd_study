/*
 * Created on 2005-7-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiaoma.kefu.thread;

import com.xiaoma.kefu.cache.CacheMan;

/**
 * @author Administrator TODO To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class ShortMessageThread extends Thread {
	private String cacheName;
	public ShortMessageThread(String cacheName) {
		this.cacheName = cacheName;
	}
	public void run() {
		CacheMan.remove(cacheName,"");
	}
}
