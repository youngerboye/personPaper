package com.personnel.mapper.mybatis;

import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.MenuOutput;
import com.personnel.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends MybatisBaseMapper<MenuOutput> {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    MenuOutput selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<MenuOutput> findByRoles(List<Integer> ids);

    List<MenuOutput> findAll();

    List<MenuOutput> findByParentId(@Param(value = "parentId") Integer parentId);

    List<MenuOutput> selectAllToW();
}
