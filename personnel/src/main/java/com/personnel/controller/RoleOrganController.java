package com.personnel.controller;

import com.common.response.ResponseResult;
import com.personnel.core.base.BaseController;
import com.personnel.core.base.BaseService;
import com.personnel.domain.input.PermissionsInput;
import com.personnel.domain.output.RoleOrganOutput;
import com.personnel.model.RoleOrgan;
import com.personnel.service.RoleOrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("roleOrgan")
public class RoleOrganController extends BaseController<RoleOrganOutput, RoleOrgan,Integer> {

    @Autowired
    private RoleOrganService roleOrganService;

    @Override
    public BaseService<RoleOrganOutput, RoleOrgan, Integer> getService() {
        return roleOrganService;

    }

    @PostMapping(value = "savePermissions")
    public ResponseResult savePermissions(@RequestBody PermissionsInput permissionsInput){
        if(permissionsInput.getRoleId() == null){
            return ResponseResult.error(PARAM_EORRO);
        }


        var result = roleOrganService.savePermissions(permissionsInput.getRoleId(),permissionsInput);
        if(result >= 0){
            return ResponseResult.success();
        }
        return ResponseResult.error(SYS_EORRO);
    }


    @GetMapping(value = "getOrganByRole")
    public ResponseResult getOrganByRole(Integer roleId){
        if(roleId == null){
            return ResponseResult.error(PARAM_EORRO);
        }
        var result = roleOrganService.getOrganByRole(roleId);
        return ResponseResult.success(result);
    }
}
