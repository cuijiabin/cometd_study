package com.xiaoma.kefu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dao.ServiceIconDao;
import com.xiaoma.kefu.model.FieldMapping;
import com.xiaoma.kefu.model.ServiceIcon;
import com.xiaoma.kefu.util.FileUtil;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.DeviceType;
import com.xiaoma.kefu.util.SysConst.DivFieldName;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * 客服图标	业务实现类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:50:53
**********************************
 */
@Service
public class ServiceIconService {
	
	@Autowired
	private ServiceIconDao serviceIconDaoImpl;
	
	/**
	 * 根据风格id,设备类型 查找
	* @Description: TODO
	* @param styleId
	 * @param type 
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ServiceIcon getByStyleId(Integer styleId, DeviceType deviceType) {
		return serviceIconDaoImpl.findByStyleId(styleId,deviceType);
	}
	
	/**
	 * 根据主键id查找
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ServiceIcon get(Integer id) {
		return serviceIconDaoImpl.findById(ServiceIcon.class, id);
	}
	
	/**
	 *更新
	* @Description: TODO
	* @param clientStyle
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public Integer update(ServiceIcon serviceIcon) {
		return serviceIconDaoImpl.update(serviceIcon);
	}
	
	/**
	 * 保存图片,生成缩略图
	* @Description: TODO
	* @param file
	* @param serviceIcon
	* @param type	在线 or 离线
	* @throws IOException
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	public void saveUplaodFile(MultipartFile file, ServiceIcon serviceIcon,
			StylePicName type) throws IOException {
        if (file != null && !file.isEmpty()) {
        	String savePath = FileUtil.getStyleRootPath(serviceIcon.getStyleId());//获取需要保存的路径
            String saveName = type.getCode();
            String fileName = file.getOriginalFilename();//名称
            String extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
            
            //路径+文件名
            String tempPath = savePath+"/"+saveName;
            if(type.equals(StylePicName.客服图标PC在线) || type.equals(StylePicName.客服图标移动在线)){
            	serviceIcon.setOnlinePic(tempPath+extensionName);//在线
            }else if(type.equals(StylePicName.客服图标PC离线) || type.equals(StylePicName.客服图标移动离线)){
            	serviceIcon.setOfflinePic(tempPath+extensionName);//离线
            }
            
            //保存文件
            FileUtil.saveFile(savePath, saveName+extensionName, file);
            
            //生成缩略图
            Thumbnails.of(tempPath+extensionName)//原始路径
            	.size(200, 300)	//要压缩到的尺寸size(宽度, 高度) 原始图片小于则不变
            	.toFile(tempPath+SysConst.MIN_PIC_SUFFIX+extensionName);//压缩后的路径
        }
		
	}
	
	/**
	 * 创建
	* @param serviceIcon
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public Integer create(ServiceIcon serviceIcon) {
		serviceIcon.setCreateDate(new Date());
		return (Integer) serviceIconDaoImpl.add(serviceIcon);
	}
	
	/**
	 * PC端保存. 保存到数据库,并且更新 div
	* @param fileOn
	* @param fileOff
	* @param serviceIcon
	 * @throws IOException 
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public void saveAndUpdatePC(MultipartFile fileOn, MultipartFile fileOff,
			ServiceIcon serviceIcon) throws IOException {
		//保存文件 ys
		saveUplaodFile(fileOn,serviceIcon,StylePicName.客服图标PC在线);
		saveUplaodFile(fileOff,serviceIcon,StylePicName.客服图标PC离线);
		
//		//拿出旧的创建时间,类型,按钮id, 别的全用新的
		ServiceIcon oldModel = get(serviceIcon.getId());
		serviceIcon.setCreateDate(oldModel.getCreateDate());
		serviceIcon.setButtonId(oldModel.getButtonId());
		serviceIcon.setUpdateDate(new Date());
		if(serviceIcon.getOnlinePic()==null){//如果这次没上传图片,则取上次的地址
			serviceIcon.setOnlinePic(oldModel.getOnlinePic());;
		}
		if(serviceIcon.getOfflinePic()==null){//如果这次没上传图片,则取上次的地址
			serviceIcon.setOfflinePic(oldModel.getOfflinePic());
		}
		update(serviceIcon);
		
		//更新div字符串 到缓存
		String template = SysConst.DIV_TEMPLATE_ICON;
		List<FieldMapping> fmList = getFieldValueList(serviceIcon);
		for(FieldMapping fm : fmList){
			template = template.replace(fm.getDynaName(), fm.getDbValue());
		}
		CacheMan.update(CacheName.DIVICONPC,serviceIcon.getStyleId(),template);
		
	}
	
	/**
	 * 获取字段和字段的值, 如果字段值为空,则用默认值
	* @param serviceIcon
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	private List<FieldMapping> getFieldValueList(ServiceIcon serviceIcon) {
		Map<String,String> hm = packFieldValue2Map(serviceIcon);
		List<FieldMapping> fieldList = getDefaultFieldValueList();
		//设置dbValue
		for(FieldMapping fm : fieldList){
			fm.setDbValue(hm.get(fm.getFieldName()));
		}
		
		return fieldList;
	}
	
	/**
	 * 获得 初始化的  字段映射 列表, 带默认值
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	private List<FieldMapping> getDefaultFieldValueList() {
		List<FieldMapping> list = new ArrayList<FieldMapping>(10);
		
		FieldMapping fm = new FieldMapping();
		fm.setFieldName(DivFieldName.top.toString());
		fm.setDefaultValue("top:0px");
		fm.setDynaName(DivFieldName.top.getCode());
		list.add(fm);
		
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.left.toString());
		fm.setDefaultValue("left:0px");
		fm.setDynaName(DivFieldName.left.getCode());
		list.add(fm);
		
		return list;
	}

	/**
	 * 将 属性 和 value ,按照固定模式, 封装成map
	* @param serviceIcon
	* @return	key=字段名称	value=dbvalue
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	private Map<String, String> packFieldValue2Map(ServiceIcon serviceIcon) {
		Map<String,String> hm = new HashMap<String,String>();
		hm.put(DivFieldName.top.toString(), serviceIcon.getSiteDd()+":"+serviceIcon.getSiteDdPx()+"px");
		hm.put(DivFieldName.left.toString(), serviceIcon.getSiteZy()+":"+serviceIcon.getSiteZyPx()+"px");
		
		return hm;
	}
	
	/**
	 * 根据风格id,获取div 格式
	* @param styleId
	 * @param type 
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public String getDivByStyleId(Integer styleId, DeviceType type) {
		ServiceIcon icon = getByStyleId(styleId, type);
		
		String template = SysConst.DIV_TEMPLATE_ICON;
		List<FieldMapping> fmList = getFieldValueList(icon);
		//替换变量
		for(FieldMapping fm : fmList){
			template = template.replace(fm.getDynaName(), fm.getDbValue());
		}
		
		return template;
	}
	


}