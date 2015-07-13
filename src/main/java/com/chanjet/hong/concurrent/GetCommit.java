/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.concurrent;

import java.util.concurrent.Callable;

/**
 *    
 * <p>
 * TODO (用一句话描述该文件做什么)   
 *</p>
 * @author 洪光华 <br/>
 * @Email honggh@chanjet.com 
 * @date 2014年11月28日 下午3:41:01
 *
 * @version V1.0     
 *     
 */
public class GetCommit implements Callable<Commit> {

    private Repo repo;
    private String sha;

    public GetCommit(Repo repo, String sha) {
        this.repo = repo;
        this.sha = sha;
    }

    @Override
    public Commit call() throws Exception {
        return repo.getCommit(sha);
    }
}
