package com.xiaoma.kefu.service;

import java.io.IOException;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.dao.ServiceIconDao;
import com.xiaoma.kefu.model.ServiceIcon;
import com.xiaoma.kefu.util.FileUtil;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.DeviceType;
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
	


}