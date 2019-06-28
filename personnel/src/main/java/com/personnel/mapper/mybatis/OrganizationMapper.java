package com.personnel.mapper.mybatis;

import com.common.model.PageData;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.OrganizationOutput;
import com.personnel.model.Organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationMapper extends MybatisBaseMapper<OrganizationOutput> {
    int insert(Organization record);

    int insertSelective(Organization record);

    List<OrganizationOutput> getByName(Organization organization);

    List<OrganizationOutput> getByPath(@Param(value = "path") String path);

    List<OrganizationOutput> getByLikePath(@Param(value = "path") String path);

    int selectCountName(String name);


    Integer selectOrganizationIdOrganizationByName(String name);

    List<OrganizationOutput> selectByName(String name);

    //根据主键查询出组织
    OrganizationOutput selectOrNoByOrId(Integer orgaId);

    List<OrganizationOutput> getByNameWithinAuthority(PageData pageData);

    List<OrganizationOutput> selectAllOrg();

    String  selectMaxNo();

    List<OrganizationOutput> selectByNo(String organizationNo);


    OrganizationOutput selectByOrgaName(String organizationName);


    //查询组织编号是否是中心窗口下的
    Integer selectCheckCondition(String orgaNo);


    List<OrganizationOutput> selectOrganizationMobile(Integer organizationId);


    List<OrganizationOutput> selectOrganizationPath();

    List<OrganizationOutput> getByUserId(PageData pageData);

    List<OrganizationOutput> noIsRepeat(PageData pageData);

    List<OrganizationOutput> selectByParentId(Integer organizationId);



    List<OrganizationOutput> findInitDatas();


    List<OrganizationOutput> selectByLinkedId(Integer linkedId);

    String selectByRuleConfigId(Integer id);
}
