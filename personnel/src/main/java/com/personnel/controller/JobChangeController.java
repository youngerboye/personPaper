package com.personnel.controller;

import com.common.model.PageData;
import com.common.response.ResponseResult;
import com.personnel.constrant.ResponseCode;
import com.personnel.core.base.BaseController;
import com.personnel.core.base.BaseService;
import com.personnel.domain.input.EmployeesInput;
import com.personnel.domain.output.JobChangeOutput;
import com.personnel.model.Employees;
import com.personnel.model.JobChange;
import com.personnel.service.EmployeesService;
import com.personnel.service.JobChangeService;
import com.personnel.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping(value = "/jobChange")
@Api(value = "岗位变动controller",description = "岗位变动操作",tags = {"岗位变动操作接口"})
public class JobChangeController extends BaseController<JobChangeOutput, JobChange,Integer> {

    @Autowired
    private JobChangeService jobChangeService;

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private UserService userService;

    @Override
    public BaseService<JobChangeOutput,JobChange, Integer> getService() {
        return jobChangeService;
    }


    @Override
    @ApiOperation("新增或修改人员岗位信息")
    @RequestMapping(value = "form", method = RequestMethod.POST)
    public ResponseResult formPost(Integer id, @Validated @RequestBody @ApiParam(name="人员岗位变动信息",value="传入json格式",required=true) JobChange jobChange) throws Exception {
        if (jobChange.getEmployeeId() != null && !jobChange.getEmployeeId().equals("")) {
            Employees employees = employeesService.getById(jobChange.getEmployeeId());
            if (employees.getWorkingState() != 1) {
                return ResponseResult.error("不是入职状态的人员不能进行岗位变动");
            }
        }
        if (!employeesService.verificationOrg(jobChange.getNowOrganizationId(), null)) {
            return ResponseResult.error("必须将员工关联末级组织");
        }
        var list = jobChangeService.selectByEmployeeId(jobChange.getEmployeeId());
        if (id == null) {
            if (list.size() > 0) {
                return ResponseResult.error("申请人还有未审批的岗位调动");
            }
            var users = userService.selectByEmployeeId(jobChange.getEmployeeId());
            if (users == null) {
                return ResponseResult.error("申请人不能为空");
            }
            jobChange.setState(0);
            Integer result = jobChangeService.updateState(jobChange,new EmployeesInput());
            if(result<0){
                return ResponseResult.error("失败");
            }
            return ResponseResult.success("成功");
        }

        return super.formPost(id,jobChange);
    }

    @Override
    @GetMapping(value = "delete")
    @ApiOperation(value="删除一条人员岗位信息")
    public ResponseResult delete(String idList){
        if(idList==null){
            return ResponseResult.error(PARAM_EORRO);
        }
        ResponseResult deleteResult = null;
        try {
            deleteResult = super.delete(idList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(deleteResult.getCode()==ResponseCode.SUCCESS){
            return ResponseResult.success("删除岗位变动审批成功");

        }
        return ResponseResult.error("删除岗位变动失败");
    }

    @Override
    @GetMapping(value = "get")
    @ApiOperation(value="根据id获取单个人员岗位信息")
    public ResponseResult get(Integer id) {
        return super.selectById(id);
    }

    @GetMapping(value = "findPageList")
    @ApiImplicitParams({
            @ApiImplicitParam(name="employeeNo",value="员工工号",required=false,dataType="string", paramType = "query"),
            @ApiImplicitParam(name="name",value="员工姓名",required=false,dataType="string", paramType = "query")
    })
    public ResponseResult findPageList(HttpServletRequest request){
        PageData pageData = new PageData(request);
        if(jobChangeService.getUsers().getAdministratorLevel()!=9){
            pageData.put("userId",jobChangeService.getUsers().getId());
            if(jobChangeService.getUsers().getUserType()==1){
                pageData.put("orgId",jobChangeService.getUsers().getOrganizationId());
            }else{
                pageData.put("employeeId",jobChangeService.getUsers().getEmployeeId());
            }
        }
        return super.selectPageList(pageData);
    }

//    /**
//     * 改变人员岗位
//     *
//     * @param
//     * @return
//     */
//    @RequestMapping(value = "updateState", method = RequestMethod.POST)
//    public ResponseResult updateState(@RequestBody EmployeesInput employeesInput) throws Exception {
//
//        if(jobChangeService.updateState(employeesInput)<0){
//            ResponseResult.error("更新失败");
//        }
//        return ResponseResult.success();
//    }

}
