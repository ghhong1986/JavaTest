/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.JavaTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com
 * @date 2014年9月28日 下午7:25:29
 * 
 * @version V1.0
 * 
 */
public class TestSentimentPositiveMatch {
	public static void main(String[] args) {
		String str = "你假如上午没给我吃冰淇淋，我绝对会不happy的。";

		// 语义映射
		Map<String, String> sentimentMap = new HashMap<String, String>();
		sentimentMap.put("happy", "高兴");

		// 情感词库
		List<String> sentimentDict = new ArrayList<String>();
		sentimentDict.add("happy");

		// 修饰词
		List<String> decorativeDict = new ArrayList<String>();
		decorativeDict.add("不");
		decorativeDict.add("没");

		// 修饰词衡量分数
		Map<String, Double> decorativeScoreMap = new HashMap<String, Double>();
		decorativeScoreMap.put("不", -0.5);
		decorativeScoreMap.put("没", -0.5);

		List<String> decorativeWordList = new ArrayList<String>(); // 修饰词
		String sentimentResult = ""; // 情感结果

		int strLen = str.length(); // 传入字符串的长度
		int j = 0;
		String matchSentimentWord = ""; // 根据词库里识别出来的情感词
		String matchDecorativeWord = ""; // 根据词库里识别出来的修饰词
		int matchPos = 0; // 根据词库里识别出来词后当前句子中的位置
		while (j < strLen) { // 从0字符匹配到字符串结束
			int matchPosTmp = 0; // 截取字符串的位置
			int i = 1;
			while (matchPosTmp < strLen) { // 从当前位置直到整句结束，匹配最大长度
				matchPosTmp = i + j;
				String keyTmp = str.substring(j, matchPosTmp);// 切出最大字符串
				if (sentimentDict.contains(keyTmp)) { // 判断当前字符串是否在词典中
					matchSentimentWord = keyTmp; // 如果在词典中匹配上了就赋值
					matchPos = matchPosTmp; // 同时保存好匹配位置
				}
				if (decorativeDict.contains(keyTmp)) { // 判断当前字符串是否在词典中
					matchDecorativeWord = keyTmp; // 如果在词典中匹配上了就赋值
					matchPos = matchPosTmp; // 同时保存好匹配位置
				}
				i++;
			}
			if (!matchSentimentWord.isEmpty()) {
				// 有匹配结果就输出最大长度匹配字符串
				j = matchPos;
				// 保存位置，下次从当前位置继续往后截取
				System.out.print(matchSentimentWord + " ");
				sentimentResult = sentimentMap.get(matchSentimentWord);
			}
			if (!matchDecorativeWord.isEmpty()) {
				// 有匹配结果就输出最大长度匹配字符串
				j = matchPos;
				// 保存位置，下次从当前位置继续往后截取
				System.out.print(matchDecorativeWord + " ");
				decorativeWordList.add(matchDecorativeWord);
			} else {
				// 从当前词开始往后都没有能够匹配上的词，则按照单字切分的原则切分
				System.out.print(str.substring(j, ++j) + " ");
			}
			matchSentimentWord = "";
			matchDecorativeWord = "";
		}

		double totalScore = 1;
		for (String decorativeWord : decorativeWordList) {
			Double scoreTmp = decorativeScoreMap.get(decorativeWord);
			totalScore *= scoreTmp;
		}

		System.out.print("\r\n");
		if (totalScore > 0) {
			System.out.println("当前心情是：" + sentimentResult);
		} else {
			System.out.println("当前心情是：不" + sentimentResult);
		}
	}
}