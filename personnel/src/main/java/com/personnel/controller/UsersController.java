package com.personnel.controller;

import com.common.model.PageData;
import com.common.request.ServiceCall;
import com.common.response.ResponseResult;
import com.github.pagehelper.PageInfo;
import com.personnel.core.base.BaseController;
import com.personnel.core.base.BaseService;
import com.personnel.domain.output.UsersOutput;
import com.personnel.mapper.jpa.EmployeesRepository;
import com.personnel.model.Employees;
import com.personnel.model.Users;
import com.personnel.service.EmployeesService;
import com.personnel.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;


@RestController
@RequestMapping(value = "/users")
public class  UsersController extends BaseController<UsersOutput, Users,Integer> {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeesRepository employeesRepository;


    @Override
    public BaseService<UsersOutput,Users,Integer> getService() {
        return userService;
    }

    @Override
    @PostMapping(value = "form")
    public ResponseResult formPost(Integer id, @RequestBody Users users) throws Exception {
        if(users == null ){
            return ResponseResult.error(PARAM_EORRO);
        }
        if(!userService.verificationOrg(users)){
            return ResponseResult.error("必须将用户关联末级组织");
        }
        switch (users.getUserType()){
            case 0:
                if(StringUtils.isBlank(users.getUsername())){
                    return ResponseResult.error(PARAM_EORRO);
                }
                Employees employees = employeesRepository.findEmployeesByEmployeeNoAndWorkingState(users.getUsername(),1);
                if(employees==null){
                    return ResponseResult.error("请检查用户名输入");
                }
                users.setEmployeeId(employees.getId());
                users.setOrganizationId(employees.getOrganizationId());
                break;
            case 1:
                if(users.getOrganizationId() == null){
                    return ResponseResult.error(PARAM_EORRO);
                }
                users.setEmployeeId(null);
                break;
            default:
        }
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            users.setIsAccountNonExpired(0);
            users.setIsAccountNonLocked(0);
            users.setIsCredentialsNonExpired(0);
            users.setIsEnabled(0);
            users.setMembershipOrganizationId(0);
            users.setAdministratorLevel(1);
        Users users1=userService.getByUserName(users.getUsername());
        if(users1!=null){
            return ResponseResult.error("用户名重复");
        }
        return super.formPost(id,users);
    }

    @Override
    @GetMapping(value = "selectById")
    public ResponseResult selectById(Integer id){
        return super.selectById(id);
    }


    @GetMapping(value = "selectPageList")
    public ResponseResult selectPageList(HttpServletRequest request){
        PageData pageData = new PageData(request);
        return super.selectPageList(pageData);
    }

    @GetMapping(value = "selectPageListWithinAuthority")
    public ResponseResult selectPageListWithinAuthority(HttpServletRequest request){
        return ResponseResult.success(new PageInfo<>(userService.selectPageListWithinAuthority(new PageData(request))));
    }



    @GetMapping(value = "getByRoleId")
    public ResponseResult getByRoleId(Integer roleId, HttpServletRequest request){
        if(roleId == null){
            return ResponseResult.error(PARAM_EORRO);
        }
        PageData pageData = new PageData(request);
        pageData.put("roleId",roleId);
        return ResponseResult.success(new PageInfo<>(userService.getByRoleId(roleId,pageData)));
    }

    @Override
    @GetMapping(value = "delete")
    public ResponseResult delete(String idList) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        return super.delete(idList);
    }

    @GetMapping(value = "getByRoleIdNotIn")
    public ResponseResult getByRoleIdNotIn(Integer roleId, HttpServletRequest request){
        if(roleId == null){
            return ResponseResult.error(PARAM_EORRO);
        }
        PageData pageData = new PageData(request);
        pageData.put("roleId",roleId);
        return ResponseResult.success(new PageInfo<>(userService.getByRoleIdNotIn(roleId,pageData)));
    }


    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }



    @Override
    @GetMapping(value = "get")
//    @PreAuthorize("hasAuthority('YUUuuu')")
    public ResponseResult get(Integer id) {
        return super.selectById(id);
    }


    @GetMapping(value = "getOrganList")
    public ResponseResult getOrganList(HttpServletRequest request){
        RestTemplate restTemplate = new RestTemplate();
        PageData pageData = new PageData(request);
        ResponseResult response = ServiceCall.getResult(loadBalancerClient,restTemplate,"/organization/getAllWithinAuthority","personnel",pageData,request);

        return response;
    }


    @PostMapping(value = "sendVerificationCode")
    public ResponseResult sendVerificationCode(String userName, String phoneNo) throws Exception {
        if(userName==null||"".equals(userName)||phoneNo==null||"".equals(phoneNo)){
            return ResponseResult.error(PARAM_EORRO);
        }
        return userService.sendVerificationCode(userName,phoneNo);
    }

    //验证并修改密码
    @PostMapping(value = "vCodeAndChangePwd")
    public ResponseResult vCodeAndChangePwd(String userName, String password) throws Exception {
        if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
            return ResponseResult.error(PARAM_EORRO);
        }
        return  userService.vCodeAndChangePwd(userName,password);
    }



}
