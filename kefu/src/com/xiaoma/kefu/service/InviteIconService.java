package com.xiaoma.kefu.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dao.InviteIconDao;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.FieldMapping;
import com.xiaoma.kefu.model.InviteElement;
import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.util.FileUtil;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.DeviceType;
import com.xiaoma.kefu.util.SysConst.DivFieldName;
import com.xiaoma.kefu.util.SysConst.StyleIconType;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * 对话邀请框	业务实现类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:50:53
**********************************
 */
@Service
public class InviteIconService {
	
	@Autowired
	private InviteIconDao inviteIconDaoImpl;
	@Autowired
	private InviteElementService inviteElementService;//元素
	
	/**
	 * 根据风格id,设备类型 查找
	* @Description: TODO
	* @param styleId
	 * @param type 
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public InviteIcon getByStyleId(Integer styleId, DeviceType deviceType) {
		return inviteIconDaoImpl.findByStyleId(styleId,deviceType);
	}
	
	/**
	 * 根据主键id查找
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public InviteIcon get(Integer id) {
		return inviteIconDaoImpl.findById(InviteIcon.class, id);
	}
	
	/**
	 *更新
	* @Description: TODO
	* @param inviteIcon
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public Integer update(InviteIcon inviteIcon) {
		return inviteIconDaoImpl.update(inviteIcon);
	}
	
	/**
	 * 创建
	* @param inviteIcon
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	private Integer create(InviteIcon inviteIcon) {
		inviteIcon.setCreateDate(new Date());
		return (Integer) inviteIconDaoImpl.add(inviteIcon);
		
	}
	
	/**
	 * 初始化对话邀请框
	 * 附带初始化邀请框元素
	* @param styleId
	* @param deviceType
	 * @throws Exception 
	 * @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public void initInviteIcon(Integer styleId, DeviceType deviceType) throws Exception {
		InviteIcon inviteIcon = new InviteIcon();
		inviteIcon.setStyleId(styleId);
		inviteIcon.setDeviceType(deviceType.getCode());
		inviteIcon.setLocationMode(2);//默认自动居中
		
		if(deviceType.equals(DeviceType.PC)){
			Integer buttonId = Integer.valueOf(styleId+""+StyleIconType.对话邀请框.getCode());
			inviteIcon.setButtonId(buttonId);
		}else{
			Integer buttonId = Integer.valueOf(styleId+""+StyleIconType.手机端对话邀请框.getCode());
			inviteIcon.setButtonId(buttonId);
		}
		create(inviteIcon);//创建
		
		//初始化一个外框元素
		InviteElement ele = new InviteElement();
		ele.setInviteId(inviteIcon.getId());
		ele.setName(SysConst.FIRST_ELEMENT_NAME);
		ele.setLevel(0);
		ele.setOperationType(2);//默认点击咨询
		
		inviteElementService.create(ele);
		try{
			initIconPic(ele,styleId,deviceType);//初始化邀请框图片
		}catch(Exception e){
			throw new Exception(e);
		}
		inviteElementService.update(ele);
		
	}

	/**
	 * 初始化 邀请框图片
	* @param ele
	 * @param styleId 
	* @param type
	 * @throws IOException 
	* @Author: wangxingfei
	* @Date: 2015年4月28日
	 */
	private void initIconPic(InviteElement ele, Integer styleId, DeviceType type) throws IOException {
		String sourcePath = null;
		if(type.equals(DeviceType.PC)){
			sourcePath = DictMan.getDictItem("d_sys_param", 16).getItemName() 
					+ "/" + SysConst.TEMPLATE_PATH
					+ "/" + SysConst.PIC_TEMPLATE_PC_INVITE;
			BufferedImage image = ImageIO.read(new File(sourcePath));  
        	ele.setHeight(image.getHeight());
        	ele.setWidth(image.getWidth());
		}else{
			sourcePath = DictMan.getDictItem("d_sys_param", 16).getItemName() 
					+ "/" + SysConst.TEMPLATE_PATH
					+ "/" + SysConst.PIC_TEMPLATE_YD_INVITE;
			BufferedImage image = ImageIO.read(new File(sourcePath));  
        	ele.setHeight(image.getHeight());
        	ele.setWidth(30);//手机默认宽度30%
		}
		
		String targetPath = FileUtil.getStyleRootPath(styleId) + "/" + ele.getId()
				+ "/" + StylePicName.元素背景图.getCode()
				+ SysConst.MIN_EXTENSION;//目前都使用 png
		ele.setPicUrl(targetPath);
		FileUtil.copyFile(sourcePath, targetPath);
		
	}

