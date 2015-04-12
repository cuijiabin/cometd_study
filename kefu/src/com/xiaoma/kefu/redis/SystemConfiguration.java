package com.xiaoma.kefu.redis;

import java.io.InputStream;
import java.util.Properties;

import com.xiaoma.kefu.util.PropertiesHelper;

public class SystemConfiguration {
	private static SystemConfiguration instance;
	private String host;
	private Integer port;
	private Integer timeout;
	private String password;
	private String secretKey;
	private String fileUrl;
	private String mailServer;
	private String mailAddress;
	private String mailCount;
	private String mailPassword;
	
	static synchronized public SystemConfiguration getInstance() {
		//if (instance == null) 
		{
			instance = new SystemConfiguration("/system.properties");
		}
		return instance;
	}
	protected SystemConfiguration(String propertiesFile) {
		InputStream fin = getClass().getResourceAsStream(propertiesFile);
		Properties dbProps = new Properties();
		try {
			dbProps.load(fin);
			this.host = PropertiesHelper.getString("redis.host", dbProps, "");
			this.port = PropertiesHelper.getInt("redis.port", dbProps, 20);
			this.timeout = PropertiesHelper.getInt("redis.timeout", dbProps, 20);
			this.password = PropertiesHelper.getString("redis.password", dbProps, "");
			this.secretKey = PropertiesHelper.getString("secret.key", dbProps, "");
			this.fileUrl = PropertiesHelper.getString("file.root", dbProps, "c:/temp");
			this.mailServer = PropertiesHelper.getString("mail.mailServer", dbProps, "");
			this.mailAddress = PropertiesHelper.getString("mail.mailAddress", dbProps, "");
			this.mailCount = PropertiesHelper.getString("mail.mailCount", dbProps, "");
			this.mailPassword = PropertiesHelper.getString("mail.mailPassword", dbProps, "");
			fin.close();
		} catch (Exception e) {
		}
	}
    public static Integer[] toIntArray(String[] str) {
    	Integer [] num=new Integer[str.length];
        for(int i=0;i<num.length;i++){
            num[i]=Integer.parseInt(str[i]);
        }
        return num;
    }
    
	public String getMailServer() {
		return mailServer;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public String getMailCount() {
		return mailCount;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	public String getHost() {
		return host;
	}
	public Integer getPort() {
		return port;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public String getPassword() {
		return password;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public static void main(String[] args) throws Exception {
		SystemConfiguration sc = SystemConfiguration.getInstance();
		System.out.println(sc.getFileUrl());
	}
}