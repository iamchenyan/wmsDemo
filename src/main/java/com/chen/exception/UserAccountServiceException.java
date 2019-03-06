package com.chen.exception;

/**
 *  账户相关 异常管理
 * @author chenyan
 *  2019年3月6日
 */
public class UserAccountServiceException extends BusinessException {

    public static final String PASSWORD_ERROR = "passwordError";
    public static final String PASSWORD_UNMATCH = "passwordUnmatched";

    public UserAccountServiceException() {
        super();
    }

    public UserAccountServiceException(String exceptionDesc) {
        super(null, exceptionDesc);
    }

    UserAccountServiceException(Exception e) {
        super(e);
    }

    UserAccountServiceException(Exception e, String exceptionDesc) {
        super(e, exceptionDesc);
    }

}
