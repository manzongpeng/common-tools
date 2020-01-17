package com.example.demo.utils;

import com.jfinal.kit.HttpKit;

import java.util.Map;

/**
 * @Author: mzp
 * @License: (C) Copyright 2019-2099, TC Corporation Limited.
 * @Date: 2020/1/17 13:07
 * @Version: 1.0
 * @Description: http请求工具类
 */
public class HttpUtil {

    /*
     * HuTool工具类已经集成了一部分http请求工具类
     */

    /**
     * @param url       请求url
     * @param postParam 请求参数
     * @param data      请求数据
     * @param header    请求header
     * @return
     */
    public static String post(String url, Map<String, String> postParam, String data, Map<String, String> header) {
        return HttpKit.post(url, postParam, null, header);
    }

}
