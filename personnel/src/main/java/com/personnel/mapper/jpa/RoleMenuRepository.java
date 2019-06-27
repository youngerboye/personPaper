package com.personnel.mapper.jpa;

import com.personnel.core.base.BaseMapper;
import com.personnel.model.RoleMenu;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuRepository extends BaseMapper<RoleMenu,Integer> {
    int deleteAllByRoleId(Integer roleId);
}
