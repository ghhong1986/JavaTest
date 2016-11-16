/* 
 * Copyright 畅捷通股份有限公司  @ 2015 版权所有    
 *    
 */
package com.abc.ai.csp.test.stat;

import com.abc.ai.csp.test.User;

/**
 * <p>
 * TODO (用一句话描述该文件做什么)   
 *</p>
 * @author 洪光华 <br/>
 */
public class UserService {
	  private UserDAO userDAO;  
	  
	    public void setUserDAO(UserDAO userDAO) {  
	        this.userDAO = userDAO;  
	    }  
	  
	        //被测方法getUser  
	    public User getUser(String id) {  
	        return this.userDAO.getUser(id);  
	    }  
}
