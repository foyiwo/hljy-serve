package com.mall.web.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolLocal {

    //多线程查询
    private static ExecutorService executorService;

    public static ExecutorService  newFixedThreadPool30(){

        if(executorService == null || executorService.isShutdown()){
            executorService = Executors.newFixedThreadPool(30);
        }
        return executorService;

    }



}
