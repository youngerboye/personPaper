package com.personnel.service;

import com.common.Enum.MessageTypeEnum;
import com.common.model.PageData;
import com.common.response.ResponseResult;
import com.common.utils.ExportExcel;
import com.common.utils.HttpRequestUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.personnel.core.base.BaseMapper;
import com.personnel.core.base.BaseService;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.input.EmployeesInput;
import com.personnel.domain.output.EmployeesOutput;
import com.personnel.domain.output.OrganizationOutput;
import com.personnel.mapper.jpa.EmployeesRepository;
import com.personnel.mapper.mybatis.EmployeesMapper;
import com.personnel.mapper.mybatis.OrganizationMapper;
import com.personnel.model.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("employeesService")
public class EmployeesService extends BaseService<EmployeesOutput, Employees, Integer> {

    @Autowired
    private EmployeesRepository repository;

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private JobsService jobsService;

    @Override
    public BaseMapper<Employees, Integer> getMapper() {
        return repository;
    }

    @Override
    public MybatisBaseMapper<EmployeesOutput> getMybatisBaseMapper() {
        return employeesMapper;
    }

    public String getMaxNo(Integer orgId){
        Organization org = organizationMapper.selectOrNoByOrId(orgId);
        String[] strs = org.getPath().split(",");
        int firstLetter = 9;
        for(int i = strs.length-1;i>=0;i--) {
            Organization o = organizationMapper.selectOrNoByOrId(Integer.valueOf(strs[i]));
            if (o!=null&&o.getFirstLetter() != null) {
                firstLetter = o.getFirstLetter();
                break;

            }
        }
        Integer small = null;
        Integer big = null;
        if(firstLetter==0){
            big = 10000;
        }else{
            small = Integer.valueOf(firstLetter+"0000");
            int firstLetter1 = firstLetter+1;
            big = Integer.valueOf(firstLetter1+"0000");
        }
        PageData pageData = new PageData();
        pageData.put("small",small);
        pageData.put("big",big);
        String s = employeesMapper.selectMaxNo(pageData);
        String t = null;
        if(s==null){
            t=firstLetter+"0001";
        }else {
            t = String.format("%05d",Integer.parseInt(s)+1);
        }
        return t;
    }

    public boolean isCantChange(Integer id, Employees employees) {
        Employees employees1 = repository.getById(id);
        if (employees1 != null) {
            if (employees.getIdCard().equals(employees1.getIdCard())
                    && employees.getEmployeeNo().equals(employees1.getEmployeeNo())
                    && employees.getSex().equals(employees1.getSex())) {
                return true;
            }
        }
        return false;
    }

    public ResponseResult selectAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return ResponseResult.success(repository.findAll(sort));
    }

//    @Transactional
//    public boolean updateState(Employees employees, Person person) throws InvocationTargetException, IntrospectionException, MethodArgumentNotValidException, IllegalAccessException {
//        if (update(employees.getId(), employees) > 0) {
//            return personService.add(person) > 0;
//
//        } else {
//            return false;
//        }
//    }

    public boolean checkCondition(Integer orgaId) {

        Organization organization = organizationMapper.selectOrNoByOrId(orgaId);
        Integer result = organizationMapper.selectCheckCondition(organization.getOrganizationNo());
        if (result > 0) {
            return true;
        }
        return false;
    }
