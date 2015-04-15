package com.xiaoma.kefu.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.model.ClientStyle;
import com.xiaoma.kefu.redis.SystemConfiguration;
import com.xiaoma.kefu.service.ClientStyleService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.FileUtil;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * 访客端界面	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:56
**********************************
 */
@Controller
@RequestMapping(value = "clientStyle")
public class ClientStyleController {

	private Logger logger = Logger.getLogger(ClientStyleController.class);

	@Autowired
	private ClientStyleService clientStyleService;
	
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "edit.action", method = RequestMethod.GET)
	public String edit(Model model,Integer styleId) {
		try {
			ClientStyle clientStyle = clientStyleService.getByStyleId(styleId);
			model.addAttribute("clientStyle", clientStyle);
			return "/style/client/edit";
		} catch (Exception e) {
			logger.error("edit"+styleId);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 保存
	* @Description: TODO
	* @param model
	* @param customer
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("clientStyle") ClientStyle clientStyle) {
		try {
			ClientStyle oldModel = clientStyleService.get(clientStyle.getId());
			clientStyle.setCreateDate(oldModel.getCreateDate());
			clientStyle.setUpdateDate(new Date());
			Integer isSuccess = clientStyleService.update(oldModel);
			if (isSuccess > 0) {
				model.addAttribute("result", Ajax.JSONResult(0, "操作成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
		}
		return "resultjson";
	}
	
	@RequestMapping(value = {"/uploadYs" },method=RequestMethod.POST)  
    public String uploadYs(MultipartFile fileYs,
//    		@ModelAttribute("clientStyle") ClientStyle clientStyle,
//    		@RequestParam("styleId")String styleId,
    		@ModelAttribute("styleId")String styleId
    		) throws IOException {
//		System.out.println(request.getParameterNames()); 
//		System.out.println(request.getParameter("styleId"));
		return "";
	}
	
	/**
	 * 图片上传
	 * 1.保存图片
	 * 2.生成缩略图
	 * 3.返回缩略图路径
	* @Description: TODO
	* @param fileYs
	* @param styleId 风格id
	* @param model
	* @return
	* @throws IOException
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	@RequestMapping(value = {"/uploadYs2" },method=RequestMethod.POST)  
    public String uploadYs2(HttpServletRequest request,MultipartFile fileYs,
    		Integer styleId,Integer id, Model model) throws IOException {
		System.out.println(request.getParameter("styleId"));
		ClientStyle clientStyle = clientStyleService.get(styleId);
        if (fileYs != null && !fileYs.isEmpty()) {
        	String savePath = getStyleRootPath(clientStyle.getStyleId());//获取需要保存的路径
            String fileName = fileYs.getOriginalFilename();
            String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1); // 获取图片的扩展名
            // 新的图片文件名 = 获取时间戳+"."图片扩展名
            String saveName = StylePicName.访问端右上.getCode()+ "." + extensionName;
            String tempPath = savePath+File.separator+saveName;
            
            //更新
            ClientStyle oldModel = clientStyleService.get(clientStyle.getId());
			clientStyle.setYsAd(tempPath+extensionName);
			clientStyleService.update(oldModel);
			
            try {
                FileUtil.saveFile(savePath, saveName, fileYs);//保存文件
                model.addAttribute("result", Ajax.JSONResult(0, tempPath+SysConst.MIN_PIC_SUFFIX+extensionName));
            } catch (Exception e) {
                logger.error("上传图片失败.", e);
                model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
            }
        }
        return "resultjson";
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
		return SystemConfiguration.getInstance().getFileUrl()
				+File.separator + SysConst.STYLE_PATH
				+File.separator+styleId;
	}  
	
	
}
