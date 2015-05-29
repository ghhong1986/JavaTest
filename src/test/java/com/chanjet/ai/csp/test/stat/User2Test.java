/*
 * Copyright 畅捷通股份有限公司 @ 2015 版权所有
 */
package com.chanjet.ai.csp.test.stat;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.chanjet.ai.csp.test.User;

import static org.powermock.api.easymock.PowerMock.*;

/**
 * <p>
 * 对于org.easymock.EasyMock的方法调用变成了org.powermock.api.easymock.PowerMock的方法调用；
 * 使用了RunWith和PrepareForTest注解；
 * 由于是静态方法，需要显示调用mockStatic方法来完成mock操作。
 * </p>
 * 
 * @author 洪光华 </br>
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserDAO.class)
public class User2Test {
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		UserDAO  dao =  createMock(UserDAO.class);
		userService = new UserService();
		userService.setUserDAO(dao);
	}

	@Test
	public void testGetUser() {

		mockStatic(UserDAO.class);

		UserDAO.getUser(EasyMock.anyObject(String.class));
		User user1 = new User("1", "Jack");
		User user2 = new User("2", "Lucy");
		expectLastCall().andReturn(user1).andReturn(user2);

		replay(UserDAO.class);

		assertEquals("Jack", userService.getUser("1").getName());
		assertEquals("Lucy", userService.getUser("2").getName());

		verify(UserDAO.class);
	}
}
