package com.chanjet.hong.solr.query;

import java.io.IOException;


import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.SolrResponseBase;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;

public class TestSolrApi {
	
	@SuppressWarnings("all")
	static class PreemptiveAuthInterceptor implements HttpRequestInterceptor {

	    public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
	        AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

	        // If no auth scheme avaialble yet, try to initialize it
	        // preemptively
	        if (authState.getAuthScheme() == null) {
	            AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
	            CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
	            HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
	            if (authScheme != null) {
	                Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
	                if (creds == null) {
	                    throw new HttpException("No credentials for preemptive authentication");
	                }
	                authState.setAuthScheme(authScheme);
	                authState.setCredentials(creds);
	            }
	        }

	    }

	}
	
	
	static String user = "solr";
	static String passwd = "cjt@ai2016";
	static String url = "http://wtd001:6944/solr/test";
	public static void main(String[] args) throws SolrServerException, IOException {
//		test2();
//		test1();
//		test3();
		test4();
	}
	
	
	public static void test4() throws SolrServerException, IOException{
		SecurityHttpSolrClient client  = new SecurityHttpSolrClient(url);
	//	client.setAuth(user, passwd);
		client.deleteByQuery("_uid_:change.me");
		
//		UpdateRequest req = new UpdateRequest();
//	    req.deleteByQuery("_uid_:change.me");
//	    req.setCommitWithin(1000);
//	    req.setBasicAuthCredentials(user, passwd);
//	    
//	    NamedList<Object>  nameList = client.request(req);
//	    System.out.println(nameList);
//	    req.process(client);
		client.close();
		
	}
	
	public static void test1() throws SolrServerException, IOException{

		
		
		HttpSolrClient client  = new HttpSolrClient(url);
        client.setConnectionTimeout(100);  
        client.setDefaultMaxConnectionsPerHost(100);  
        client.setMaxTotalConnections(100);  

        HttpClientUtil.setBasicAuth((DefaultHttpClient) client.getHttpClient(), user, passwd);
        
        
        
        
        
//    	SolrParams qp = new SolrQuery("*:*");
//    	client.query(qp);
        client.deleteByQuery("id:test1");
        client.commit();
        client.close();
//		SolrRequest req = new SolrRequest<SolrResponse>;
	}
	
	
	public static void test3() throws SolrServerException, IOException{
		  CredentialsProvider credsProvider = new BasicCredentialsProvider();
	        credsProvider.setCredentials(AuthScope.ANY,
	                new UsernamePasswordCredentials(user, passwd));
	        CloseableHttpClient httpclient = HttpClients.custom()
	                .setDefaultCredentialsProvider(credsProvider)
	                .build();
	        
	    	HttpSolrClient client  = new HttpSolrClient(url,httpclient);
//	        client.setConnectionTimeout(100);  
//	        client.setDefaultMaxConnectionsPerHost(100);  
//	        client.setMaxTotalConnections(100); 
//	    	SolrParams qp = new SolrQuery("*:*");
//	    	client.query(qp);
	        client.deleteByQuery("id:test1");
	        client.commit();
	        client.close();
	        
	}
	
	
	
	public static void test2() throws SolrServerException, IOException{
		String url = "http://192.168.192.11:8080/solr/FormResponses";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		    httpclient.getCredentialsProvider().setCredentials(
		    AuthScope.ANY, new UsernamePasswordCredentials(user, passwd));
		HttpSolrServer solrServer = new HttpSolrServer(url, httpclient);
		
		
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
//		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		
		
//		httpclient.getCredentialsProvider().setCredentials(
//		                    AuthScope.ANY,
//		                    new UsernamePasswordCredentials(user, passwd));
//		HttpSolrServer solrServer = new HttpSolrServer(url, httpclient);
		SolrParams qp = new SolrQuery("*:*");
		solrServer.query(qp);

//		solrServer.deleteByQuery("id:test1");
		solrServer.commit();
	}

}
