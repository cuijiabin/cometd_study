package com.xiaoma.kefu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;

public class PropertiesUtil {
	/**
	 * 日志组件
	 */
	private static Logger logger = Logger.getLogger(PropertiesUtil.class.getName());

	private static PropertiesUtil instance = null;

	/**
	 * 配置文件名称
	 */
	private String configFileString = "system.properties";

	/**
	 * 只允许创建一个实例对象
	 */
	private PropertiesUtil() {
	}

	/**
	 * 创建本类的单例
	 * <p/>
	 * getInstance(单例)
	 * 
	 * @return PropertiesUtil对象
	 */
	public static PropertiesUtil getInstance() {
		if (instance == null) {
			instance = new PropertiesUtil();
		}
		return instance;
	}

	/**
	 * 读取配置文件
	 * 
	 * @return boolean
	 */
	public void readConfigFile() {
		// 读取配置文件
		try {
			InputStream inputStream = this.getClass().getClassLoader()
					.getResourceAsStream(configFileString);

			// 读路径出错，换另一种方式读取配置文件
			if (null == inputStream) {
				logger.error("read config file failed.");
			}

			// 读取配置文件中的appid和moduleId
			Properties p = new Properties();

			try {
				p.load(inputStream);
			} catch (IOException e1) {
				logger.error("Load config file failed." + e1);
			}
			// 得到文件路径
			CacheMan.addCache(CacheName.PAYURL,p.getProperty("payUrl"));
			CacheMan.addCache(CacheName.TUOFU,p.getProperty("tuofuUrl"));
			//邮箱相关
			CacheMan.addCache(CacheName.MAILADDRESS,p.getProperty("mail.mailAddress"));
			CacheMan.addCache(CacheName.MAILCOUNT,p.getProperty("mail.mailCount"));
			CacheMan.addCache(CacheName.MAILPASSWORD,p.getProperty("mail.mailPassword"));
			CacheMan.addCache(CacheName.MAILSERVER,p.getProperty("mail.mailServer"));
			
			//redis
			CacheMan.addCache(CacheName.REDISHOST,p.getProperty("redis.host"));
			CacheMan.addCache(CacheName.REDISPORT,p.getProperty("redis.port"));
			CacheMan.addCache(CacheName.REDISPASSWORD,p.getProperty("redis.password"));
			CacheMan.addCache(CacheName.REDISTIMEOUT,p.getProperty("redis.timeout"));
			
			CacheMan.addCache(CacheName.SECRETKEY,p.getProperty("secret.key"));
			
			//保存的文件的顶级目录
			CacheMan.addCache(CacheName.FILEROOT,p.getProperty("file.root"));
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	/**
	 * 获取支付平台路径
	 * @return
	 */
	public static String getProperties(String cacheName) {
		if (CacheMan.getCache(cacheName) == null)
			getInstance().readConfigFile();
		return CacheMan.getCache(cacheName).toString();

	}

	public static void main(String[] args) {
		System.out.println(getProperties(CacheName.PAYURL));
		System.out.println(getProperties(CacheName.REDISHOST));
	}
}
