package com.xiaoma.kefu.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.dao.InviteElementDao;
import com.xiaoma.kefu.model.InviteElement;
import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.util.FileUtil;
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
	 * 删除元素
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public int delete(InviteElement inviteElement) {
		return inviteElementDaoImpl.delete(inviteElement);
	}

}