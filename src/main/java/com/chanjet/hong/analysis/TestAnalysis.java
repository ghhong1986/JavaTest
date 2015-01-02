/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.analysis;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 *    
 * <p>
 * TODO (用一句话描述该文件做什么)   
 *</p>
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com 
 * @date 2014年11月20日 下午7:35:27
 *
 * @version V1.0     
 *     
 */
@SuppressWarnings("all")
public class TestAnalysis {

	public static void main(String[] args) throws IOException {
		  String text="基于java语言开发的轻量级的中文分词工具包";  
		  text ="联合舰队 she's my wife  ";
	
	        //创建分词对象  
	        Analyzer anal=new IKAnalyzer(true);       
	        StringReader reader=new StringReader(text);  
	        //分词  
	        TokenStream ts=anal.tokenStream("", reader);  
	        
	        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
	        //遍历分词数据  
	        while(ts.incrementToken()){  
	            System.out.print(term.toString()+"|");  
	        }  
	        reader.close();  
	        
	        test2();
	}
	
	
	public static void test2() throws IOException{
		  String text2 ="http://repo.csp.chanapp.com/nexus/#nexus-search;classname~IKAnalyzer";
		  text2 ="China2";
        StringReader reader=new StringReader(text2);  
        
        Analyzer anal=new IKAnalyzer(true);       
        //分词  
        TokenStream ts=anal.tokenStream("", reader);  
        
        CharTermAttribute  term=ts.getAttribute(CharTermAttribute.class);  
        //遍历分词数据  
        while(ts.incrementToken()){  
            System.out.print(term.toString()+" ");  
        }  
        reader.close();  
        
        anal.close();
	}
	

}
