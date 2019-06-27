package com.personnel.service;

import com.personnel.core.base.BaseMapper;
import com.personnel.core.base.BaseService;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.UserRoleOutput;
import com.personnel.mapper.jpa.UserRoleRepository;
import com.personnel.mapper.mybatis.UserRoleMapper;
import com.personnel.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleService extends BaseService<UserRoleOutput, UserRole,Integer> {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public BaseMapper<UserRole, Integer> getMapper() {
        return userRoleRepository;
    }

    @Override
    public MybatisBaseMapper<UserRoleOutput> getMybatisBaseMapper() {
        return userRoleMapper;
    }


    public List<UserRole> findByUserId(Integer userId){
        var a = userRoleRepository.findAllByUserId(userId);
        return a;
    }


    @Transactional
    public int deleteByUserIdANdRoleId(Integer userId,Integer roleId){
        return userRoleRepository.deleteAllByUserIdAndRoleId(userId,roleId);
    }


    @Transactional
    public int deleteByRoleId(Integer roleId){
        return userRoleRepository.deleteByRoleId(roleId);
    }



}
