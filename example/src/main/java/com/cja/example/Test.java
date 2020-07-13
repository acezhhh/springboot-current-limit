package com.cja.example;


import java.util.concurrent.ConcurrentHashMap;

public class Test {



    public static void main(String[] args) {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        System.out.println(map.putIfAbsent("key",1));
        System.out.println(map.putIfAbsent("key",1));
    }

}
