package com.personnel.mapper.jpa;

import com.personnel.core.base.BaseMapper;
import com.personnel.model.RoleAction;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleActionRepository extends BaseMapper<RoleAction,Integer> {
    int deleteAllByRoleId(Integer roleId);
}
