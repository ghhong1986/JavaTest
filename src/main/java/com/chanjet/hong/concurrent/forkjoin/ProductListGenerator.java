/* 
 *  智能平台
 *
 * 版本信息：2016年3月22日    
 *
 * Copyright 畅捷通信息技术股份有限公司  @ 2016 版权所有    
 *    
 */
package com.chanjet.hong.concurrent.forkjoin;

import java.util.ArrayList;
import java.util.List;

public class ProductListGenerator {
    public List<Product> generate(int size) {  
        List<Product> list = new ArrayList<Product>();  
        for(int i = 0 ; i < size; i++) {  
            Product product = new Product();  
            product.setName("Product" + i);  
            product.setPrice(10);  
            list.add(product);  
        }  
        return list;  
    }
}
