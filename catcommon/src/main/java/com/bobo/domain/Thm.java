package com.bobo.domain;

import java.util.concurrent.ConcurrentHashMap;

public class Thm extends Thread{


    public static void main(String[] args) {
        String key = "aaa";
        String value = "bbb";

        for(int i = 0; i < 30; i ++){
            Thm thm = new Thm(key,value);
            thm.setName("Threadname"+i);
            thm.start();
        }
    }

    private String key;
    private String value;

    public Thm(String key,String value){
        this.key = key;
        this.value = value;
    }
    @Override
    public void run() {
        super.run();
        String v = concurrentWorkIdMap.put(key, value);
        System.out.println(this.getName()+"------>"+v);
        try {
            if(null != v){
                while(true){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String o = concurrentWorkIdMap.put(key, value);
                    if(null == o){
                        break;
                    }
                }
            }
            try {
                Thread.sleep(3000);
                System.err.println(this.getName()+"------>"+v);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            concurrentWorkIdMap.remove(key);
        }
    }

    private static ConcurrentHashMap<String,String> concurrentWorkIdMap  = new ConcurrentHashMap<>();

}
