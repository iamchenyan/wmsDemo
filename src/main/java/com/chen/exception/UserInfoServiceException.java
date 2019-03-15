package com.chen.exception;

/** 
* 
* @author 作者 chenyan
* @version 创建时间：2019年3月12日 下午4:43:20 
*/
public class UserInfoServiceException extends BusinessException{

	public UserInfoServiceException(){
        super();
    }

    public UserInfoServiceException(Exception e){
        super(e);
    }

    public UserInfoServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }

    public UserInfoServiceException(String exceptionDesc){
        super(exceptionDesc);
    }
    
}
