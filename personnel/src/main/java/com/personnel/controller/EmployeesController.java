package com.personnel.controller;


import com.common.model.PageData;
import com.common.response.ResponseResult;
import com.github.pagehelper.PageInfo;
import com.personnel.core.base.BaseController;
import com.personnel.core.base.BaseService;
import com.personnel.core.util.AppConsts;
import com.personnel.domain.input.EmployeesInput;
import com.personnel.domain.input.PhysicalAddressInput;
import com.personnel.domain.input.PlateNoInput;
import com.personnel.domain.output.EmployeesOutput;
import com.personnel.model.Employees;
import com.personnel.model.Organization;
import com.personnel.model.Person;
import com.personnel.model.Plate;
import com.personnel.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/employees")
@Api(value = "人员controller",description = "人员操作",tags = {"人员操作接口"})
public class EmployeesController extends BaseController<EmployeesOutput, Employees,Integer> {

    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private OrganizationService organizationService;

    @Override
    public BaseService<EmployeesOutput,Employees, Integer> getService() {
        return employeesService;
    }

    @Override
    @ApiOperation("新增或修改人员信息")
    @RequestMapping(value = "form", method = RequestMethod.POST)
    public ResponseResult formPost(Integer id, @Validated @RequestBody @ApiParam(name="人员信息",value="传入json格式",required=true) Employees employees) throws Exception {
        if(!employeesService.verificationOrg(employees.getOrganizationId(),null)){
            return ResponseResult.error("必须将员工关联末级组织");
        }
        if(employees.getPlateNoList()!=null&&employees.getPlateNoList().size()>0){
            StringBuilder a=new StringBuilder();
            for(String s:employees.getPlateNoList()){
                a.append(s+",");
            }
            employees.setPlateNo(a.toString().substring(0,a.toString().length()-1));
        }else {
            employees.setPlateNo("");
        }
        if(id!=null){
            if(employees.getEmployeeNo()==null||employees.getEmployeeNo().length()<=0||employees.getEmployeeNo().length()>55){
                return ResponseResult.error("工号不能为null且长度不超过55");
            }
        }
        if(employeesService.isRepeatIdCard(id,employees)){
            return ResponseResult.error("身份证号码重复");
        }
        if(employeesService.isRepeatPhoneNumber(id,employees)){
            return ResponseResult.error("手机号码重复");
        }
        if(id==null){
            String t=employeesService.getMaxNo(employees.getOrganizationId());
            employees.setEmployeeNo(t);
            employees.setActivationId(0);
            employees.setInductionDateTime(new Date());
            employees.setWorkingState(AppConsts.Work);
            id=employeesService.add(employees);
            if(id<0){
                return  ResponseResult.error(SYS_EORRO);
            }
        }else {
            if(employeesService.update(id,employees)<0){
                return  ResponseResult.error(SYS_EORRO);
            }
            employees.setId(id);
        }
        return ResponseResult.success();
    }


    @Override
    @ApiOperation("删除人员信息")
    @GetMapping(value = "logicDelete")
    public ResponseResult logicDelete(String idList) throws IllegalAccessException, IntrospectionException, InvocationTargetException, MethodArgumentNotValidException{

        Employees employees ;
        if(idList.split(",").length>1){
            return ResponseResult.error("人员数据只能单个删除，请选择单条数据后做删除");
        }else{
            employees=employeesService.getById(Integer.parseInt(idList.split(",")[0]));
            if(employees.getWorkingState()!=0){
                return ResponseResult.error("不是待入职状态的人员不可以删除");
            }
        }
        ResponseResult result = super.logicDelete(idList);
        //如果人员删除成功
        if(result.getCode()==200){
            return ResponseResult.success("删除人员成功");
        }
        return ResponseResult.error("删除人员失败");
    }


    @ApiOperation("获取所有不分页人员信息")
    @GetMapping(value = "selectAll")
    public ResponseResult selectAll(){
      return   employeesService.selectAll();
    }

