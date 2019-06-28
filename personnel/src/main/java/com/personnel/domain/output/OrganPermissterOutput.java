package com.personnel.domain.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganPermissterOutput {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer checkState = 0;

    private List<OrganPermissterOutput> children;
}
