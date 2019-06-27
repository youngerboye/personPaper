package com.personnel.mapper.mybatis;


import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.MenuOutput;
import com.personnel.domain.output.RoleActionOutput;
import com.personnel.model.RoleAction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleActionMapper extends MybatisBaseMapper<RoleActionOutput> {
    int deleteByPrimaryKey(Long id);

    int insert(RoleAction record);

    int insertSelective(RoleAction record);

    RoleActionOutput selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleAction record);

    int updateByPrimaryKey(RoleAction record);

    List<RoleActionOutput> findByRoleId(@Param(value = "roleId") Integer roleId);

    List<RoleActionOutput> findByRoleIdIn(List<Integer> ids);

    List<MenuOutput> findMenuByRoleId(@Param(value = "roleId") Integer roleId);

    List<MenuOutput> findMenuByRoleIdIn(List<Integer> ids);
}