    @GetMapping(value = "findPageList")
    @ApiOperation("获取分页的员工列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="employeeNo",value="员工工号",required=false,dataType="string", paramType = "query"),
            @ApiImplicitParam(name="name",value="员工姓名",required=false,dataType="string", paramType = "query")
    })
    public ResponseResult selectPageList(HttpServletRequest request){
        PageData pageData = new PageData(request);
        pageData.put("amputated",0);
        return super.selectPageList(pageData);
    }

    @Override
    @GetMapping(value = "selectById")
    @ApiOperation(value="根据id获取单个窗口")
    public ResponseResult selectById(Integer id) {
        if( id==null ){
            ResponseResult.error(PARAM_EORRO);
        }
        return ResponseResult.success(employeesService.getByEmployeeId(id));
    }

    /**
     * 导入人员信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "importemployees", method = RequestMethod.POST)
    public ResponseResult importemployees(MultipartFile files) throws Exception {
        ResponseResult result = new ResponseResult();
        if (files == null ) {
            result.setMessage("上传文件为空");
            result.setSuccess(false);
            result.setCode(500);
            return result;
        }
        String a=employeesService.checkedFile(files);
        if(!a.equals("操作成功")){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage(a);
            return result;
        }
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("导入成功");
        return result;
    }

    /**
     * 导出人员信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public ResponseResult export(HttpServletResponse response, HttpServletRequest request) {
        try {
            String str = employeesService.export(response,request);
            return  ResponseResult.success(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 改变人员状态
     *
     * @param
     * @return
     */
    @RequestMapping(value = "updateState", method = RequestMethod.POST)
    public ResponseResult updateState(@RequestBody EmployeesInput employeesInput) throws Exception {
        ResponseResult result = new ResponseResult();
        result.setSuccess(true);
        result.setCode(200);
        Employees employees;
        if(employeesInput.getId()==null||employeesInput.getId().equals("")||employeesInput.getState()==null||employeesInput.getState().equals("")){
            ResponseResult.error(PARAM_EORRO);
        }
        employees=employeesService.getById(employeesInput.getId());
        if(employeesInput.getState()==1){
            employees.setWorkingState(employeesInput.getState());
        }else {
            if(!logicDelete(employeesInput.getId().toString()).isSuccess()){
                result.setCode(500);
                result.setSuccess(false);
            }
            return result;
        }

        return result;
    }



    @ApiOperation("根据窗口id获得该窗口下的人员")
    @GetMapping(value = "selectByWindowId")
    public ResponseResult selectByWindowId(Integer windowsId){
        return employeesService.selectByWindowId(windowsId);
    }


    /**
     * 导入人员信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "importcitizenCardPhysicalAddress", method = RequestMethod.POST)
    public ResponseResult importcitizenCardPhysicalAddress(MultipartFile files) throws Exception {
        ResponseResult result = new ResponseResult();
        if (files == null ) {
            result.setMessage("上传文件为空");
            result.setSuccess(false);
            result.setCode(500);
            return result;
        }
        String a=employeesService.checkedFiles(files);
        if(!a.equals("操作成功")){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage(a);
            return result;
        }
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("导入成功");
        return result;
    }

    @GetMapping(value = "get_by_organ_id")
    public ResponseResult getByOrganCode(Integer organId){
        if(organId == null){
            return ResponseResult.error(PARAM_EORRO);
        }
        return ResponseResult.success(employeesService.getByOrganCode(organId));
    }


    @GetMapping(value = "findList")
    @ApiOperation("获取分页的员工列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="employeeNo",value="员工工号",dataType="string", paramType = "query"),
            @ApiImplicitParam(name="name",value="员工姓名",dataType="string", paramType = "query")})
    public ResponseResult findList(HttpServletRequest request){
        PageData pageData = new PageData(request);
        var level = employeesService.getUsers().getAdministratorLevel();
        if(level!=9){
            if(getService().getUsers().getUserType()==0){
                pageData.put("employeeId",getService().getUsers().getEmployeeId());
            }else {
                pageData.put("organId",getService().getUsers().getOrganizationId());
            }
        }
        return super.selectPageList(pageData);
    }

    @GetMapping(value = "selectPageListWithinAuthority")
    @ApiOperation("获取带数据权限的分页的员工列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="employeeNo",value="员工工号",required=false,dataType="string", paramType = "query"),
            @ApiImplicitParam(name="name",value="员工姓名",required=false,dataType="string", paramType = "query")})
    public ResponseResult selectPageListWithinAuthority(HttpServletRequest request){
        PageData pageData = new PageData(request);
        return ResponseResult.success(new PageInfo<>(employeesService.selectPageListWithinAuthority(pageData)));
    }
}
