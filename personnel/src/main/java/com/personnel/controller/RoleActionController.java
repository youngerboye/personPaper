package com.personnel.controller;


import com.common.response.ResponseResult;
import com.personnel.core.base.BaseController;
import com.personnel.core.base.BaseService;
import com.personnel.domain.input.PermissionsInput;
import com.personnel.domain.output.RoleActionOutput;
import com.personnel.model.RoleAction;
import com.personnel.service.RoleActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "roleAction")
@RestController
public class RoleActionController extends BaseController<RoleActionOutput, RoleAction,Integer> {



    @Autowired
    private RoleActionService roleActionService;

    @Override
    public BaseService<RoleActionOutput, RoleAction, Integer> getService() {
        return roleActionService;
    }

    @GetMapping(value = "getPermissions")
    public ResponseResult getPermissions(Integer roleId){
        if(roleId == null){
            return ResponseResult.error(PARAM_EORRO);
        }
        return ResponseResult.success(roleActionService.getPermissions(roleId));
    }

    @PostMapping(value = "savePermissions")
    public ResponseResult savePermissions(@RequestBody PermissionsInput permissionsInput){
        if(permissionsInput.getRoleId() == null){
            return ResponseResult.error(PARAM_EORRO);
        }


        var result = roleActionService.savePermissions(permissionsInput.getRoleId(),permissionsInput);
        if(result >= 0){
            return ResponseResult.success();
        }
        return ResponseResult.error(SYS_EORRO);
    }


}
