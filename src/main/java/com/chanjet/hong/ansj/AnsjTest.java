/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.ansj;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.Graph;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年12月3日 下午9:46:52
 * 
 * @version V1.0
 * 
 */
@SuppressWarnings("all")
public class AnsjTest {

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
	public void testBasic() {
		List<Term> parse = BaseAnalysis.parse("让战士们过一个欢乐祥和的新春佳节。畅捷通信息技术有限公司 大数据");
		System.out.println(parse);
	}

	@Test
	public void testToAnalysis() {
		List<Term> parse = ToAnalysis.parse("让战士们过一个欢乐祥和的新春佳节.畅捷通信息技术有限公司  大数据");
		System.out.println(parse);
	}

	@Test
	public void testGetOffset() throws IOException {
		// Tokenizer tokenizer = new AnsjTokenizer(new
		// StringReader("天天向上，媒体打打。《回家真好》"), 0, true);

		Tokenizer tokenizer = new ANSJTokenizer(new StringReader("天天向上，媒体打打。《回家真好》"));
		tokenizer.reset();
		CharTermAttribute termAtt = tokenizer.addAttribute(CharTermAttribute.class);
		OffsetAttribute offsetAtt = tokenizer.addAttribute(OffsetAttribute.class);
		PositionIncrementAttribute positionIncrementAtt = tokenizer.addAttribute(PositionIncrementAttribute.class);

		while (tokenizer.incrementToken()) {
			System.out.print(new String(termAtt.toString()));
			System.out.print(offsetAtt.startOffset() + "-" + offsetAtt.endOffset() + "-");
			System.out.print(positionIncrementAtt.getPositionIncrement() + "/");
		}
		tokenizer.close();
	}

}
