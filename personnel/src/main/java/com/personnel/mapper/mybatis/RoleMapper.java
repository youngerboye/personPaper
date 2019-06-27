package com.personnel.mapper.mybatis;


import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.RoleOutput;
import com.personnel.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends MybatisBaseMapper<RoleOutput> {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    RoleOutput selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> findByUserId(@Param(value = "createdUserId") Integer createdUserId,@Param(value = "userId") Integer userId);

    List<RoleOutput> selectByDefaultPermissions();

    List<Role> findByUserIdNotIn(@Param(value = "createdUserId") Integer createdUserId, @Param(value = "userId")Integer userId);
}
