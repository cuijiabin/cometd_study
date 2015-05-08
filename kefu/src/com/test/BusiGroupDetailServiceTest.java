package com.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.model.Department;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.BusiGroupDetailService;

/**
 * 等待菜单列表	测试
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午3:45:02
**********************************
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class BusiGroupDetailServiceTest {
	@Autowired
	private BusiGroupDetailService busiGroupDetailService;//业务分组明细
	
	@Test
	public void testUpdateUser(){
		User user = new User();
		user.setId(38);
		user.setCardName("王老师");
		Integer num = busiGroupDetailService.updateUserCardName(user);
		Assert.assertEquals(num.intValue(), 2);
	}
	
	@Test
	public void testUpdateDept(){
		Department dept = new Department();
		dept.setId(15);
		dept.setName("客服部2");
		Integer num = busiGroupDetailService.updateDeptName(dept);
		Assert.assertEquals(num.intValue(), 2);
	}
	
	
}
