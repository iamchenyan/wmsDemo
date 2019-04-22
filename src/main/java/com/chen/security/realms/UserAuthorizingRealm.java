package com.chen.security.realms;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.chen.common.service.Interface.RepositoryAdminManageService;
import com.chen.common.service.Interface.SystemLogService;
import com.chen.exception.RepositoryAdminManageServiceException;
import com.chen.exception.UserInfoServiceException;
import com.chen.pojo.RepositoryAdmin;
import com.chen.pojo.UserInfoDTO;
import com.chen.security.service.Interface.UserInfoService;
import com.chen.security.util.EncryptingModel;

/** 
*  用户认证、授权realm
* @author 作者 chenyan
* @version 创建时间：2019年3月12日 下午4:22:13 
*/
public class UserAuthorizingRealm extends AuthorizingRealm {

	@Autowired
	private UserInfoService userInfoService ;
	
	@Autowired
	private EncryptingModel encryptingModel ;
	
	@Autowired
	private RepositoryAdminManageService repositoryAdminManageService ;
	
	@Autowired
	private SystemLogService systemLogService ;

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
		return new SimpleAuthorizationInfo(roles);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		String realmName = getName() ;
		String credentials = "" ;
		
		// 获取用户名对应的用户账户信息
		try { 
			UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken ;
			String principal = usernamePasswordToken.getUsername() ;
			
			if(!StringUtils.isNumeric(principal)) {
				throw new AuthenticationException() ;
			}
			Integer userId = Integer.valueOf(principal) ;
			// 依赖于 / security.service.Interface.UserInfoService,UserInfoDTO 中包含用户id,用户名，密码，角色
			// wms_user表
			UserInfoDTO userInfoDTO = userInfoService.getUserInfo(userId) ;
			
			if(userInfoDTO != null) {
				Subject currentSubject = SecurityUtils.getSubject() ;
				Session session = currentSubject.getSession() ;
				
				// 设置部分用户信息到 session
				session.setAttribute("userId", userId) ;
				session.setAttribute("userName", userInfoDTO.getUserName()) ;
				// 获取该用户的所属仓库
				List<RepositoryAdmin> repositoryAdmin = (List<RepositoryAdmin>)repositoryAdminManageService.selectByID(userInfoDTO.getUserID()).get("data") ;
				session.setAttribute("repositoryBelong", (repositoryAdmin.isEmpty()) ? "none":repositoryAdmin.get(0).getRepositoryBelongID()) ;
				
				// 结合验证码对密码进行处理
				String checkCode = (String)session.getAttribute("checkCode") ;
				String password = userInfoDTO.getPassword() ;
				if(checkCode != null && password != null) {
					checkCode = checkCode.toUpperCase() ;
					credentials = encryptingModel.MD5(password + checkCode) ;
				}
			}
			// 比对账号密码
			// principal 前端传来 userId
			// credentials 为数据库的密码，加 checkCode 的MD5加密
			// realmName 为 com.chen.security.realms.UserAuthorizingRealm_0
			return new SimpleAuthenticationInfo(principal, credentials, realmName) ;
			
		} catch (UserInfoServiceException | RepositoryAdminManageServiceException | NoSuchAlgorithmException e) {
			throw new AuthenticationException();
		}
	}
}
