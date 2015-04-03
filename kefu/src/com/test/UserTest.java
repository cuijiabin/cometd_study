package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.util.PageBean;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class UserTest {
	@Autowired
	private UserService userService;
	@Test
	public void test(){
	UserService user=new UserService();
	PageBean<User> pageBean = user.getResultByuserNameOrPhone(1, 10, null, null);
	System.out.println(pageBean.getObjList());
		
	}
}
