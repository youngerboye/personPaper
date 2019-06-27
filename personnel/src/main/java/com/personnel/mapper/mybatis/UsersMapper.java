package com.personnel.mapper.mybatis;

import com.common.model.PageData;
import com.github.pagehelper.Page;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.UsersOutput;
import com.personnel.model.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersMapper extends MybatisBaseMapper<UsersOutput> {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    @Override
    UsersOutput selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Page<UsersOutput> findByRoleId(PageData pageData);

    Page<UsersOutput> findByRoleIdNotIn(PageData pageData);


    List<UsersOutput> findByOrganAndJob(PageData pageData);

    Page<UsersOutput> selectPageListWithinAuthority(PageData pageData);

    int updateEmployeeState(PageData pageData);


    //离职成功锁定用户状态
    int updateByUserId(@Param(value = "userId") Integer userId);

    UsersOutput selectByUserName(@Param("userName") String userName);

    UsersOutput selectByEmployeeId(@Param("employeeId") Integer employeeId);

    void updateOrgId(PageData pageData);

    List<UsersOutput> getByOrganCode(PageData pageData);

    int updatePassword(Users users);

}
