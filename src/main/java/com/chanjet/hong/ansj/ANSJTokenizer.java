/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.ansj;

import java.io.IOException;
import java.io.Reader;

import org.ansj.domain.Term;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com
 * @date 2014年12月8日 下午2:49:17
 * 
 * @version V1.0
 * 
 */
public class ANSJTokenizer extends Tokenizer {
	Analysis udf;
	private CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;
	private TypeAttribute typeAtt;

	protected ANSJTokenizer(Reader input) {
		super(input);
		termAtt = (CharTermAttribute) addAttribute(CharTermAttribute.class);
		offsetAtt = (OffsetAttribute) addAttribute(OffsetAttribute.class);
		typeAtt = (TypeAttribute) addAttribute(TypeAttribute.class);
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		udf = new ToAnalysis(input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		Term term = udf.next();
		if (term != null) {
			termAtt.copyBuffer(term.getName().toCharArray(), 0, term.getName().length());
			offsetAtt.setOffset(term.getOffe(), term.getOffe());
			typeAtt.setType("word");
			return true;
		} else {
			end();
			return false;
		}
	}

}
