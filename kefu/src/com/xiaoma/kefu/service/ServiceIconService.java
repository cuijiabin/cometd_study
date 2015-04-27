package com.xiaoma.kefu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dao.ServiceIconDao;
import com.xiaoma.kefu.dict.DictMan;
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
	public void saveAndUpdateDiv4PC(MultipartFile fileOn, MultipartFile fileOff,
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
		updateDivCachePC(serviceIcon);
		
	}
	
	/**
	 * 更新div缓存	PC
	* @param serviceIcon
	* @Author: wangxingfei
	* @Date: 2015年4月26日
	 */
	private void updateDivCachePC(ServiceIcon serviceIcon) {
		List<FieldMapping> fmList = getFieldValueList(serviceIcon);
		
		//在线
		String divOn = SysConst.DIV_ICON_PC_ON;
		for(FieldMapping fm : fmList){//替换变量
			divOn = divOn.replace(fm.getDynaName(), fm.getDbValue());
		}
		System.out.println(divOn);
		CacheMan.update(CacheName.DIVICONPCON,serviceIcon.getStyleId(),divOn);
		
		//离线
		String divOff = SysConst.DIV_ICON_PC_OFF;
		for(FieldMapping fm : fmList){//替换变量
			divOff = divOff.replace(fm.getDynaName(), fm.getDbValue());
		}
		System.out.println(divOff);
		CacheMan.update(CacheName.DIVICONPCOFF,serviceIcon.getStyleId(),divOff);
		
		
	}

	
	/**
	 * 获得 字段映射 list
	* @param icon
	* @return	
	* @Author: wangxingfei
	* @Date: 2015年4月26日
	 */
	private List<FieldMapping> getFieldValueList(ServiceIcon icon) {
		List<FieldMapping> list = new ArrayList<FieldMapping>(10);
		boolean isPC = true; //是否PC 端
		if(icon.getDeviceType()!=null && icon.getDeviceType().equals(DeviceType.移动.getCode())){
			isPC = false;
		}
		
		//是否隐藏
		FieldMapping fm = new FieldMapping();
		fm.setFieldName(DivFieldName.isDisplay.toString());
		fm.setDefaultValue("display:block");
		fm.setDynaName(DivFieldName.isDisplay.getCode());
		if(icon.getIsDisplay()!=null && icon.getIsDisplay()==1){//不显示
			fm.setDbValue("display:none");
		}
		list.add(fm);
		
		//显示方式
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.position.toString());
		fm.setDefaultValue("position:fixed");
		fm.setDynaName(DivFieldName.position.getCode());
		if(icon.getDisplayMode()!=null && icon.getDisplayMode()!=2){//非浮动固定
			fm.setDbValue("position:absolute");
		}
		list.add(fm);
		
		//浮动位置水平
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.left.toString());
		fm.setDefaultValue("right:10px");
		fm.setDynaName(DivFieldName.left.getCode());
		if(icon.getSiteZyPx()!=null){
			fm.setDbValue(icon.getSiteZy()+":"+icon.getSiteZyPx()+"px");
		}
		list.add(fm);
		
		//浮动位置垂直
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.top.toString());
		fm.setDefaultValue("top:200px");
		fm.setDynaName(DivFieldName.top.getCode());
		if(icon.getSiteDdPx()!=null){
			fm.setDbValue(icon.getSiteDd()+":"+icon.getSiteDdPx()+"px");
		}
		list.add(fm);
		
		//在线图片
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.onlinePic.toString());
		fm.setDefaultValue("http://oc2.xiaoma.com//img/upload/53kf/zdytb/on_53kf1407116979.png");
		fm.setDynaName(DivFieldName.onlinePic.getCode());
		if(StringUtils.isNotBlank(icon.getOnlinePic())){
			if(isPC){
				fm.setDbValue(getViewPath(icon,StylePicName.客服图标PC在线));
			}else{
				fm.setDbValue(getViewPath(icon,StylePicName.客服图标移动在线));
			}
		}
		list.add(fm);
		
		//离线图片
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.offlinePic.toString());
		fm.setDefaultValue("http://oc2.xiaoma.com//img/upload/53kf/zdytb/on_53kf1407116979.png");
		fm.setDynaName(DivFieldName.offlinePic.getCode());
		if(StringUtils.isNotBlank(icon.getOfflinePic())){
			if(isPC){
				fm.setDbValue(getViewPath(icon,StylePicName.客服图标PC离线));
			}else{
				fm.setDbValue(getViewPath(icon,StylePicName.客服图标移动离线));
			}
		}
		list.add(fm);

		//宽
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.width.toString());
		fm.setDefaultValue("width:auto");
		fm.setDynaName(DivFieldName.width.getCode());
		if(icon.getWidth()!=null){
			fm.setDbValue("width:"+icon.getWidth()+"px");
		}
		list.add(fm);
		
		//高
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.height.toString());
		fm.setDefaultValue("height:auto");
		fm.setDynaName(DivFieldName.height.getCode());
		if(icon.getHeight()!=null){
			fm.setDbValue("height:"+icon.getHeight()+"px");
		}
		list.add(fm);
		
		//按钮id
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.buttonId.toString());
		fm.setDefaultValue("0");
		fm.setDynaName(DivFieldName.buttonId.getCode());
		fm.setDbValue(icon.getButtonId().toString());
		list.add(fm);
		
		return list;
	}

	
	/**
	 * 根据风格id,获取 在线 div 格式
	* @param styleId	风格id
	 * @param type 	手机or移动
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public String getDivOnline(Integer styleId, DeviceType type) {
		ServiceIcon icon = getByStyleId(styleId, type);
		String template = SysConst.DIV_ICON_PC_ON;
		if(type.equals(DeviceType.移动)){
			template = SysConst.DIV_ICON_YD_ON;
		}
		List<FieldMapping> fmList = getFieldValueList(icon);
		for(FieldMapping fm : fmList){//替换变量
			template = template.replace(fm.getDynaName(), fm.getDbValue());
		}
		
		return template;
	}
	
	/**
	 * 根据风格id,获取 离线 div 格式
	* @param styleId	风格id
	 * @param type 	手机or移动
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public String getDivOffline(Integer styleId, DeviceType type) {
		ServiceIcon icon = getByStyleId(styleId, type);
		String template = SysConst.DIV_ICON_PC_OFF;
		if(type.equals(DeviceType.移动)){
			template = SysConst.DIV_ICON_YD_OFF;
		}
		List<FieldMapping> fmList = getFieldValueList(icon);
		for(FieldMapping fm : fmList){//替换变量
			template = template.replace(fm.getDynaName(), fm.getDbValue());
		}
		
		return template;
	}
	
	
	/**
	 * 客服图标 展示的路径
	* @Description: TODO
	* @param serviceIcon
	* @param type
	* @return http://xxxx/style/styleId/xx.xx
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	private String getViewPath(ServiceIcon serviceIcon,StylePicName type) {
		String extensionName = "";
		if(type.equals(StylePicName.客服图标PC在线) || type.equals(StylePicName.客服图标移动在线)){
			String fileName = serviceIcon.getOnlinePic();
			if(StringUtils.isBlank(fileName)) return extensionName;
			extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
		}else if(type.equals(StylePicName.客服图标PC离线) || type.equals(StylePicName.客服图标移动离线)){
			String fileName = serviceIcon.getOfflinePic();
			if(StringUtils.isBlank(fileName)) return extensionName;
			extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
		}
		return 
				DictMan.getDictItem("d_sys_param", 15).getItemName()
				+ "/" + SysConst.STYLE_PATH //风格主目录
				+ "/"+serviceIcon.getStyleId()	//风格id
				+ "/"+type.getCode()	//类别
				+ extensionName	//后缀
				;
	}
	
	/**
	 * 移动端保存. 保存到数据库,并且更新 div
	* @param fileOn
	* @param fileOff
	* @param serviceIcon
	 * @throws IOException 
	* @Author: wangxingfei
	* @Date: 2015年4月27日
	 */
	public void saveAndUpdateDiv4YD(MultipartFile fileOn,
			MultipartFile fileOff, ServiceIcon serviceIcon) throws IOException {
		//保存文件 ys
		saveUplaodFile(fileOn,serviceIcon,StylePicName.客服图标移动在线);
		saveUplaodFile(fileOff,serviceIcon,StylePicName.客服图标移动离线);
		
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
		updateDivCacheYD(serviceIcon);
		
	}
	
	
	/**
	 * 更新div缓存	移动
	* @param serviceIcon
	* @Author: wangxingfei
	* @Date: 2015年4月27日
	 */
	private void updateDivCacheYD(ServiceIcon serviceIcon) {
		List<FieldMapping> fmList = getFieldValueList(serviceIcon);
		
		//在线
		String divOn = SysConst.DIV_ICON_YD_ON;
		for(FieldMapping fm : fmList){//替换变量
			divOn = divOn.replace(fm.getDynaName(), fm.getDbValue());
		}
		System.out.println(divOn);
		CacheMan.update(CacheName.DIVICONYDON,serviceIcon.getStyleId(),divOn);
		
		//离线
		String divOff = SysConst.DIV_ICON_YD_OFF;
		for(FieldMapping fm : fmList){//替换变量
			divOff = divOff.replace(fm.getDynaName(), fm.getDbValue());
		}
		System.out.println(divOff);
		CacheMan.update(CacheName.DIVICONYDOFF,serviceIcon.getStyleId(),divOff);
		
	}


}