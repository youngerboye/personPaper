package com.personnel.mapper.jpa;

import com.personnel.core.base.BaseMapper;
import com.personnel.model.RoleMenu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleMenuRepository extends BaseMapper<RoleMenu,Integer> {

    @Transactional(rollbackFor = Exception.class)
    int deleteAllByRoleId(Integer roleId);
}
