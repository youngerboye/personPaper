package com.personnel.mapper.jpa;

import com.personnel.core.base.BaseMapper;
import com.personnel.model.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends BaseMapper<UserRole,Integer> {

    List<UserRole> findAllByUserId(Integer userId);


    int deleteAllByUserIdAndRoleId(Integer userId, Integer roleId);

    int deleteByRoleId(Integer roleId);
}