//
//    @Async
//    /**当人员审核通过下发*/
//    public void queueUpdateState(EmployeesInput employeesInput) throws Exception {
//        if (employeesInput.getId() == null || employeesInput.getId().equals("") || employeesInput.getState() == null || employeesInput.getState().equals("")) {
//            return;
//        }
//        //如果是未审核的状态直接返回
//        if (employeesInput.getState() == 3) {
//            return;
//        }
//        Employees employees = this.getById(employeesInput.getId());
//        if (employeesInput.getState() == 1) {
//            employees.setWorkingState(employeesInput.getState());
//            employees.setInductionDateTime(new Date());
//        } else {
//            if (logicDelete(employeesInput.getId().toString()) < 0) {
//                return;
//            }
//        }
//    }


    public List<EmployeesOutput> selectByPath(PageData pageData) {
        Integer pagesize = pageData.getRows();
        Integer page = pageData.getPageIndex();
        PageHelper.startPage(page, pagesize);
        Page<EmployeesOutput> pageList = employeesMapper.selectByPath(pageData);
        return pageList;
    }


    public List<EmployeesOutput> selectByOrgId(PageData pageData){
        Integer pagesize = pageData.getRows();
        Integer page = pageData.getPageIndex();
        PageHelper.startPage(page, pagesize);
        Page<EmployeesOutput> pageList = employeesMapper.selectByOrgId(pageData);
        return pageList;
    }

    public EmployeesOutput getByEmployeeId(Integer id) {
        EmployeesOutput employeesOutput = employeesMapper.selectByPrimaryKey(id);
        if (employeesOutput != null) {
            if (employeesOutput.getPlateNo() != null && !employeesOutput.getPlateNo().equals("")) {
                String[] s = employeesOutput.getPlateNo().split(",");
                List<String> list = new ArrayList<String>();
                if (s != null && s.length > 0) {
                    for (String s1 : s) {
                        list.add(s1);
                    }
                }
                employeesOutput.setPlateNoList(list);
            }
        }
        return employeesOutput;
    }

    //上传excel到服务器
    public String checkedFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if (!suffixName.equals(".xls") && !suffixName.equals(".xlsx")) {
            return "文件类型错误";
        }
        int index = fileName.lastIndexOf("\\");
        if (index != -1) {
            fileName = fileName.substring(index + 1);
        }
        int hashCode = fileName.hashCode();
        //把hash值转换为十六进制
        String hex = Integer.toHexString(hashCode);
        String pathHeader = "D:\\\\HZFY\\\\personInfo";
        StringBuilder sb = new StringBuilder();
        sb.append("/" + hex.charAt(0));
        sb.append("/" + hex.charAt(1));
        sb.append("/" + System.currentTimeMillis() + "_" + fileName);
        String pathEnd = sb.toString();
        File fileInfo = new File(pathHeader + pathEnd);
        if (!fileInfo.getParentFile().exists()) {
            fileInfo.getParentFile().mkdirs();
        }
        file.transferTo(fileInfo);
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(new FileInputStream(pathHeader + pathEnd));
        } catch (Exception e) {
            wb = new XSSFWorkbook(new FileInputStream(pathHeader + pathEnd));
        }
        return importemployees(wb);

    }

    //把excel数据导入到数据库中
    public String importemployees(Workbook wb) throws InvocationTargetException, IntrospectionException, MethodArgumentNotValidException, IllegalAccessException, IOException {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int rows = sheet.getPhysicalNumberOfRows();
        int totalCells = 0;
        // 得到Excel的列数(前提是有行数)
        if (rows > 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Employees> list = new ArrayList<Employees>();
        List<QueueEmployees> queueEmployeesList = new ArrayList<>();


        for (int r = 1; r < rows; r++) {
            Row row = sheet.getRow(r);
            Employees employees = new Employees();
            QueueEmployees queueEmployees = new QueueEmployees();
            if (row == null) {
                continue;
            }
            var a = row.getCell(0);
            var name = "";
            try {
                name = String.valueOf(a.getStringCellValue()).trim();
            } catch (NullPointerException e) {
                break;
            }

            if (name == null || "".equals(name)) {
                continue;
            }


            // 循环Excel的列
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);

                if (null != cell) {
                    if (c == 15) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String value = String.valueOf(cell.getStringCellValue()).trim();
                        if (employees.getPartyMemberState() < 2) {
                            if (value.equals("") || value == null) {
                                return "第" + r + "行入党时间错误";
                            }
                            if (0 == cell.getCellType()) {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    //用于转化为日期格式
                                    Date d = cell.getDateCellValue();
                                    employees.setInductionDateTime(d);
                                } else {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                    try {
                                        Date date = simpleDateFormat.parse(String.valueOf(cell.getStringCellValue()));
                                        employees.setInductionDateTime(date);
                                    } catch (ParseException px) {
                                        px.printStackTrace();
                                        return "第" + r + "行入党时间错误";
                                    }
                                }
                            }
                        }

                    }
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String value = String.valueOf(cell.getStringCellValue()).trim();
                    if (!value.equals("") && value != null) {
                        if (c == 0) {
                            employees.setName(value.replace(" ",""));
                        } else if(c==1) {
                            if(!this.verificationOrg(null,value)){
                                return "第"+r+"行组织机构错误，必须将员工关联根组织";
                            }
                            Organization organization=organizationService.getByname(value);
                            if(organization!=null){
                                employees.setOrganizationId(organization.getId());
                                if(organization.getLinkedId()!=null){
                                    Organization organization1=organizationService.getByLinkedId(organization.getLinkedId());
                                    if(organization1!=null){
                                        employees.setOrganizationId(organization1.getId());
                                    }
                                }
                            }else {
                                return "第"+r+"行组织机构错误，系统中没有对应组织机构";
                            }
                        } else if(c==2){
                            List<Jobs> jobs=jobsService.getByName(value);
                            if(jobs!=null&&jobs.size()>0){
                                employees.setJobsId(jobs.get(0).getId());
                            }else {
                                return "第"+r+"行职务错误，系统中没有对应职务";
                            }
                        }
                        else if(c==3){
                            employees.setIdCard(value);
                            String s=value.substring(6,14);
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
                            try {
                                Date date=simpleDateFormat.parse(s);
                                employees.setDateBirth(date);
                            } catch(ParseException px) {
                                px.printStackTrace();
                               return "第"+r+"行身份证错误";
                            }
                        }else if(c==4){
                             if(value.equals("行政")){
                                 employees.setWindowState(2);
                             }else if(value.equals("窗口")){
                                 employees.setWindowState(1);

                             }else if(value.equals("后台")){
                                 employees.setWindowState(0);
                             }else if(value.equals("其他")){
                                 employees.setWindowState(3);
                             }else {
                                 return "第"+r+"行人员类型错误";
                             }
                        }else if(c==5){
                            employees.setWindowNo(value);
                        }else if(c==6){
                           employees.setOffice(value);
                        }else if(c==7){
                            if(value.equals("男")){
                                employees.setSex(0);
                            }else if (value.equals("女")){
                                employees.setSex(1);
                            }else {
                                return  "第"+r+"行，性别错误";
                            }
                        }else if(c==8){
                            if(value.equals("国家机关人员编制")){
                                employees.setUserCompile(0);
                            }else if(value.equals("国家事业单位人员编制")){
                                employees.setUserCompile(1);
                            }else if(value.equals("国家企业单位人员编制")){
                                employees.setUserCompile(2);
                            }else if(value.equals("编外人员")){
                                employees.setUserCompile(3);
                            } else {
                                return  "第"+r+"行，人员编制错误";
                            }
                        }else if(c==9){
                            if(value.equals("是")){
                                employees.setAttendanceState(0);
                            }else if(value.equals("否")){
                                employees.setAttendanceState(1);
                            }else {
                                return  "第"+r+"行，是否考勤错误，只能为是或否";
                            }
                        }else if(c==10){
                            if(value.equals("未婚")){
                                employees.setMaritalStatus(0);
                            }else if(value.equals("已婚")){
                                employees.setMaritalStatus(1);
                            }else if(value.equals("离异")){
                                employees.setMaritalStatus(2);
                            }else if(value.equals("丧偶")){
                                employees.setMaritalStatus(3);
                            }else {
                                return "第"+r+"行，婚姻状态错误";
                            }
                        }else if(c==11){
                            employees.setNational(value);
                        }else if(c==12){
                            if(value.equals("小学")){
                                employees.setRecordOfFormalSchooling(7);
                            } else if(value.equals("初中")){
                                employees.setRecordOfFormalSchooling(6);
                            }else if(value.equals("高中")){
                                employees.setRecordOfFormalSchooling(5);
                            }else if(value.equals("中专")){
                                employees.setRecordOfFormalSchooling(4);
                            }else if(value.equals("大专")){
                                employees.setRecordOfFormalSchooling(3);
                            }else if(value.equals("本科")){
                                employees.setRecordOfFormalSchooling(2);
                            }else if(value.equals("硕士")){
                                employees.setRecordOfFormalSchooling(1);
                            }else if(value.equals("博士")){
                                employees.setRecordOfFormalSchooling(0);
                            }else {
                                return "第"+r+"行，学历错误";
                            }
                        }else if(c==13){
                            if(value.equals("中共党员")){
                                employees.setPartyMemberState(1);
                            }else if(value.equals("中共预备党员")){
                                employees.setPartyMemberState(2);
                            }else if(value.equals("共青团员")){
                                employees.setPartyMemberState(3);
                            }else if(value.equals("民主党派")){
                                employees.setPartyMemberState(4);
                            }else if(value.equals("群众")){
                                employees.setPartyMemberState(5);
                            }else {
                                return "第"+r+"行，政治面貌错误";
                            }
                        }else if(c==14){
                            if(employees.getPartyMemberState()<2){
                                if(value.equals("机关支部")){
                                    employees.setPartyBranch(1);
                                }else if(value.equals("投资项目综合受理区支部")){
                                    employees.setPartyBranch(2);
                                }else if(value.equals("社会事务综合受理区支部")){
                                    employees.setPartyBranch(3);
                                }else if(value.equals("不动产登记综合受理区支部")){
                                    employees.setPartyBranch(4);
                                }else if(value.equals("公安业务受理区支部")){
                                    employees.setPartyBranch(5);
                                }else if(value.equals("商事登记综合受理区支部")){
                                    employees.setPartyBranch(6);
                                }else if(value.equals("新登分中心支部")){
                                    employees.setPartyBranch(7);
                                }else if(value.equals("场口分中心支部")){
                                    employees.setPartyBranch(8);
                                } else if(value.equals("中心机关支部")){
                                    employees.setPartyBranch(9);
                                } else if (value.equals("其他")) {
                                    employees.setPartyBranch(10);
                                } else {
                                    return "第"+r+"行，所在党支部错误";
                                }
                            }
                        }else if(c==16){
                            if(value.equals("是")){
                                employees.setReserveCadresState(1);
                            }else if(value.equals("否")){
                                employees.setReserveCadresState(0);
                            }else {
                                return "第"+r+"行，是否后备干部错误";
                            }
                        }else if(c==17){
                            employees.setPhoneNumber(value);
                        }else if(c==18){
                            employees.setOfficePhone(value);
                        }else if(c==19){
                            employees.setCitizenCards(value);
                        }else if(c==20){
                            employees.setBankCardNumber(value);
                        }
//                        else if(c==21){
//                            employees.setPlateNo(value);
//                        }else if(c==22){
//                            employees.setEmail(value);
//                            if(value!=null||!value.equals("")){
//                                if(!value.matches("^\\w+@(\\w+\\.)+\\w+$")){
//                                    return "第"+r+"行，邮箱格式错误";
//                                }
//                            }
//                        }
                    }
                }
            }
            list.add(employees);
        }
        if(!addEmployees(list)){
            return "系统错误";
        }
        return "操作成功";
    }

    @Transactional
    public  boolean addEmployees(List<Employees> employees) throws InvocationTargetException, IntrospectionException, MethodArgumentNotValidException, IllegalAccessException, IOException {
        for(Employees employees1:employees){
            employees1.setActivationId(0);
            employees1.setIcon("---");

            String t=getMaxNo(employees1.getOrganizationId());
            Organization organization=organizationService.getById(employees1.getOrganizationId());
            employees1.setEmployeeNo(t);
            employees1.setInductionDateTime(new Date());
            employees1.setWorkingState(1);
            PageData pageData=new PageData();
            pageData.put("idCard",employees1.getIdCard());
            List<EmployeesOutput> employeesOutputList=employeesMapper.selectByIdCard(pageData);

            if(employeesOutputList!=null&&employeesOutputList.size()>0){
                employees1.setEmployeeNo(employeesOutputList.get(0).getEmployeeNo());
                employees1.setId(employeesOutputList.get(0).getId());
                employees1.setIcon(employeesOutputList.get(0).getIcon());
                if(update(employees1.getId(),employees1)<0){
                    return false;
                }
            }else {
                int id=add(employees1);

                var user = new Users();
                user.setEmployeeId(id);
                user.setUserType(0);
                user.setPassword(passwordEncoder.encode("123456"));
                user.setIsEnabled(0);
                user.setIsCredentialsNonExpired(0);
                user.setIsAccountNonLocked(0);
                user.setIsAccountNonExpired(0);
                user.setUsername(employees1.getEmployeeNo());
                user.setOrganizationId(employees1.getOrganizationId());
                user.setAdministratorLevel(1);
                var userId = userService.add(user);
                if (userId < 0) {
                    return false;
                } else {
                    userService.setDefaultRole(userId);//保存默认权限
                    return true;
                }
            }
        }
        return false;
    }

    public List<EmployeesOutput> findByplateNo(PageData pageData){
        return employeesMapper.selectByPlateNo(pageData);
    }


    public String export(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String title = "人员信息";
        String excelName = "人员信息";
        String[] rowsName = new String[]{"序号", "工号","姓名" , "组织机构名称", "职务", "入职时间", "状态","手机号码","市民卡号","市民卡物理地址","车牌号","人员类型","身份证号码","性别","所在党支部","婚姻状态","窗口号","人员编制","是否考勤","民族","学历","政治面貌","是否后备干部","办公电话","后台号"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        PageData pageData=new PageData(request);
        pageData.put("userId",getUsers().getId().toString());
        if(getUsers().getAdministratorLevel()!=9){
            if(getUsers().getUserType()==0){
                pageData.put("employeeId",getUsers().getEmployeeId().toString());
            }else {
                pageData.put("orgId",getUsers().getOrganizationId().toString());
            }
        }
        List<EmployeesOutput> employeesOutputList=selectAll(false,pageData);
        if(employeesOutputList.size()>0){
            int i=1;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for(EmployeesOutput employeesOutput:employeesOutputList){
                objs = new Object[rowsName.length];
                objs[0]=i;
                objs[1]=employeesOutput.getEmployeeNo();
                objs[2]=employeesOutput.getName();
                objs[3]=employeesOutput.getOrganizationName();
                objs[4]=employeesOutput.getJobsName();
                objs[5]=formatter.format(employeesOutput.getInductionDateTime());
                objs[6]=employeesOutput.getStateName();
                objs[7]=employeesOutput.getPhoneNumber();
                objs[8]=employeesOutput.getCitizenCards();
                objs[9]=employeesOutput.getCitizenCardPhysicalAddress();
                objs[10]=employeesOutput.getPlateNo();
                objs[11]=employeesOutput.getWindowStateName();
                objs[12]=employeesOutput.getIdCard();
                objs[13]=employeesOutput.getSexName();
                objs[14]=employeesOutput.getPartyBranchName();
                objs[15]=employeesOutput.getMaritalStatusName();
                objs[16]=employeesOutput.getWindowName();
                objs[17]=employeesOutput.getUserCompileName();
                objs[18]=employeesOutput.getAttendanceStateName();
                objs[19]=employeesOutput.getNational();
                objs[20]=employeesOutput.getRecordOfFormalSchoolingName();
                objs[21]=employeesOutput.getPartyMemberStateName();
                objs[22]=employeesOutput.getReserveCadresStateName();
                objs[23]=employeesOutput.getOfficePhone();
                objs[24]=employeesOutput.getOffice();
                dataList.add(objs);
                i++;
            }
        }
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, excelName);
        return ex.export(response, request);
    }

    public ResponseResult selectByWindowId(Integer id) {
        List<EmployeesOutput> list =  employeesMapper.selectByWindowId(id);
        return  ResponseResult.success(list);
    }

    //上传excel到服务器
    public  String checkedFiles(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if(!suffixName.equals(".xls")){
            return "文件类型错误";
        }
        int index = fileName.lastIndexOf("\\");
        if (index != -1) {
            fileName = fileName.substring(index + 1);
        }
        int hashCode = fileName.hashCode();
        //把hash值转换为十六进制
        String hex = Integer.toHexString(hashCode);
        String pathHeader = "D:\\HZFY\\personInfo";
        StringBuilder sb = new StringBuilder();
        sb.append("/" + hex.charAt(0));
        sb.append("/" + hex.charAt(1));
        sb.append("/" + System.currentTimeMillis() + "_" + fileName);
        String pathEnd = sb.toString();
        File fileInfo = new File(pathHeader + pathEnd);
        if (!fileInfo.getParentFile().exists()) {
            fileInfo.getParentFile().mkdirs();
        }
        file.transferTo(fileInfo);
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(new FileInputStream(pathHeader + pathEnd));
        } catch (Exception e) {
            wb = new XSSFWorkbook(new FileInputStream(pathHeader + pathEnd));
        }
        return importcitizenCardPhysicalAddress(wb);

    }

    //把excel数据导入到数据库中
    public  String  importcitizenCardPhysicalAddress(Workbook wb) throws InvocationTargetException, IntrospectionException, MethodArgumentNotValidException, IllegalAccessException {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int rows = sheet.getPhysicalNumberOfRows();
        int totalCells = 0;
        // 得到Excel的列数(前提是有行数)
        if (rows > 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Employees> list=new ArrayList<Employees>();
       for (int r = 1; r < rows; r++){
           Row row = sheet.getRow(r);
           if (row == null) {
               continue;
           }
           var a = row.getCell(0);
           var name = "";
           try{
               name = String.valueOf(a.getStringCellValue()).trim();
           }catch (NullPointerException e){
               break;
           }
           if(name == null || "".equals(name)){
               continue;
           }
           Employees employees=new Employees();
           if (row == null) {
               continue;
           }
           for (int c = 0; c < totalCells; c++) {
               Cell cell = row.getCell(c);
               if(cell==null){
                   break;
               }
               cell.setCellType(Cell.CELL_TYPE_STRING);
               String value= String.valueOf(cell.getStringCellValue()).trim();
               EmployeesOutput employeesOutput=new EmployeesOutput();
               if(c==1){
                   if(value==null||value.equals("")){
                       return "第"+r+"行身份证不能为空";
                   }
                   PageData pageData=new PageData();
                   pageData.put("idCard",value);
                   List<EmployeesOutput> employeesOutputList=employeesMapper.selectByIdCard(pageData);
                   if(employeesOutputList==null||employeesOutputList.size()<0){
                       if(value==null||value.equals("")){
                           return "第"+r+"行身份证错误，未在系统中找到对应人";
                       }
                   }
                   employees.setId(employeesOutputList.get(0).getId());
               } else if(c==2){
                   if(value==null||value.equals("")){
                       return "第"+r+"行市民卡号错误，市民卡号不能为空";
                   }else {
                       employees.setCitizenCards(value);
                   }

               }else if(c==4){
                   if(value==null||value.equals("")){
                       return "第"+r+"行市民卡物理地址不能为空";
                   }else {
                       employees.setCitizenCardPhysicalAddress(value);
                   }
               }
           }
           list.add(employees);
       }
       if(list!=null&&list.size()>0){
           if(!updateEmployees(list)){
               return "系统错误";
           }
       }
        return "操作成功";
    }

    @Transactional
    public boolean updateEmployees(List<Employees> list) throws InvocationTargetException, IntrospectionException, MethodArgumentNotValidException, IllegalAccessException {
        for(Employees employees:list){
            if(super.update(employees.getId(),employees)<=0){
                return false;
            }
        }
        return true;
    }

    //判断市民卡号是否重复
    public boolean isRepeatCitizenCards(Integer id,Employees employees) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        if(id!=null&&!id.equals("")){
            Employees employees1=getById(id);
            if(employees1!=null&&!employees1.equals("")){
                if(employees.getCitizenCards()!=null&&!employees.getCitizenCards().equals("")){
                    if(employees.getCitizenCards().equals(employees1.getCitizenCards())){
                        return false;
                    }
                }
            }
            if(employeesMapper.selectByCitizenCards(employees.getCitizenCards())!=null){
                return true;
            }
        }
        return false;
    }


    //判断身份证号是否重复
    public boolean isRepeatIdCard(Integer id,Employees employees) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        if(id!=null&&!id.equals("")){
            Employees employees1=getById(id);
            if(employees1!=null&&!employees1.equals("")){
                if(employees.getIdCard()!=null&&!employees.getIdCard().equals("")){
                    if(employees.getIdCard().equals(employees1.getIdCard())){
                        return false;
                    }
                }
            }
        }
        PageData pageData=new PageData();
        pageData.put("idCard",employees.getIdCard());
        List<EmployeesOutput> list=employeesMapper.selectByIdCards(pageData);
        if(list!=null&&list.size()>0){
            return true;
        }
        return false;
    }

    //判断手机号是否重复
    public boolean isRepeatPhoneNumber(Integer id,Employees employees) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        if(id!=null&&!id.equals("")){
            Employees employees1=getById(id);
            if(employees1!=null&&!employees1.equals("")){
                if(employees.getPhoneNumber()!=null&&!employees.getPhoneNumber().equals("")){
                    if(employees.getPhoneNumber().equals(employees1.getPhoneNumber())){
                        return false;
                    }
                }
            }
        }
        PageData pageData=new PageData();
        pageData.put("phoneNumber",employees.getPhoneNumber());
        List<EmployeesOutput> list=employeesMapper.selectByPhoneNumber(pageData);
        if(list!=null&&list.size()>0){
            return true;
        }
        return false;
    }
    /**
     * 获取组织机构编码相同的管理员列表
     * @param organId
     * @return
     */
    public List<EmployeesOutput> getByOrganCode(Integer organId){
        var list = employeesMapper.getByOrganCode(organId);
        return list;
    }


    public  void sendMessage(){
        List<EmployeesOutput> list=employeesMapper.selectAll(null);
        for(EmployeesOutput output:list){
            List<String> list3 = new ArrayList<>();
            list3.add(output.getPhoneNumber()+ "/" +output.getName());
            String description=output.getName()+"您好： 大数据平台的地址是：http://10.32.250.84  您的账号是："+output.getEmployeeNo()+" 您的初始密码是：123456  请尽快修改您的初始密码";
            if(description==null||description.equals("")){
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("description", description);
            map.put("isTiming", 0);
            map.put("type", MessageTypeEnum.APPROVAL.getCode());
            map.put("sendList",list3);
            try {
                ResponseResult result = HttpRequestUtil.sendPostRequest("http://127.0.0.1:8775/smsSend/form",map);
            } catch (Exception e) {

            }
        }

    }

    public Page<EmployeesOutput> selectPageListWithinAuthority(PageData pageData) {
        Integer pagesize = pageData.getRows();
        Integer page = pageData.getPageIndex();
        PageHelper.startPage(page, pagesize);
        Page<EmployeesOutput> pageList = null;
        pageData.put("userId",getUsers().getId());
        if(getUsers().getAdministratorLevel()==9){
            pageList = employeesMapper.selectPageListWithinAuthority(pageData);
        }else{
            if(getUsers().getUserType()==1){
                pageData.put("orgId",getUsers().getOrganizationId());
            }else{
                pageData.put("employeeId",getUsers().getEmployeeId());
            }
            pageList = employeesMapper.selectPageListWithinAuthority(pageData);
        }
        return pageList;
    }


    public Integer getUserIdByEmpId(Integer empId){
        return employeesMapper.selectUserIdByEmpId(empId);
    }

    public boolean verificationOrg(Integer organizationId, String organizationName) {
        if(organizationId!=null){
            List<OrganizationOutput> list = organizationMapper.selectByParentId(organizationId);
            if(list!=null&&list.size()>0){
                return false;
            }else{
                return true;
            }
        }
        if(organizationName!=null){
            List<OrganizationOutput> list = organizationMapper.selectByName(organizationName);
            List<OrganizationOutput> lists = organizationMapper.selectByParentId(list.get(0).getId());
            if(lists!=null&&lists.size()>0){
                return false;
            }else{
                return true;
            }
        }
        return true;

    }
}
