/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.concurrent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 *    
 * <p>
 * TODO (用一句话描述该文件做什么)   
 *</p>
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com 
 * @date 2014年12月27日 下午1:36:23
 *
 * @version V1.0     
 *     
 */
public class LifecycleWebServer {
	private final ExecutorService exec = Executors.newFixedThreadPool(10);

	    public void start() throws IOException {
	        ServerSocket socket = new ServerSocket(80);
	        while (!exec.isShutdown()) {
	            try {
	                final Socket conn = socket.accept();
	                exec.execute(new Runnable() {
	                    public void run() { handleRequest(conn); }
	                });
	            } catch (RejectedExecutionException e) {
	                if (!exec.isShutdown())
	                    log("task submission rejected", e);
	            }
	        }
	    }

	    private void log(String string, RejectedExecutionException e) {
	        // TODO Auto-generated method stub
	        
        }

	public void stop() { exec.shutdown(); }

	    void handleRequest(Socket connection) {
//	        Request req = readRequest(connection);
//	        if (isShutdownRequest(req))
//	            stop();
//	        else
//	            dispatchRequest(req);
	    }

//	private Request readRequest(Socket connection) {
//	        // TODO Auto-generated method stub
//	        return null;
//        }
}
