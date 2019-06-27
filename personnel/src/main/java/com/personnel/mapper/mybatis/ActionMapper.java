package com.personnel.mapper.mybatis;

import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.ActionOutput;
import com.personnel.model.Action;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionMapper extends MybatisBaseMapper<ActionOutput> {
    int deleteByPrimaryKey(Long id);

    int insert(Action record);

    int insertSelective(Action record);

    ActionOutput selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Action record);

    int updateByPrimaryKey(Action record);

    List<ActionOutput> findByRoles(List<Integer> ids);

    List<ActionOutput> findByRoleId(@Param(value = "roleId") Integer roleId);

    List<ActionOutput> selectAllTow();
}
