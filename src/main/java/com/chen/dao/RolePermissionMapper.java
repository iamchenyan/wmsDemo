package com.chen.dao;

import org.springframework.stereotype.Repository;

import com.chen.pojo.RolePermissionDO;

import java.util.List;

/**
 *  角色权限信息 Mapper
 * @author chenyan
 *  2019年3月20日
 */
@Repository
public interface RolePermissionMapper {

    List<RolePermissionDO> selectAll();
}
