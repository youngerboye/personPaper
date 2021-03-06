package com.personnel.service;

import com.common.Enum.AuditStateEnum;
import com.personnel.core.base.BaseMapper;
import com.personnel.core.base.BaseService;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.input.EmployeesInput;
import com.personnel.domain.output.DimissionOutput;
import com.personnel.mapper.jpa.DimissionRepository;
import com.personnel.mapper.jpa.UserRepository;
import com.personnel.mapper.mybatis.AttachmentMapper;
import com.personnel.mapper.mybatis.DimissionMapper;
import com.personnel.mapper.mybatis.UsersMapper;
import com.personnel.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;


@Service
public class DimissionService extends BaseService<DimissionOutput,Dimission,Integer> {

    @Autowired
    private DimissionRepository repository;

    @Autowired
    private DimissionMapper dimissionMapper;

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseMapper<Dimission, Integer> getMapper() {
        return repository;
    }

    @Override
    public MybatisBaseMapper<DimissionOutput> getMybatisBaseMapper() {
        return dimissionMapper;
    }

    public DimissionOutput selectByEmployeeId(Integer id) {
        return dimissionMapper.selectByEmployeeId(id);
    }

    @Override
    public int add(Dimission dimission) throws InvocationTargetException, IntrospectionException, MethodArgumentNotValidException, IllegalAccessException {
        dimission.setState(AuditStateEnum.AUDIT.getCode());
        return super.add(dimission);
    }

    @Override
    public DimissionOutput getById(Integer id) {
        DimissionOutput dimissionOutput=dimissionMapper.selectByPrimaryKey(id);
        dimissionOutput.setAttachmentList(attachmentMapper.selectByDimissionId(id));
        return dimissionOutput;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateState(EmployeesInput employeesInput) throws IllegalAccessException, IntrospectionException, InvocationTargetException, MethodArgumentNotValidException {
        Dimission dimission= getMapper().getById(employeesInput.getId());
        if(employeesInput.getState()==1){
            Employees employees=employeesService.getById(dimission.getEmployeeId());
            employees.setId(dimission.getEmployeeId());
            employees.setWorkingState(3);
            //如果员工的离职时间为空，设置离职时间
            if(employees.getDepartureDateTime()==null){
                employees.setDepartureDateTime(new Date());
            }
            if(employeesService.update(dimission.getEmployeeId(),employees)<=0){
                return ERROR;
            }
            dimission.setState(employeesInput.getState());
            if(super.update(employeesInput.getId(),dimission)<=0){
                return ERROR;
            }

            Users users = userRepository.findByEmployeeId(employees.getId());
            users.setIsAccountNonExpired(1);
            users.setIsAccountNonLocked(1);
            users.setIsCredentialsNonExpired(1);
            users.setIsEnabled(1);
            users.setLastUpdateDateTime(new Date());
            users.setLastUpdateUserName(getUsers().getUsername());
            users.setLastUpdateUserId(getUsers().getId());
            userRepository.save(users);
        }
        return ERROR;
    }
}
