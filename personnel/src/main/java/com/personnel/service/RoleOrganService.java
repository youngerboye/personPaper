package com.personnel.service;

import com.common.model.PageData;
import com.personnel.core.base.BaseMapper;
import com.personnel.core.base.BaseService;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.input.PermissionsInput;
import com.personnel.domain.output.OrganPermissterOutput;
import com.personnel.domain.output.RoleOrganOutput;
import com.personnel.mapper.jpa.RoleOrganRepository;
import com.personnel.mapper.mybatis.OrganizationMapper;
import com.personnel.mapper.mybatis.RoleOrganMapper;
import com.personnel.model.Organization;
import com.personnel.model.RoleOrgan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RoleOrganService extends BaseService<RoleOrganOutput, RoleOrgan,Integer> {

    @Autowired
    private RoleOrganRepository roleOrganRepository;

    @Autowired
    private RoleOrganMapper roleOrganMapper;

    @Autowired
    private OrganizationMapper organizationMapper;


    @Override
    public BaseMapper getMapper() {
        return roleOrganRepository;
    }

    @Override
    public MybatisBaseMapper getMybatisBaseMapper() {
        return roleOrganMapper;
    }

    public List<OrganPermissterOutput> getOrganByRole(Integer roleId) {
        List<OrganPermissterOutput> currentOrganList = null;
        if(getUsers().getAdministratorLevel() != 9){
            PageData pageData = new PageData();
            pageData.put("userId",getUsers().getId());
            if(getUsers().getUserType()==1){//部门账号为部门本身和部门账号权限内的组织
                Organization organizationOutput = organizationMapper.selectOrNoByOrId(getUsers().getOrganizationId());
                pageData.put("path",organizationOutput.getPath());
                pageData.put("linkedId",organizationOutput.getId());
            }
            currentOrganList = roleOrganMapper.findOrganByuserId(pageData);
        }else {
            currentOrganList = roleOrganMapper.findAllOrgan();
        }
        if(currentOrganList == null || currentOrganList.size() <= 0 ){
            return null;
        }
        var roleOrgans = roleOrganRepository.findAllByRoleId(roleId);
        if(roleOrgans != null && roleOrgans.size() > 0){
            for(var ro : roleOrgans){
                for(var co : currentOrganList){
                    if(co.getId().equals(ro.getOrganId())){
                        co.setCheckState(1);
                        continue;
                    }
                }
            }
        }

        var out = getOrganOutput(currentOrganList,0);

        return out;



    }

    private List<OrganPermissterOutput> getOrganOutput(List<OrganPermissterOutput> list,Integer parentId){
        List<OrganPermissterOutput> parents = new ArrayList<>();
        var firstList = list.stream().filter(OrganPermissterOutput -> OrganPermissterOutput.getParentId().equals(parentId)).collect(toList());
        if(firstList == null || firstList.size() <= 0){
            return null;
        }
        parents.addAll(firstList);
        for(var organ : parents){
            var l = getOrganOutput(list,organ.getId());
            if(l == null){
                continue;
            }
            organ.setChildren(l);
        }

        return parents;
    }

    @Transactional(rollbackFor = Exception.class)
    public int savePermissions(Integer roleId, PermissionsInput permissionsInput) {
        roleOrganRepository.deleteAllByRoleId(roleId);
        if(permissionsInput == null || CollectionUtils.isEmpty(permissionsInput.getRoleMenuInputs())){
            return SUCCESS;
        }
        List<RoleOrgan> list = new ArrayList<>();
        for(var o : permissionsInput.getRoleMenuInputs()){
            RoleOrgan roleOrgan = new RoleOrgan();
            roleOrgan.setOrganId(o.getId());
            roleOrgan.setRoleId(roleId);
            list.add(roleOrgan);
        }
        var result = roleOrganRepository.saveAll(list);
        if(result == null || result.size() <= 0){
            return ERROR;
        }else{
            return SUCCESS;
        }
    }

    @Async
    public void deleteRoleOrgan(Integer roleId){
        roleOrganRepository.deleteAllByRoleId(roleId);
    }
}
