//package com.chanjet.hong.JavaTest;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.AtomicReaderContext;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.queries.CustomScoreProvider;
//import org.apache.lucene.queries.CustomScoreQuery;
//import org.apache.lucene.queries.function.FunctionQuery;
//import org.apache.lucene.queries.function.ValueSource;
//import org.apache.lucene.queryparser.surround.query.FieldsQuery;
//import org.apache.lucene.search.FieldCache;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.TopDocs;
///*import org.apache.lucene.search.function.CustomScoreProvider;
//import org.apache.lucene.search.function.CustomScoreQuery;
//import org.apache.lucene.search.function.FieldScoreQuery;
//import org.apache.lucene.search.function.FieldScoreQuery.Type;
//import org.apache.lucene.search.function.ValueSourceQuery;*/
//
////import com.lucene.search.IndexUtils;
//
//public class MyCustomScore {
//    public void searchByScoreQuery() {
//        try {
//            IndexSearcher searcher = new IndexSearcher(
//                    IndexReader.open(IndexUtils.getDirectory()));
//            Query q = new TermQuery(new Term("content", "1"));
//            // 1.创建评分域
//            FieldsQuery fieldScoreQuery = new FieldsQuery("size", Type.INT);
//            // 2.根据评分域和原有的query创建自定义的query对象
//            // 2.1 创建MyCustomScoreQuery继承CustomScoreQuery
//            // 2.2重写getCustomScoreProvider方法
//            // 2.3创建MyCustomScoreProvider继承CustomScoreProvider
//            // 2.4重写customScore方法,自定义评分算法
//            MyCustomScoreQuery query = new MyCustomScoreQuery(q,fieldScoreQuery);
//            TopDocs docs=searcher.search(query, 30);
//            // 输出信息
//            ScoreDoc[] sds = docs.scoreDocs;
//            Document d = null;
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            for (ScoreDoc s : sds) {
//                d = searcher.doc(s.doc);
//                System.out.println(s.doc + "->" + s.score + "->"
//                        + d.get("filename") + "->" + d.get("size") + "->"
//                        + sdf.format(new Date(Long.valueOf(d.get("date")))));
//            }
//
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//@SuppressWarnings("serial")
//class MyCustomScoreQuery extends CustomScoreQuery {
//
//    @Override
//    protected CustomScoreProvider getCustomScoreProvider(AtomicReaderContext context) throws IOException {
//	    // TODO Auto-generated method stub
//	    return super.getCustomScoreProvider(context);
//    }
//
//	public MyCustomScoreQuery(Query subQuery, FunctionQuery valSrcQuery) {
//        super(subQuery, valSrcQuery);
//    }
//
//   /* @Override
//    protected CustomScoreProvider getCustomScoreProvider(IndexReader reader)
//            throws IOException {
//        // 默认情况评分是根据原有的评分*传入进来的评分
//        //return super.getCustomScoreProvider(reader);
//        
//        return new MyCustomScoreProvider(reader);
//    }*/
//}
//
//class MyCustomScoreProvider extends CustomScoreProvider {
//
//    String[] filenames = null;
//
//    public MyCustomScoreProvider(AtomicReaderContext reader) {
//        super(reader);
//        try {
//            filenames = FieldCache.DEFAULT.getStrings(reader, "filename");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public float customScore(int doc, float subQueryScore, float valSrcScore)
//            throws IOException {
//        // 根据doc获取文件名
//        String filename = filenames[doc];
//        //判断文件后缀是否为txt结尾,是把评分*10  否则/10
//        if (filename.endsWith(".txt")) {
//            return subQueryScore * 10;
//        } else {
//            return subQueryScore / 10;
//        }
//    }
//}