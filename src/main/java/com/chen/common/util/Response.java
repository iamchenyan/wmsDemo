package com.chen.common.util;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

/** 
*  controller响应的载体（信息体对象）
* @author 作者 chenyan
* @version 创建时间：2019年3月6日 下午11:03:24 
*/
public class Response {
	
	public static final String RESPONSE_RESULT_SUCCESS = "success" ;
	public static final String RESPONSE_RESULT_ERROR = "error" ;
	
	// response 中可能使用的值
	private static final String RESPONSE_RESULT = "result" ;
	private static final String RESPONSE_MSG = "msg" ;
	private static final String RESPONSE_DATA = "data" ;
	private static final String RESPONSE_TOTAL = "total" ;
	
	// 存放响应中的信息
	private Map<String ,Object> responseContent ;
	
	// Constructor
	Response(){
		this.responseContent = new HashedMap(10) ;
	}
	
	/**
	 *  设置 response 的状态
	 * @param result response的状态，success or error
	 */
	public void setResponseResult(String result) {
		this.responseContent.put(Response.RESPONSE_RESULT, result) ;
	}
	
	/**
	 *  设置 response 的附加信息
	 * @param msg response附加信息
	 */
	public void setResponseMsg(String msg){
		this.responseContent.put(Response.RESPONSE_MSG, msg) ;
	}
	
	/**
	 *  设置 response中携带的数据
	 * @param data response 中携带的数据
	 */
	public void setResponseData(Object data) {
		this.responseContent.put(Response.RESPONSE_DATA, data) ;
	}
	
	/**
	 *  设置 response 中携带的数据的数量 与 RESPONSE_DATA 配合用
	 * @param total 数据数量
	 */
	public void setResponseTotal(long total) {
		this.responseContent.put(Response.RESPONSE_TOTAL, total) ;
	}
	
	/**
	 *  设置 response 自定义信息
	 * @param key 自定义 key
	 * @param value 自定义值
	 */
	public void setCustomerInfo(String key ,Object value) {
		this.responseContent.put(key, value) ;
	}
	
	/**
	 *  生成 response对象
	 * @return response 的 Map 对象
	 */
	public Map<String ,Object> generateResponse(){
		return this.responseContent ;
	}
}
