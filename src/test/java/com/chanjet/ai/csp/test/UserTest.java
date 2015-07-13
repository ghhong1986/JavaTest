/*
 * Copyright 畅捷通股份有限公司 @ 2015 版权所有
 */
package com.chanjet.ai.csp.test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
/**
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 */
public class UserTest {
	private UserService userService;
	private UserDAO userDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		userDAO = createMock(UserDAO.class);
		userService = new UserService();
		userService.setUserDAO(userDAO);
	}

	@Test
	public void testGetUser() {
		userDAO.getUser(anyObject(String.class));
		User user1 = new User("1", "Jack");
		User user2 = new User("2", "Lucy");
		expectLastCall().andReturn(user1).andReturn(user2);
		replay(userDAO);

		assertEquals("Jack", userService.getUser("1").getName());
		assertEquals("Lucy", userService.getUser("2").getName());

		verify(userDAO);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testGetUserParamError() {
		reset(userDAO);

		userDAO.getUser(anyObject(String.class));
		expectLastCall().andThrow(new NumberFormatException());

		replay(userDAO);

		userService.getUser("abc");
	}
	
	/*Mocking static methods
	Mocking final methods or classes
	Mocking private methods
	Mock construction of new objects*/
	
	@Test
	public void testBigTha你(){
		assertThat(12, greaterThan(10));
	}

}
