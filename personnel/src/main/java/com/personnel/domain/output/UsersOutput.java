package com.personnel.domain.output;

import com.personnel.model.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersOutput extends Users {
    private String employeesName;

    private String organizationName;

    private String jobsName;

    private String icon;

    private String userTypeName;

    private Integer jobId;

    private Integer organId;

    private String organName;

    private String phoneNumber;

    public String getUserTypeName() {
        if(this.getUserType() != null){
            switch (this.getUserType()){
                case 1:
                    return "部门账号";
                case 0:
                    return "个人账号";
            }
        }
        return userTypeName;
    }


}
