package com.chen.security.service.Interface;

import java.util.List;
import java.util.Set;

import com.chen.exception.UserInfoServiceException;
import com.chen.pojo.UserInfoDTO;

/** 
*  用户信息 service层接口
* @author 作者 chenyan
* @version 创建时间：2019年3月12日 下午4:40:15 
*/
public interface UserInfoService {

	/**
	 *  指定 userId获取对应用户信息
	 * @param userId(用户ID)
	 * @return UserInfoDTO(用户账户信息)
	 * @throws UserInfoServiceException
	 */
	UserInfoDTO getUserInfo(Integer userId) throws UserInfoServiceException ;
	
	/**
	 *  指定 userName获取对应用户信息
	 * @param userName(用户名)
	 * @return UserInfoDTO(用户账户信息)
	 * @throws UserInfoServiceException
	 */
	UserInfoDTO getUserInfo(String userName) throws UserInfoServiceException ;
	
	/**
	 *  获取所有用户信息
	 * @return List<UserInfoDTO>(所有用户)
	 * @throws UserInfoServiceException
	 */
	List<UserInfoDTO> getAllUserInfo() throws UserInfoServiceException ;
	
	/**
	 *  获取用户的权限角色
	 * @param userId(用户id)
	 * @return Set<String>(用户角色信息，若无任何角色，则返回空 Set)
	 * @throws UserInfoServiceException
	 */
	Set<String> getUserRoles(Integer userId) throws UserInfoServiceException ;
	
	/**
	 * 	更新用户信息
	 * @param userInfoDTO 用户帐户信息
	 * @throws UserInfoServiceException
	 */
	void updateUserInfo(UserInfoDTO userInfoDTO) throws UserInfoServiceException;

	/**
	 *  删除指定用户
	 * @param userID 指定用户唯一 ID
	 * @throws UserInfoServiceException
	 */
	void deleteUserInfo(Integer userID) throws UserInfoServiceException;
	
	/**
	 *  添加一条用户信息
	 * @param userInfoDTO 用户信息对象
	 * @return
	 * @throws UserInfoServiceException
	 */
	boolean insertUserInfo(UserInfoDTO userInfoDTO) throws UserInfoServiceException;
}
