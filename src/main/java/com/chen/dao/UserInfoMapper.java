package com.chen.dao;

import org.springframework.stereotype.Repository;

import com.chen.pojo.UserInfoDO;

/**
 *  用户账户信息 dao层
 * @author chenyan
 *
 */
@Repository
public interface UserInfoMapper {

	UserInfoDO selectByUserId(Integer userId) ;

}
