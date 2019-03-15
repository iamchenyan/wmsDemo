package com.chen.security.service.Interface;

import java.util.Map;
import com.chen.exception.UserAccountServiceException;

/** 
*  账号相关服务
* @author 作者 chenyan
* @version 创建时间：2019年3月12日 下午4:40:15 
*/
public interface AccountService {

	/**
	 * 密码更改
	 * @param userID 用户ID
	 * @param passwordInfo 更改的密码信息
	 */
	public void passwordModify(Integer userID, Map<String, Object> passwordInfo) throws UserAccountServiceException;
}
