package com.xiaoma.kefu.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dao.InviteElementDao;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.FieldMapping;
import com.xiaoma.kefu.model.InviteElement;
import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.util.FileUtil;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.DeviceType;
import com.xiaoma.kefu.util.SysConst.DivFieldName;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * 邀请框元素	业务实现类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:50:53
**********************************
 */
@Service
public class InviteElementService {
	
	@Autowired
	private InviteElementDao inviteElementDaoImpl;
	
	@Autowired
	private InviteIconService inviteIconService;
	
	/**
	 * 根据主键id查找
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public InviteElement get(Integer id) {
		return inviteElementDaoImpl.findById(InviteElement.class, id);
	}
	
	/**
	 *更新
	* @Description: TODO
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public Integer update(InviteElement inviteElement) {
		return inviteElementDaoImpl.update(inviteElement);
	}
	
	/**
	 * 根据邀请框id,获取元素list
	* @Description: TODO
	* @param inviteId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月17日
	 */
	public List<InviteElement> listByInviteId(Integer inviteId) {
		return inviteElementDaoImpl.findByInviteId(inviteId);
	}
	
	/**
	 * 保存背景图
	* @Description: TODO
	* @param file
	* @param inviteElement
	 * @param type 
	 * @throws IOException 
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public void saveUplaodFile(MultipartFile file,
			InviteElement inviteElement, StylePicName type) throws IOException {
		 if (file != null && !file.isEmpty()) {
			
			InviteIcon inviteIcon = inviteIconService.get(inviteElement.getInviteId());
			 
			//获取需要保存的路径 = 跟路径+元素id
        	String savePath = FileUtil.getStyleRootPath(inviteIcon.getStyleId()) + "/" + inviteElement.getId() ;
        	String saveName = type.getCode();
            String fileName = file.getOriginalFilename();//名称
            String extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
            
            //路径+文件名
            String tempPath = savePath+"/"+saveName;
            inviteElement.setPicUrl(tempPath+extensionName);
            
            //保存文件
            FileUtil.saveFile(savePath, saveName+extensionName, file);
            
            //如果高或宽为空,则取图片的宽高
            if(inviteElement.getHeight()==null || inviteElement.getWidth()==null){
            	BufferedImage image = ImageIO.read(new File(inviteElement.getPicUrl()));  
            	inviteElement.setHeight(image.getHeight());
            	inviteElement.setWidth(image.getWidth());
            }

        }
	}

	/**
	 * 校验名称是否存在
	* @Description: TODO
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public Integer validateName(InviteElement inviteElement) {
		return inviteElementDaoImpl.validateName(inviteElement);
	}
	
	/**
	 * 新增
	* @Description: TODO
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public Integer create(InviteElement inviteElement) {
		inviteElement.setCreateDate(new Date());
		return (Integer) inviteElementDaoImpl.add(inviteElement);
	}
	
	/**
	 * 删除元素 ,同时删除元素中上传的文件
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public int delete(InviteElement inviteElement) {
		inviteElement = get(inviteElement.getId());
		InviteIcon inviteIcon = inviteIconService.get(inviteElement.getInviteId());
		String savePath = FileUtil.getStyleRootPath(inviteIcon.getStyleId()) + "/" + inviteElement.getId() ;
		FileUtil.deleteDir(savePath);
		
		return inviteElementDaoImpl.delete(inviteElement);
	}
	
	
	/**
	 * PC端保存. 保存到数据库,并且更新 整个div
	* @param groupFile
	* @param inviteElement
	 * @throws IOException 
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public void saveAndUpdateDiv4PC(MultipartFile groupFile,
			InviteElement inviteElement) throws IOException {
		saveUplaodFile(groupFile,inviteElement,StylePicName.元素背景图);
//		//补充字段
		InviteElement oldModel = get(inviteElement.getId());
		if(inviteElement.getPicUrl()==null){//如果这次没上传图片,则取上次的地址
			inviteElement.setPicUrl(oldModel.getPicUrl());
		}
		if(inviteElement.getHeight()==null){//高
			inviteElement.setHeight(oldModel.getHeight());
		}
		if(inviteElement.getWidth()==null){//宽
			inviteElement.setWidth(oldModel.getWidth());
		}
		inviteElement.setName(oldModel.getName());
		inviteElement.setCreateDate(oldModel.getCreateDate());
		inviteElement.setUpdateDate(new Date());
		update(inviteElement);
		
		InviteIcon inviteIcon = inviteIconService.get(inviteElement.getInviteId());
		String div = inviteIconService.getDiv(inviteIcon,DeviceType.PC);
		CacheMan.update(CacheName.DIVINVITEPC,inviteIcon.getStyleId(),div);
		
	}
	
	/**
	 * 获取所有元素 的div
	* @param inviteId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public String getDivByInviteId(Integer inviteId,DeviceType type){
		StringBuffer sbf = new StringBuffer();
		//邀请框下元素
		List<InviteElement> list = listByInviteId(inviteId);
		boolean hasFirst = false;//是否包含第一个元素
		boolean firstHasUrl = false;//第一个元素是否包含 连接地址
		boolean isPC = true;
		if(type.equals(DeviceType.移动)){
			isPC = false;
		}
		//循环更新元素
		for(InviteElement ele : list){
			boolean thisIsFirst = false;//当前元素是否是第一个元素
			boolean thisHasUrl = false;//当前元素是否有a标签
			
			//如果是第一个元素
			if(ele.getName().equals(SysConst.FIRST_ELEMENT_NAME)){
				hasFirst = true;
				thisIsFirst = true;
			}
			
			//如果有连接地址,先增加a标签
			if(StringUtils.isNotBlank(ele.getOpenUrl())){
				String aurl = getOperUrlDiv(ele);
				sbf.append(aurl);
				thisHasUrl = true;
				if(thisIsFirst){
					firstHasUrl = true;
				}
			}
			
			//增加元素 div
			String eleStr = getEleDiv(ele,thisIsFirst,isPC);
			sbf.append(eleStr); 
			
			//如果不是第一个元素, 并且有a标签,则增加 结束a标签
			if(!thisIsFirst && thisHasUrl){
				sbf.append(SysConst.A_END); 
			}
		}
		
		if(hasFirst){//如果有第一个元素, 则加个结束标签
			sbf.append(SysConst.DIV_END); 
		}
		if(firstHasUrl){//如果第一个元素有 a标签, 则在这里增加结束标签
			sbf.append(SysConst.A_END); 
		}
		
		return sbf.toString();
	}
	
	/**
	 * 获取元素 div 字符串
	* @param ele
	* @param thisIsFirst  是否第一个元素  true=是,false=否
	 * @param isPC 是否PC端
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月26日
	 */
	private String getEleDiv(InviteElement ele,boolean thisIsFirst, boolean isPC) {
		String temp = null;
		List<FieldMapping> eleFmList = null;
		if(isPC){
			if(thisIsFirst){
				temp = SysConst.DIV_TEMPLATE_ELE_FIRST;
			}else{
				temp = SysConst.DIV_TEMPLATE_ELE_OTHER;
			}
			eleFmList = getFieldValueList(ele,thisIsFirst,isPC);
		}else{
			if(thisIsFirst){
				temp = SysConst.DIV_TEMPLATE_ELE_FIRST_YD;
			}else{
				temp = SysConst.DIV_TEMPLATE_ELE_OTHER_YD;
			}
			eleFmList = getFieldValueList(ele,thisIsFirst,isPC);
		}
		//替换变量
		for(FieldMapping fm : eleFmList){
			temp = temp.replace(fm.getDynaName(), fm.getDbValue());
		}
		return temp;
	}

