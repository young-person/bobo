package com.bobo.feign;

/**
 * 服务名基类
 */
public interface FeignService {
    /**
     * webservice 服务
     */
    public static final String Service_catwebservice = "catwebservice";
    /**
     * catmain 业务主服务
     */
    public static final String Service_catmain = "catmain";
    /**
     * catupload 文件上传中转服务
     */
    public static final String Service_catupload ="catupload";
    /**
     * cattask 任务调度服务
     */
    public static final String Service_task = "cattask";
}
