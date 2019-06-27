package com.personnel.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role_menu")
@Getter
@Setter
@NoArgsConstructor
public class RoleMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleMenuGenerator")
    @SequenceGenerator(name = "roleMenuGenerator", sequenceName = "roleMenuNew_sequence", allocationSize = 1, initialValue = 1)
    private Integer id;

    @Column(nullable = false)
    private Integer roleId;

    @Column(nullable = false)
    private Integer menuId;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "menuId")
    private Menu menu;

    public RoleMenu(Integer roleId, Integer menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

}







