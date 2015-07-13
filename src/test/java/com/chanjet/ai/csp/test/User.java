/* 
 * Copyright 畅捷通股份有限公司  @ 2015 版权所有    
 *    
 */
package com.chanjet.ai.csp.test;

/**
 * <p>
 * TODO (用一句话描述该文件做什么)   
 *</p>
 * @author 洪光华 <br/>
 */
public class User {
	private String id;
	private String name;
	public User(String id, String name) {
		this.id = id;
		this.name = name;
        }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
