/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.ansj;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

/**
 * 
 * <p>
 * TODO (用一句话描述该文件做什么)
 * </p>
 * 
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com
 * @date 2014年12月8日 下午2:54:43
 * 
 * @version V1.0
 * 
 */
public class ANSJTokenizerFactory extends TokenizerFactory implements ResourceLoaderAware {
	protected ANSJTokenizerFactory(Map<String, String> args) {
		super(args);
	}

	private ThreadLocal<ANSJTokenizer> tokenizerLocal = new ThreadLocal<ANSJTokenizer>();

	@Override
	public Tokenizer create(AttributeFactory factory, Reader input) {
		ANSJTokenizer tokenizer = tokenizerLocal.get();
		if (tokenizer == null) {
			tokenizer = newTokenizer(input);
		} else {
			try {
				tokenizer.setReader(input);
				tokenizer.reset();
			} catch (IOException e) {
				tokenizer = newTokenizer(input);
			}
		}

		return tokenizer;
	}

	@Override
	public void inform(ResourceLoader loader) throws IOException {

	}

	private ANSJTokenizer newTokenizer(Reader input) {
		ANSJTokenizer tokenizer = new ANSJTokenizer(input);
		tokenizerLocal.set(tokenizer);
		return tokenizer;
	}

}
