package com.chanjet.hong.solr.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.DefaultQueryParser;
import org.springframework.data.solr.core.QueryParser;
import org.springframework.data.solr.core.QueryParsers;
import org.springframework.data.solr.core.TermsQueryParser;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.Query.Operator;
import org.springframework.data.solr.core.query.SimpleQuery;

public class TestQueryParser {
    public static void main(String[] args) {
        Query query =
                new SimpleQuery(new Criteria("field_1").is("value_1").and("field_2")
                        .startsWith("value_2")).addProjectionOnFields("asd").setPageRequest(
                        new PageRequest(0, 10)); // .addProjection("field_3")
      //  query.addSort(new Sort());
        query.addCriteria(new Criteria("sdf").between(12, 30).and("ss").is("hong"));
        QueryParsers parsers = new QueryParsers();
        QueryParser parse = parsers.getForClass(SimpleQuery.class);
        // QueryParser parse = new DefaultQueryParser();
        query.setDefaultOperator(Operator.OR);
        parse.constructSolrQuery(query);
        System.out.println(query.toString());
        /*
         * TermsQueryParser tqp = new TermsQueryParser(); tqp.constructSolrQuery(query);
         * System.out.println(tqp.getQueryString(query));
         */
      //  query.getFilterQueries()
    }
}
