package com.xiaoma.kefu.service;

import java.io.IOException;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.dao.ClientStyleDao;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.ClientStyle;
import com.xiaoma.kefu.util.FileUtil;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * 访客端界面	业务实现类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:50:53
**********************************
 */
@Service
public class ClientStyleService {
	
	@Autowired
	private ClientStyleDao clientStyleDaoImpl;
	
	/**
	 * 创建
	* @param clientStyle
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public Integer create(ClientStyle clientStyle){
		clientStyle.setCreateDate(new Date());
		return (Integer) clientStyleDaoImpl.add(clientStyle);
	}
	
	/**
	 * 根据风格id查找
	* @Description: TODO
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ClientStyle getByStyleId(Integer styleId) {
		return clientStyleDaoImpl.findByStyleId(styleId);
	}
	
	/**
	 * 根据主键id查找
	* @Description: TODO
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public ClientStyle get(Integer id) {
		return clientStyleDaoImpl.findById(ClientStyle.class, id);
	}
	
	/**
	 *更新
	* @Description: TODO
	* @param clientStyle
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public Integer update(ClientStyle clientStyle) {
		return clientStyleDaoImpl.update(clientStyle);
	}
	
	/**
	 * 保存广告图片,生成缩略图
	* @Description: TODO
	* @param file
	* @param clientStyle
	* @param type	右上 or 右下
	* @throws IOException
	* @Author: wangxingfei
	* @Date: 2015年4月15日
	 */
	public void saveUplaodFile(MultipartFile file, ClientStyle clientStyle,
			StylePicName type) throws IOException {
        if (file != null && !file.isEmpty()) {
        	String savePath = getStyleRootPath(clientStyle.getStyleId());//获取需要保存的路径
            String saveName = type.getCode();
            String fileName = file.getOriginalFilename();//名称
            String extensionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀 .xxx
            
            //路径+文件名
            String tempPath = savePath+"/"+saveName;
            if(type.equals(StylePicName.访问端右上)){
            	clientStyle.setYsAd(tempPath+extensionName);//设置路径
            }else if(type.equals(StylePicName.访问端右下)){
            	clientStyle.setYxAd(tempPath+extensionName);//设置路径
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
	 * 风格上传图片的	根路径
	 * @param styleId 	风格id
	* @Description: TODO
	* @return	root/style/styleId
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	private String getStyleRootPath(Integer styleId) {
		if(styleId==null) styleId=0;
		return 
//				SystemConfiguration.getInstance().getFileUrl()
				DictMan.getDictItem("d_sys_param", 1).getItemName()
				+"/" + SysConst.STYLE_PATH
				+"/" + styleId;
	}



}