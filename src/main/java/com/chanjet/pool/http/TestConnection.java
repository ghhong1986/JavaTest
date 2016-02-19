package com.chanjet.pool.http;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestConnection {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testSimpleConnectionManager() throws InterruptedException, ExecutionException, IOException {
        HttpClientContext context = HttpClientContext.create();
        HttpClientConnectionManager connMrg = new BasicHttpClientConnectionManager();
        HttpRoute route = new HttpRoute(new HttpHost("localhost", 80));
        // Request new connection. This can be a long process
        ConnectionRequest connRequest = connMrg.requestConnection(route, null);
        // Wait for connection up to 10 sec
        HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
        try {
            // If not open
            if (!conn.isOpen()) {
                // establish connection based on its route info
                connMrg.connect(conn, route, 1000, context);
                // and mark it as route complete
                connMrg.routeComplete(conn, route, context);
            }
            // Do useful things with the connection.
        } finally {
            connMrg.releaseConnection(conn, null, 1, TimeUnit.MINUTES);
        }
    }
    
    @Test
    public void testPoolConnectionManager() throws IOException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
        // Increase max connections for localhost:80 to 50
        HttpHost localhost = new HttpHost("locahost", 80);
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        // do useful thing ...
        httpClient.close();
    }
    
    
    @Test
    public void testPoolConnectionMultiThread() throws InterruptedException{
        
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        // URIs to perform GETs on
        String[] urisToGet = {
            "http://www.domain1.com/",
            "http://www.domain2.com/",
            "http://www.domain3.com/",
            "http://www.domain4.com/"
        };

        // create a thread for each URI
        GetThread[] threads = new GetThread[urisToGet.length];
        for (int i = 0; i < threads.length; i++) {
            HttpGet httpget = new HttpGet(urisToGet[i]);
            threads[i] = new GetThread(httpClient, httpget);
        }

        // start the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }

        // join the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }

    }
    
    
    ///Connection eviction policy 
    
    
    // Connection keep alive strategy
    public void setKeepliveStrategy(){
        
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch(NumberFormatException ignore) {
                        }
                    }
                }
                HttpHost target = (HttpHost) context.getAttribute(
                        HttpClientContext.HTTP_TARGET_HOST);
                if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
                    // Keep alive for 5 seconds only
                    return 5 * 1000;
                } else {
                    // otherwise keep alive for 30 seconds
                    return 30 * 1000;
                }
            }

        };
        //线程安全
        CloseableHttpClient client = HttpClients.custom()
                .setKeepAliveStrategy(myStrategy)
                .build();
    }
    
    //proxy  代理服务器
    @Test
    public void testClientWithProxy() throws ClientProtocolException, IOException, KeyStoreException, KeyManagementException, NoSuchAlgorithmException{
        
        ConnectionSocketFactory plainsf =  PlainConnectionSocketFactory.getSocketFactory();
        
     KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
        @SuppressWarnings("deprecation")
        SSLContext sslContext =
                SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new TrustStrategy() {

                    @Override
                    public boolean isTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {
                        return true;
                    }
                }).build();
        
        

        
        /*  
        LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  */
        
        // Trust own CA and all self-signed certs
//        SSLContext sslcontext = SSLContexts.custom()
//                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//                .loadKeyMaterial(trustStore, trustStorePassword.toCharArray())
//                .useTLS()
//                .build();

        // Allow TLSv1 protocol only
        @SuppressWarnings("deprecation")
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                new String[] { "TLSv1" }, // supported protocols
                null,  // supported cipher suites
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        

        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        
        HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
        
        HttpHost proxy = new HttpHost("localhost", 1080);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRoutePlanner(routePlanner)
                .setConnectionManager(cm)
//                .setSSLSocketFactory(sslsf)
                .build();
        
        //// get google 
        HttpGet httpget = new HttpGet("https://www.google.com/?gws_rd=ssl#safe=strict&q=%E5%A4%A9%E6%B0%94");
        
        CloseableHttpResponse response = httpclient.execute(
                httpget, HttpClientContext.create());
        
        byte data[] = EntityUtils.toByteArray(response.getEntity());
        System.out.println(new String(data));
        
        response.close();
        
        httpclient.close();
        
    }
    
    
    
    static class GetThread extends Thread {

        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;

        public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
            this.httpClient = httpClient;
            this.context = HttpClientContext.create();
            this.httpget = httpget;
        }

        @Override
        public void run() {
            try {
                CloseableHttpResponse response = httpClient.execute(
                        httpget, context);
                try {
                    HttpEntity entity = response.getEntity();
                } finally {
                    response.close();
                }
            } catch (ClientProtocolException ex) {
                // Handle protocol errors
            } catch (IOException ex) {
                // Handle I/O errors
            }
        }

    }
    
    
    

}
