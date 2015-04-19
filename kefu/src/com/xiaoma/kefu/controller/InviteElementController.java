package com.xiaoma.kefu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.model.InviteElement;
import com.xiaoma.kefu.service.InviteElementService;
import com.xiaoma.kefu.service.InviteIconService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * 邀请框元素	controller
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日上午10:51:56
**********************************
 */
@Controller
@RequestMapping(value = "inviteElement")
public class InviteElementController {

	private Logger logger = Logger.getLogger(InviteElementController.class);

	@Autowired
	private InviteElementService inviteElementService;
	@Autowired
	private InviteIconService inviteIconService;//邀请框
	
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param inviteId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "editPC.action", method = RequestMethod.GET)
	public String editPC(Model model,Integer inviteId,Integer id) {
		try {
			List<InviteElement> list = inviteElementService.listByInviteId(inviteId);
			Map<Integer,InviteElement> hm = new HashMap<Integer,InviteElement>();
			for(int i=0;i<list.size();i++){
				InviteElement ele = list.get(i);
				ele.setSortId(i+1);//设置序号
				hm.put(ele.getId(), ele);
			}
			InviteElement inviteElement = new InviteElement();
			if(id==null){//默认第一个
				if(list.size()>0){
					inviteElement = list.get(0);
				}
			}else{//按id取
				inviteElement = hm.get(id);
			}
			
//			InviteIcon inviteIcon = inviteIconService.getByStyleId(styleId,DeviceType.PC);
////			String offUrl = getViewPath(serviceIcon, StylePicName.客服图标PC离线);
//			List<DictItem> dict = DictMan.getDictList("d_location_model");
			model.addAttribute("elementList", list);
			model.addAttribute("inviteElement", inviteElement);
			model.addAttribute("inviteIcon", inviteIconService.get(inviteId));
			return "/style/invite/editElementPC";
		} catch (Exception e) {
			logger.error("editPC"+inviteId,e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 元素明细编辑
	* @param model
	* @param id
	* @param sortId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	@RequestMapping(value = "editDetailPC.action", method = RequestMethod.GET)
	public String editDetailPC(Model model,Integer id,Integer sortId) {
		try {
			InviteElement inviteElement = inviteElementService.get(id);
			inviteElement.setSortId(sortId);
			model.addAttribute("inviteElement", inviteElement);
			return "/style/invite/elementDetailPC";
		} catch (Exception e) {
			logger.error("editDetailPC"+id,e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 保存
	* @Description: TODO
	* @param model
	* @param groupFile
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	@RequestMapping(value = "savePC.action", method = RequestMethod.POST)
	public String savePC(Model model,MultipartFile groupFile,
			@ModelAttribute("inviteElement") InviteElement inviteElement) {
		try {
			inviteElementService.saveUplaodFile(groupFile,inviteElement,StylePicName.元素背景图);
//			//补充字段
			InviteElement oldModel = inviteElementService.get(inviteElement.getId());
			inviteElement.setName(oldModel.getName());
			inviteElement.setCreateDate(oldModel.getCreateDate());
			inviteElement.setUpdateDate(new Date());
			inviteElementService.update(inviteElement);
		} catch (Exception e) {
			logger.error("savePC"+inviteElement.getId(), e);
			return "error500";
		}
		return "redirect:/inviteElement/editPC.action?inviteId="+inviteElement.getInviteId()+"&id="+inviteElement.getId(); 
	}
	
	/**
	 * 编辑名称 (新增or更新)
	* @Description: TODO
	* @param model
	* @param id
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "editName.action", method = RequestMethod.GET)
	public String editName(Model model,Integer id,Integer inviteId) {
		try {
			InviteElement inviteElement;
			if(id!=null && id>0){
				inviteElement = inviteElementService.get(id);
			}else{
				inviteElement = new InviteElement();
				inviteElement.setInviteId(inviteId);
			}
			model.addAttribute("inviteElement", inviteElement);
			return "/style/invite/add";
		} catch (Exception e) {
			logger.error("editName"+id, e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 校验元素名称是否存在
	* @Description: TODO
	* @param model
	* @param style
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	@RequestMapping(value = "validate.action", method = RequestMethod.POST)
	public String validate(Model model, InviteElement inviteElement) {
		try {
			Integer isSuccess = inviteElementService.validateName(inviteElement);
			if (isSuccess == 0) {
				model.addAttribute("result", Ajax.JSONResult(0, "成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "名称已存在!"));
			}
		} catch (Exception e) {
			logger.error("validate"+inviteElement.getId(), e);
			model.addAttribute("result", Ajax.JSONResult(1, "失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 保存名称,用于 新增或更新名称
	* @Description: TODO
	* @param model
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	@RequestMapping(value = "saveName.action", method = RequestMethod.POST)
	public String saveName(Model model, @ModelAttribute("inviteElement") InviteElement inviteElement) {
		try {
			Integer id = inviteElement.getId();
			int isSuccess = 0;
			if(id==null){
				isSuccess = inviteElementService.create(inviteElement);
			}else{
				InviteElement toUpdate = inviteElementService.get(id);
				toUpdate.setName(inviteElement.getName());
				isSuccess = inviteElementService.update(toUpdate);
			}
			if (isSuccess > 0) {
				model.addAttribute("result", Ajax.JSONResult(0, inviteElement.getId().toString() ));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
			}
		} catch (Exception e) {
			logger.error("saveName"+inviteElement.getId(), e);
			model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 删除元素
	* @param model
	* @param inviteElement
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String delete(Model model,@ModelAttribute("inviteElement") InviteElement inviteElement) {
		try {
			int num = 0;
			if(inviteElement==null || inviteElement.getId()==null){
			}else{
				num = inviteElementService.delete(inviteElement);
			}
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error("delete失败!",e);
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		return "resultjson";
	}
	
}
