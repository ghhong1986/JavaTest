package com.chanjet.hong.solr.query;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.ResponseParser;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.util.NamedList;

@SuppressWarnings("all")
public class SecurityHttpSolrClient extends HttpSolrClient {

	public SecurityHttpSolrClient(String baseURL) {
		super(baseURL);
	}

	public SecurityHttpSolrClient(String baseURL, HttpClient client) {
		super(baseURL, client);
	}

	public SecurityHttpSolrClient(String baseURL, HttpClient client, ResponseParser parser) {
		super(baseURL, client, parser);
	}

	public void setAuth(String user, String password){
		this.user = user;
		this.password = password;
	}
	
	private static final long serialVersionUID = 1L;
	
	private void setAuth(SolrRequest request){
		request.setBasicAuthCredentials(user, password);
	}
	
	/**
	 * 用户名
	 */
	private String user;
	/**
	 * 密码
	 */
	private String password;

	@Override
	public NamedList<Object> request(SolrRequest request, String collection) throws SolrServerException, IOException {
		setAuth(request);
		return super.request(request, collection);
	}

	@Override
	public NamedList<Object> request(SolrRequest request, ResponseParser processor)
			throws SolrServerException, IOException {
		setAuth(request);
		return super.request(request, processor);
	}

	@Override
	public NamedList<Object> request(SolrRequest request, ResponseParser processor, String collection)
			throws SolrServerException, IOException {
		setAuth(request);
		return super.request(request, processor, collection);
	}
	
	

}
