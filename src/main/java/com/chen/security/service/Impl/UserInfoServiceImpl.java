package com.chen.security.service.Impl;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;

import com.chen.dao.UserInfoMapper;
import com.chen.dao.UserPermissionMapper;
import com.chen.exception.UserInfoServiceException;
import com.chen.pojo.RoleDO;
import com.chen.pojo.UserInfoDO;
import com.chen.pojo.UserInfoDTO;
import com.chen.security.service.Interface.UserInfoService;

/** 
*  账户信息 service (UserInfoService)实现
* @author 作者 chenyan
* @version 创建时间：2019年3月12日 下午5:05:40 
*/
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper ;
	
	@Autowired
	private UserPermissionMapper userPermissionMapper ;
	
	@Override
	public UserInfoDTO getUserInfo(Integer userId) throws UserInfoServiceException {
		if(userId == null) {
			return null ;
		}
		
		try {
			// 获取用户信息 (dao.UserInfoMapper )
			UserInfoDO userInfoDO = userInfoMapper.selectByUserId(userId) ;
			
			// 获取用户角色信息
			List<RoleDO> roles = userPermissionMapper.selectUserRole(userId) ;
			return assembleUserInfo(userInfoDO, roles) ;
		} catch (PersistenceException e) {
			throw new UserInfoServiceException(e) ;
		}
	}

	/**
	 *  组装 UserInfoDTO 对象，包括用户账户信息 和 角色信息
	 * @param userInfoDO(账户信息)
	 * @param roles(角色信息)
	 * @return UserInfoDTO(组装后的对象)
	 */
	private UserInfoDTO assembleUserInfo(UserInfoDO userInfoDO, List<RoleDO> roles) {
		UserInfoDTO userInfoDTO = null ;
		if (userInfoDO != null && roles != null) {
            userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserID(userInfoDO.getUserID());
            userInfoDTO.setUserName(userInfoDO.getUserName());
            userInfoDTO.setPassword(userInfoDO.getPassword());

            for (RoleDO role : roles) {
                userInfoDTO.getRole().add(role.getRoleName());
            }
        }
		return userInfoDTO;
	}

	@Override
	public UserInfoDTO getUserInfo(String userName) throws UserInfoServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfoDTO> getAllUserInfo() throws UserInfoServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getUserRoles(Integer userId) throws UserInfoServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