	/**
	 * 有打开连接的元素, 获取 a标签的 div内容
	* @param ele
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月26日
	 */
	private String getOperUrlDiv(InviteElement ele) {
		String temp = SysConst.DIV_TEMPLATE_ELE_A;
		List<FieldMapping> eleFmList = getFieldValueList4A(ele);
		for(FieldMapping fm : eleFmList){
			temp = temp.replace(fm.getDynaName(), fm.getDbValue());
		}
		return temp;
	}

	/**
	 * 获取第一个元素 字段和字段的值, 如果字段值为空,则用默认值
	* @param ele
	* @param isFirst 是否第一个元素, true 是, false 否
	 * @param isPC 是否PC
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	private List<FieldMapping> getFieldValueList(InviteElement ele,boolean isFirst, boolean isPC) {
		
		List<FieldMapping> list = new ArrayList<FieldMapping>(7);
		
		//宽
		FieldMapping fm = new FieldMapping();
		fm.setFieldName(DivFieldName.width.toString());
		fm.setDefaultValue("width:auto");
		fm.setDynaName(DivFieldName.width.getCode());
		if(ele.getWidth()!=null){
			fm.setDbValue("width:"+ele.getWidth()+"px");
		}
		if(isPC && isFirst){
			fm.setDefaultValue("width:680px");
		}
		if(!isPC && isFirst){
			fm.setDefaultValue("width:80%");
			fm.setDbValue("width:"+ele.getWidth()+"%");
		}
		list.add(fm);
		
		//高
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.height.toString());
		fm.setDefaultValue("height:auto");
		if(isFirst){
			fm.setDefaultValue("height:380px");
		}
		fm.setDynaName(DivFieldName.height.getCode());
		if(ele.getHeight()!=null){
			fm.setDbValue("height:"+ele.getHeight()+"px");
		}
		list.add(fm);
		
		//层叠顺序
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.index.toString());
		fm.setDefaultValue("0");
		fm.setDynaName(DivFieldName.index.getCode());
		if(ele.getLevel()!=null){
			fm.setDbValue(ele.getLevel().toString());
		}
		list.add(fm);
		
		//点击事件
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.onclick.toString());
		fm.setDefaultValue("");
		fm.setDynaName(DivFieldName.onclick.getCode());
		if(ele.getOperationType()!=null){
			if(ele.getOperationType()==2){//点击咨询
				InviteIcon tempIcon = inviteIconService.get(ele.getInviteId());
				fm.setDbValue("onclick=\\\"gotoKF('"+tempIcon.getButtonId()+"');\\\"  ");
			}else if(ele.getOperationType()==3){//点击关闭
				if(isPC){
					fm.setDbValue("onclick=\\\"iconType=1;hiddenKfbox();\\\"  ");
				}else{
					fm.setDbValue("onclick=\\\"iconType=3;hiddenKfbox();\\\"  ");
				}
			}
		}
		list.add(fm);
		
		//背景图
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.backgroundImg.toString());
		fm.setDynaName(DivFieldName.backgroundImg.getCode());
		if(isFirst){
			fm.setDefaultValue("http://oc2.xiaoma.com/img/upload/53kf/zdyivt/zdyivt_53kf_1429507915.png");
		}else{
			fm.setDefaultValue("");
		}
		if(ele.getPicUrl()!=null){
			fm.setDbValue(getViewPath(ele));
		}
		list.add(fm);
		
		if(!isFirst){//如果不是第一个元素,则增加位置设置
			//顶
			fm = new FieldMapping();
			fm.setFieldName(DivFieldName.top.toString());
			fm.setDefaultValue("top:0px");
			fm.setDynaName(DivFieldName.top.getCode());
			if(ele.getSiteTop()!=null){
				fm.setDbValue("top:"+ele.getSiteTop()+"px");
			}
			list.add(fm);
			
			//左
			fm = new FieldMapping();
			fm.setFieldName(DivFieldName.left.toString());
			fm.setDefaultValue("left:0px");
			fm.setDynaName(DivFieldName.left.getCode());
			if(ele.getSiteLeft()!=null){
				fm.setDbValue("left:"+ele.getSiteLeft()+"px");
			}
			list.add(fm);
		}
		
		
		return list;
	}
	
	/**
	 * 获取元素中 A标签的  字段和字段的值
	* @param ele
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	private List<FieldMapping> getFieldValueList4A(InviteElement ele) {
		
		List<FieldMapping> list = new ArrayList<FieldMapping>(2);
		
		//打开方式
		FieldMapping fm = new FieldMapping();
		fm.setFieldName(DivFieldName.target.toString());
		fm.setDefaultValue("_blank");
		fm.setDynaName(DivFieldName.target.getCode());
		fm.setDbValue(ele.getOpenType());
		list.add(fm);
		
		//打开地址
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.openUrl.toString());
		fm.setDefaultValue("");
		fm.setDynaName(DivFieldName.openUrl.getCode());
		fm.setDbValue(ele.getOpenUrl());
		list.add(fm);
		
		return list;
	}
	
	
	/**
	 * 获取邀请框的第一个元素, 就是外框
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月26日
	 */
	public InviteElement getFirstEle(Integer inviteId) {
		return inviteElementDaoImpl.findFirstEle(inviteId);
	}
	
	
	/**
	 * 客服图标 展示的路径
	* @Description: TODO
	* @param inviteElement
	* @param type
	* @return http://xxxx/style/styleId/eleId/xx.xx
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	private String getViewPath(InviteElement inviteElement) {
		String extensionName = "";
		String fileName = inviteElement.getPicUrl();
		if(StringUtils.isBlank(fileName)) return extensionName;
		extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
		InviteIcon inviteIcon = inviteIconService.get(inviteElement.getInviteId());
		return 
				DictMan.getDictItem("d_sys_param", 15).getItemName()
				+ "/" + SysConst.STYLE_PATH //风格主目录
				+ "/" + inviteIcon.getStyleId()	//风格id
				+ "/" + inviteElement.getId()	//元素id
				+ "/" + StylePicName.元素背景图.getCode()
				+ extensionName	//后缀
				;
	}
	
	/**
	 * 移动端保存. 保存到数据库,并且更新 整个div
	* @param groupFile
	* @param inviteElement
	 * @throws IOException 
	* @Author: wangxingfei
	* @Date: 2015年4月27日
	 */
	public void saveAndUpdateDiv4YD(MultipartFile groupFile,
			InviteElement inviteElement) throws IOException {
		saveUplaodFile(groupFile,inviteElement,StylePicName.元素背景图);
//		//补充字段
		InviteElement oldModel = get(inviteElement.getId());
		if(inviteElement.getPicUrl()==null){//如果这次没上传图片,则取上次的地址
			inviteElement.setPicUrl(oldModel.getPicUrl());;
		}
		if(inviteElement.getHeight()==null){//高
			inviteElement.setHeight(oldModel.getHeight());
		}
		if(inviteElement.getWidth()==null){//宽
			inviteElement.setWidth(oldModel.getWidth());
		}
		inviteElement.setName(oldModel.getName());
		inviteElement.setCreateDate(oldModel.getCreateDate());
		inviteElement.setUpdateDate(new Date());
		update(inviteElement);
		
		InviteIcon inviteIcon = inviteIconService.get(inviteElement.getInviteId());
		String div = inviteIconService.getDiv(inviteIcon,DeviceType.移动);
		CacheMan.update(CacheName.DIVINVITEYD,inviteIcon.getStyleId(),div);
	}  

}