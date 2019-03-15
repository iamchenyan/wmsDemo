package com.chen.security.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.chen.exception.UserInfoServiceException;
import com.chen.pojo.UserInfoDTO;
import com.chen.security.service.Interface.UserInfoService;

/** 
*  用户认证、授权realm
* @author 作者 chenyan
* @version 创建时间：2019年3月12日 下午4:22:13 
*/
public class UserAuthorizingRealm extends AuthorizingRealm {

	@Autowired
	private UserInfoService userInfoService ;
//	
//	@Autowired
//	private EncryptingModel encryptingModel ;
//	
//	@Autowired
//	private RepositoryAdminManageService reposityAdminManageService ;
//	
//	@Autowired
//	private SystemLogServie systemLogService ;

	/**
	 *  对用户进行角色授权
	 * @param principalCollection (用户信息)
	 * @return AuthorizationInfo (授权信息)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 存放用户角色
		Set<String> roles = new HashSet<>() ;
		// 获取用户角色
		Object principal = principalCollection.getPrimaryPrincipal() ;
		if(principal instanceof String) {
			String userId = (String)principal ;
			if(StringUtils.isNumeric(userId)) {
				try {
					UserInfoDTO userInfo = userInfoService.getUserInfo(Integer.valueOf(userId)) ;
					if(userInfo != null ) {
						// 设置用户角色
						roles.addAll(userInfo.getRole()) ;
					}
					
				} catch (UserInfoServiceException e) {
					// logger
				}
			}
		}
		
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		return null;
	}
	
	
	
	
	
}