	/**
	 * 获取字段和字段的值, 如果字段值为空,则用默认值
	* @param icon
	 * @param pvwEle 预览的元素	,如果为空,则不是预览
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	private List<FieldMapping> getFieldValueList(InviteIcon icon, InviteElement pvwEle) {
		List<FieldMapping> list = new ArrayList<FieldMapping>(5);
		
		FieldMapping fm = new FieldMapping();
		fm.setFieldName(DivFieldName.position.toString());
		fm.setDefaultValue("position:absolute");
		fm.setDynaName(DivFieldName.position.getCode());
		if(icon.getLocationMode()!=null && icon.getLocationMode()==3){//浮动固定
			fm.setDbValue("position:fixed");
		}
		list.add(fm);
		
		//位置垂直
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.top.toString());
		fm.setDefaultValue("top:0px");
		fm.setDynaName(DivFieldName.top.getCode());
		if(icon.getSiteDdPx()!=null){
			fm.setDbValue(icon.getSiteDd()+":"+icon.getSiteDdPx()+"px");
		}
		list.add(fm);
		
		//浮动位置水平
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.left.toString());
		fm.setDefaultValue("right:0px");
		fm.setDynaName(DivFieldName.left.getCode());
		if(icon.getSiteZyPx()!=null){
			fm.setDbValue(icon.getSiteZy()+":"+icon.getSiteZyPx()+"px");
		}
		list.add(fm);
		
		//宽和高, 取外框里面的宽和高
		InviteElement firstEle = inviteElementService.getFirstEle(icon.getId());
		
		//如果是预览,且是第一个元素, 则取预览的值
		if(pvwEle!=null && pvwEle.getId()== firstEle.getId()){
			firstEle = pvwEle;
		}
		//宽
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.width.toString());
		fm.setDefaultValue("width:auto");
		fm.setDynaName(DivFieldName.width.getCode());
		if(firstEle.getWidth()!=null){
			fm.setDbValue("width:"+firstEle.getWidth()+"px");
		}
		list.add(fm);
		
		//高
		fm = new FieldMapping();
		fm.setFieldName(DivFieldName.height.toString());
		fm.setDefaultValue("height:auto");
		fm.setDynaName(DivFieldName.height.getCode());
		if(firstEle.getHeight()!=null){
			fm.setDbValue("height:"+firstEle.getHeight()+"px");
		}
		list.add(fm);
		
		return list;
		
	}
	
	
	/**
	 * PC端保存. 保存到数据库,并且更新 div
	* @param inviteIcon
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public void saveAndUpdateDiv4PC(InviteIcon inviteIcon) {
//		//补充字段
		InviteIcon oldModel = get(inviteIcon.getId());
		inviteIcon.setCreateDate(oldModel.getCreateDate());
		inviteIcon.setTruePic(oldModel.getTruePic());
		inviteIcon.setButtonId(oldModel.getButtonId());
		inviteIcon.setUpdateDate(new Date());
		update(inviteIcon);
		
		//更新div
		String div = getViewDiv(inviteIcon,DeviceType.PC);
		System.out.println(div);
		CacheMan.update(CacheName.DIVINVITEPC,inviteIcon.getStyleId(),div);
	}
	
	/**
	 * 移动 端保存. 保存到数据库,并且更新 div
	* @param inviteIcon
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public void saveAndUpdateDiv4YD(InviteIcon inviteIcon) {
		//补充字段
		InviteIcon oldModel = get(inviteIcon.getId());
		inviteIcon.setCreateDate(oldModel.getCreateDate());
		inviteIcon.setTruePic(oldModel.getTruePic());
		inviteIcon.setButtonId(oldModel.getButtonId());
		inviteIcon.setUpdateDate(new Date());
		update(inviteIcon);
		
		//更新div
		String div = getViewDiv(inviteIcon,DeviceType.移动);
		System.out.println(div);
		CacheMan.update(CacheName.DIVINVITEYD,inviteIcon.getStyleId(),div);
		
	}
	
	/**
	 * 获取div
	* @param inviteIcon
	 * @param inviteElement 当前预览的元素,还没保存到数据库
	 * @param isEdit	是否是编辑  如果是,则内层div\"要替换
	* @param type	PC,移动
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月28日
	 */
	public String getPvwDiv(InviteIcon inviteIcon, InviteElement inviteElement,boolean isEdit, DeviceType type) {
		StringBuffer sbf = new StringBuffer();
		//邀请框模板
		String template = SysConst.DIV_TEMPLATE_INVITE;
		if(type.equals(DeviceType.移动)){
			template = SysConst.DIV_TEMPLATE_INVITE_YD;
		}
		List<FieldMapping> inviteFmList = getFieldValueList(inviteIcon,inviteElement);
		for(FieldMapping fm : inviteFmList){
			template = template.replace(fm.getDynaName(), fm.getDbValue());
		}
		
		sbf.append(template);
		sbf.append(inviteElementService.getViewDiv(inviteIcon.getId(),inviteElement,isEdit, type));//元素明细的
		sbf.append(SysConst.DIV_END); //邀请框的结束标签
		System.out.println("邀请框div="+sbf.toString());
		return sbf.toString();
	}
	
	
	/**
	 * 根据风格id,获取div 格式	用于引用站点使用
	* @param styleId
	 * @param type 
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	public String getDivByStyleId(Integer styleId,DeviceType type){
		InviteIcon inviteIcon = getByStyleId(styleId, type);
		return getViewDiv(inviteIcon,type);
	}
	
	/**
	 * 获取最终展示div
	* @param inviteIcon
	* @param type
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月28日
	 */
	public String getViewDiv(InviteIcon inviteIcon, DeviceType type){
		return getPvwDiv(inviteIcon,null,false,type);
	}

}