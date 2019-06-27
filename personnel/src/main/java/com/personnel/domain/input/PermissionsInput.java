package com.personnel.domain.input;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PermissionsInput {
    private Integer roleId;

    private List<RoleMenuInput> roleMenuInputs;

}
