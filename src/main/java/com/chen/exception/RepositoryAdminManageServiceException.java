package com.chen.exception;

/**
 *  RepositoryAdminManageService异常
 * @author chenyan
 *  2019年4月21日
 */
public class RepositoryAdminManageServiceException extends BusinessException {

    public RepositoryAdminManageServiceException(){
        super();
    }

    public RepositoryAdminManageServiceException(Exception e){
        super(e);
    }

    public RepositoryAdminManageServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }

    public RepositoryAdminManageServiceException(String exceptionDesc){
        super(exceptionDesc);
    }
}
