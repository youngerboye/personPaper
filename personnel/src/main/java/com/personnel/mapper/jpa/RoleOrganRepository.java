package com.personnel.mapper.jpa;


import com.personnel.core.base.BaseMapper;
import com.personnel.model.RoleOrgan;

import java.util.List;


public interface RoleOrganRepository extends BaseMapper<RoleOrgan,Integer> {
    List<RoleOrgan> findAllByRoleId(Integer roleId);

    int deleteAllByRoleId(Integer roleId);
}
