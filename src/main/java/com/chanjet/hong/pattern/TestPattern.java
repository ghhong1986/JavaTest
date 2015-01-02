/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.pattern;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Joiner;

/**
 * 
 * <p>
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年11月12日 下午1:18:53
 * 
 * @version V1.0
 * 
 */
@SuppressWarnings("all")
public class TestPattern {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Map<String, String> tokens = new HashMap();
		tokens.put("cat", "Garfield");
		tokens.put("beverage", "coffee"); // 匹配类似velocity规则的字符串
		String template = "${cat} really needs some ${beverage}."; // 生成匹配模式的正则表达式
		//String patternString = "\\$\\{(" + Joiner.on(" | ").join(tokens.keySet()) + ")\\}";
		String patternString = "\\$\\{(" + "\\w+" + ")\\}";
		System.out.println(patternString);
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(template); // 两个方法：appendReplacement,
		                                             // appendTail
		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
		}
		matcher.appendTail(sb);
		// out: Garfield really needs some coffee.
		System.out.println(sb.toString()); // 对于特殊含义字符”\”,”$”，使用Matcher.quoteReplacement消除特殊意义
		matcher.reset(); // out: cat really needs some beverage.
		System.out.println(matcher.replaceAll("$1"));
		// out: $1 really needs some $1.
		System.out.println(matcher.replaceAll(Matcher.quoteReplacement("$1")));

	}

}
