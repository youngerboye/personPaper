package com.personnel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "UserRole")
@Getter
@Setter
@NoArgsConstructor
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userRoleGenerator")
    @SequenceGenerator(name = "userRoleGenerator", sequenceName = "userRoleNew_sequence",allocationSize = 1,initialValue = 1)
    private Integer id;

    @Column(nullable = false)
    private Integer roleId;

    @Column(nullable = false)
    private Integer userId;

    @OneToMany(mappedBy = "roleId")
    private List<RoleAction> actions;

    @OneToMany(mappedBy = "roleId")
    private List<RoleMenu> menus;

    public UserRole(Integer roleId, Integer userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

}
