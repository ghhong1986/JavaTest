package com.abc.hong.solr.check;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CheckIndex;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class IndexCheck {
	public static void main(String[] args) throws IOException, InterruptedException {
		checkIndex("/Users/honggh/software/solr/server/solr/ct0/data/index");
	}
	
	/**
	 * 最新的版本5.4才能执行该段代码
	 * @param indexFilePath
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void checkIndex(String indexFilePath) throws IOException, InterruptedException {
//		CheckIndex checkIndex = new CheckIndex(FSDirectory.open(Paths.get(indexFilePath)));
//		checkIndex.setInfoStream(System.out);
//		CheckIndex.Status status = checkIndex.checkIndex();
//		if (status.clean) {
//			System.out.println("Check Index successfully！");
//		} else {
//			// 产生索引中的某个文件之后再次测试
//			System.out.println("Starting repair index files...");
//			// 该方法会向索引中写入一个新的segments文件，但是并不会删除不被引用的文件，除非当你再次打开IndexWriter才会移除不被引用的文件
//			// 该方法会移除所有存在错误段中的Document索引文件
//			checkIndex.exorciseIndex(status);
//			checkIndex.close();
//			// 测试修复完毕之后索引是否能够打开
//			IndexWriter indexWriter = new IndexWriter(FSDirectory.open(Paths.get(indexFilePath)),
//					new IndexWriterConfig(new StandardAnalyzer()));
//			System.out.println(indexWriter.isOpen());
//			indexWriter.close();
//		}
	}
}
