package com.xiaoma.kefu.model;

/**
 * 图标,邀请框 div 设置时,  字段和模板中变量 及 值的关系 
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月24日下午2:20:04
**********************************
 */
public class FieldMapping {
	
	/**
	 * 字段名称  
	 */
	private String fieldName;
	
	/**
	 * 模板中的变量名称 
	 */
	private String dynaName;
	
	/**
	 * 字段的值  数据库存放的
	 */
	private String dbValue;
	
	/**
	 * 字段的默认值, 如果数据库值为空,则取默认值
	 */
	private String defaultValue;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDynaName() {
		return dynaName;
	}

	public void setDynaName(String dynaName) {
		this.dynaName = dynaName;
	}
	
	/**
	 * 如果dbValue为空,则返回默认Value
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public String getDbValue() {
		if(dbValue==null || dbValue.equals("")){
			return defaultValue;
		}
		return dbValue;
	}

	public void setDbValue(String dbValue) {
		this.dbValue = dbValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
