package com.personnel.mapper.jpa;

import com.personnel.core.base.BaseMapper;
import com.personnel.model.RoleAction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleActionRepository extends BaseMapper<RoleAction,Integer> {

    @Transactional(rollbackFor = Exception.class)
    int deleteAllByRoleId(Integer roleId);
}
