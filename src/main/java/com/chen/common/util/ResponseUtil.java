package com.chen.common.util;

import org.springframework.stereotype.Component;

/** 
* 
* @author 作者 chenyan
* @version 创建时间：2019年3月6日 下午11:01:51 
*/
@Component
public class ResponseUtil {

	/**
     * 获得一个 Response 对象
     * @return response 对象
     */
    public Response getResponseInstance(){
        Response response = new Response();
        return response;
    }
    
}
