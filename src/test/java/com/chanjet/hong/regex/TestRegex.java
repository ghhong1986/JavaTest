package com.chanjet.hong.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestRegex {

	@Test
	public void testRegexGroup() {
		String reg = ".*[搜索|查找|查](.*)(.*)";

		String text = "在智能平台里面搜索桑海岩abc发的帖子机器学习分析";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(text);
		System.out.println(mat.groupCount());
		if (mat.find()) {
			System.out.println(mat.group(1));
			System.out.println(mat.group(2));
		}
	}
}
