package com.personnel.domain.input;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleInput {
    private Integer roleId;

    private String userIdList;

    private Integer userId;

    private String roleIdList;

}
