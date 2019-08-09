package com.personnel.service;

import com.common.model.PageData;
import com.common.response.ResponseResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.personnel.core.base.BaseMapper;
import com.personnel.core.base.BaseService;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.OrganizationOutput;
import com.personnel.domain.output.UsersOutput;
import com.personnel.mapper.jpa.EmployeesRepository;
import com.personnel.mapper.jpa.RoleRepository;
import com.personnel.mapper.jpa.UserRepository;
import com.personnel.mapper.mybatis.OrganizationMapper;
import com.personnel.mapper.mybatis.UsersMapper;
import com.personnel.model.Employees;
import com.personnel.model.UserRole;
import com.personnel.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class UserService extends BaseService<UsersOutput, Users,Integer> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public BaseMapper<Users,Integer> getMapper() {
        return userRepository;
    }

    @Override
    public MybatisBaseMapper<UsersOutput> getMybatisBaseMapper() {
        return usersMapper;
    }


    public Users getByUserName(String name){
        return userRepository.findByUsername(name);
    }


    public List<UsersOutput> getByRoleId(Integer roleId, PageData pageData) {
        Integer pagesize = pageData.getRows();
        Integer page = pageData.getPageIndex();
        PageHelper.startPage(page, pagesize);
        return usersMapper.findByRoleId(pageData);
    }

    public UsersOutput selectByEmployeeId(Integer employeeId) {
        return usersMapper.selectByEmployeeId(employeeId);
    }

    @Async
    public void setDefaultRole(Integer userId) throws InvocationTargetException, IntrospectionException, MethodArgumentNotValidException, IllegalAccessException {
        var roles = roleRepository.findAllByDefaultPermissions(1);
        if(roles == null || roles.size() <= 0){
            return;
        }
        for(var r : roles){
            var userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(r.getId());
            userRoleService.add(userRole);
        }

    }
    public List<UsersOutput> getByRoleId(Integer roleId) {
        PageData pageData = new PageData();
        pageData.put("roleId",roleId);
        return usersMapper.findByRoleId(pageData);
    }

    public List<UsersOutput> getByRoleIdNotIn(Integer roleId, PageData pageData) {
        Integer pagesize = pageData.getRows();
        Integer page = pageData.getPageIndex();
        PageHelper.startPage(page, pagesize);

        Page<UsersOutput> pageList = usersMapper.findByRoleIdNotIn(pageData);
        return pageList;
    }



    public int deleteByEmployee(Integer employeeId){
        return usersMapper.updateByUserId(employeeId);
    }


    public boolean updatePassword(Users users){
        return usersMapper.updatePassword(users) > 0;
    }

//    public ResponseResult sendVerificationCode(String userName, String phoneNo) {
//        //验证手机号码是否有效
//            //根据用户的账号判断是人员账号还是部门账号
//        PageData pageData = new PageData();
//        UsersOutput usersOutput = usersMapper.selectByUserName(userName);
//        pageData.put("receiveTelephone",phoneNo);
//        pageData.put("isTiming",0);
//        ArrayList<String> objects = new ArrayList<>();
//        if(usersOutput==null){
//            return ResponseResult.error("系统中未找到该账户，请确认");
//        }
//        if(usersOutput.getUserType()==1){ //部门账号
//            OrganizationOutput organizationOutput = organizationMapper.selectByPrimaryKey(usersOutput.getOrganizationId());
//            if(!phoneNo.equals(organizationOutput.getPhoneNumber())){
//                return ResponseResult.error("手机号和系统中部门手机号不匹配请确认手机号是否输入正确或联系管理员核实部门对应的手机号");
//            }else {
//                pageData.put("receiveEmployeeName",organizationOutput.getName());
//                objects.add(phoneNo+ "/" +organizationOutput.getName());
//            }
//        }else{ //员工账号
//            Employees employeesById = employeesRepository.findEmployeesById(usersOutput.getEmployeeId());
//            if(!phoneNo.equals(employeesById.getPhoneNumber())){
//                return ResponseResult.error("手机号未注册到系统，请确认手机号是否输入正确或联系管理员核实手机号");
//            }else {
//                pageData.put("receiveEmployeeName",employeesById.getName());
//                objects.add(phoneNo+ "/" +employeesById.getName());
//            }
//        }
//        String code = String.valueOf(new Random().nextInt(899999) + 100000);
//        return ResponseResult.success();
//    }


    /**验证用户名和手机号码的正确性*/
    public ResponseResult sendVerificationCode(String userName, String phoneNo) {
        UsersOutput usersOutput = usersMapper.selectByUserName(userName);
        if(phoneNo.equals(usersOutput.getPhoneNumber())){
            return ResponseResult.success("验证成功");
        }
        return ResponseResult.error("手机号码填写错误");
    }
    public ResponseResult vCodeAndChangePwd(String userName,  String password) {
        Users users = userRepository.findByUsername(userName);
        users.setPassword(passwordEncoder.encode(password));
        Integer id = userRepository.save(users).getId();
        if(id<=0){
            return ResponseResult.error("密码修改失败");
        }
        return ResponseResult.success("密码修改成功");
    }


    public List<Users> getByOrganIdAndUserType(Integer organId){
        return userRepository.findAllByOrganizationIdAndUserType(organId, 1);
    }

    public Users getUsernameAndUserType(String username) {
        return userRepository.findByUsername(username);
    }

    public Page<UsersOutput> selectPageListWithinAuthority(PageData pageData) {
        Integer pagesize = pageData.getRows();
        Integer page = pageData.getPageIndex();
        PageHelper.startPage(page, pagesize);
        Page<UsersOutput> pageList = null;
        pageData.put("userId",getUsers().getId());
        if(getUsers().getAdministratorLevel()==9){
            pageList = usersMapper.selectPage(pageData);
        }else{
            if(getUsers().getUserType()==1){
                OrganizationOutput organizationOutput = organizationMapper.selectByPrimaryKey(getUsers().getOrganizationId());
                pageData.put("path",organizationOutput.getPath());
                pageData.put("orgId",getUsers().getOrganizationId());
            }else{
                pageData.put("employeeId",getUsers().getEmployeeId());
            }
            pageList = usersMapper.selectPageListWithinAuthority(pageData);
        }
        return pageList;
    }

//    public Integer selectOrganIdByEmpId(Integer empId) {
//        return usersMapper.selectOrganIdByEmpId(empId);
//    }
//
//    public Integer selectOrganIdByParentId(Integer organId){
//        return usersMapper.selectOrganIdByParentId(organId);
//    }

    public boolean verificationOrg(Users users) {
       List<OrganizationOutput> list =  organizationMapper.selectByParentId(users.getOrganizationId());
       if(list!=null&&list.size()>0){
           return false;
       }else{
           return true;
       }
    }

}
