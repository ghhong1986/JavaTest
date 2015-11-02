package com.chanjet.hong.tuple;

import java.util.List;

import org.javatuples.KeyValue;
import org.javatuples.Pair;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import com.google.common.collect.Lists;

public class TestTupleExample {
    public static void main(String[] args) {
        Pair<String,Integer> pair = Pair.with("hello", Integer.valueOf(23));
        pair = new Pair<String,Integer>("hello", Integer.valueOf(23));
        System.out.println(pair);
        
        KeyValue<String, Integer> kv = new KeyValue<String, Integer>("sdf", 1213);
        
        Quintet<String,Integer,Double,String,String> quintet = 
                Quintet.with("a", Integer.valueOf(3), Double.valueOf(34.2), "b", "c"); 
        quintet =   
                new Quintet<String,Integer,Double,String,String>("a", Integer.valueOf(3), Double.valueOf(34.2), "b", "c"); 
        System.out.println(quintet);
        
        List<? extends Object>  myCollectionWith5Elements = Lists.newArrayList("asd",12,123.4,"sd","wed");
        quintet = (Quintet<String, Integer, Double, String, String>) Quintet.fromCollection(myCollectionWith5Elements);
            
        Sextet<String, Integer, Double, String, String, Integer>  six =quintet.add(12321);
        System.out.println(six);
        
        List<String> testList = Lists.newArrayList("sadf","sdf","hong","guang","hua");
        String[]arr = new String[testList.size()] ;
        testList.toArray(arr);
        System.out.println(arr);
        
        
    }
}
