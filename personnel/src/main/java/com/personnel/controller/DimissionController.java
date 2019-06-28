package com.personnel.controller;

import com.common.Enum.ApprovalTypeEnum;
import com.common.Enum.AttachmentEnum;
import com.common.model.PageData;
import com.common.request.Audit;
import com.common.request.ServiceCall;
import com.common.response.ResponseResult;
import com.personnel.core.base.BaseController;
import com.personnel.core.base.BaseService;
import com.personnel.domain.input.EmployeesInput;
import com.personnel.domain.output.DimissionOutput;
import com.personnel.domain.output.EmployeesOutput;
import com.personnel.model.Attachment;
import com.personnel.model.Dimission;
import com.personnel.model.Employees;
import com.personnel.service.DimissionService;
import com.personnel.service.EmployeesService;
import com.personnel.service.OrganizationService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/dimission")
@Api(value = "离职申请controller",description = "离职申请操作",tags = {"离职申请操作接口"})
public class DimissionController extends BaseController<DimissionOutput, Dimission,Integer> {

    @Autowired
    private DimissionService dimissionService;

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public BaseService<DimissionOutput, Dimission, Integer> getService() {
        return dimissionService;
    }

    @Override
    @RequestMapping(value = "form", method = RequestMethod.POST)
    public ResponseResult formPost(Integer id, @Validated @RequestBody Dimission dimission) throws Exception {
        if(dimission == null){
            return ResponseResult.error(PARAM_EORRO);
        }
        if(dimission.getEmployeeId() == null){
            dimission.setEmployeeId(getService().getUsers().getEmployeeId());
        }
        if(organizationService.hasOrganManager(dimission.getEmployeeId())){
            return ResponseResult.error("您还是部门管理员，请移交工作后在申请离职");
        }

        int applyId=dimissionService.add(dimission);
        if(applyId>0){
            EmployeesOutput employeesOutput = employeesService.selectById(dimission.getEmployeeId());
            //设置离职时间
            employeesOutput.setDepartureDateTime(new Date());
            Employees employees = new Employees();
            BeanUtils.copyProperties(employeesOutput,employees);
            Integer result = employeesService.update(dimission.getEmployeeId(),employees);
            if(result<=0){
                return ResponseResult.error("离职失败");
            }
            EmployeesInput employeesInput = new EmployeesInput();
            employeesInput.setId(applyId);
            employeesInput.setState(1);
            updateState(employeesInput);
            return ResponseResult.success("离职成功");
        }else {
            return ResponseResult.error(SYS_EORRO);
        }
    }



    @Override
    @GetMapping(value = "get")
    public ResponseResult get(Integer id)  {
        if( id==null ){
            ResponseResult.error(PARAM_EORRO);
        }
        return ResponseResult.success(dimissionService.getById(id));
    }


    @GetMapping(value = "getByEmployeeId")
    public ResponseResult getByEmployeeId() throws Exception {
        if(getService().getUsers().getUserType()!=0){
            return ResponseResult.error("请登陆个人账号申请离职");
        }
        if(getService().getUsers().getAdministratorLevel()==9){
            return ResponseResult.error("请登陆个人账号申请离职");
        }
        Employees employees=employeesService.getById(this.getService().getUsers().getEmployeeId());
        if(employees.getWorkingState()!=1){
            return ResponseResult.error("不是在职状态不能离职");
        }
        if(dimissionService.selectByEmployeeId(this.getService().getUsers().getEmployeeId())!=null){
            return ResponseResult.error("您的离职申请正在审批中，请不要重复提交");
        }

        return ResponseResult.success();
    }

    /**
     * 改变人员状态
     *
     * @param
     * @return
     */
    @RequestMapping(value = "updateState", method = RequestMethod.POST)
    public ResponseResult updateState(@RequestBody EmployeesInput employeesInput) throws Exception {
        if(employeesInput.getId()==null||employeesInput.getId().equals("")||employeesInput.getState()==null||employeesInput.getState().equals("")){
            ResponseResult.error(PARAM_EORRO);
        }
        Dimission dimission=dimissionService.getById(employeesInput.getId());
        if(dimission==null){
            ResponseResult.error(PARAM_EORRO);
        }
        if(dimissionService.updateState(employeesInput)<0){
            ResponseResult.error("操作失败");
        }
        return ResponseResult.success();
    }





}
