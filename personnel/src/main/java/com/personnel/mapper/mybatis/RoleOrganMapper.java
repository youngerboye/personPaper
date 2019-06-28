package com.personnel.mapper.mybatis;

import com.common.model.PageData;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.OrganPermissterOutput;
import com.personnel.domain.output.RoleOrganOutput;
import com.personnel.model.RoleOrgan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleOrganMapper extends MybatisBaseMapper<RoleOrganOutput> {
    int deleteByPrimaryKey(Long id);

    int insert(RoleOrgan record);

    int insertSelective(RoleOrgan record);

    RoleOrganOutput selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleOrgan record);

    int updateByPrimaryKey(RoleOrgan record);

    List<OrganPermissterOutput> findOrganByuserId(PageData pageData);

    List<OrganPermissterOutput> findAllOrgan();
}
