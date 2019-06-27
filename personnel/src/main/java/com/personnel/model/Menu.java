package com.personnel.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menuGenerator")
    @SequenceGenerator(name = "menuGenerator", sequenceName = "menuNew_sequence",allocationSize = 1,initialValue = 1)
    private Integer id;

    @Column(nullable = false)
    private Integer parentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Integer isShow;

    @Column(nullable = false)
    private Integer hasChild;

    private Integer disPlayOrderBy;


    @Column(nullable = true)
    private String path;

    @Column(nullable = true)
    private String icon;

    @Column(nullable = true)
    private String ename;


    @Column(nullable = false)
    private Integer createdUserId;//

    @Column(nullable = false,length = 55)
    private String createdUserName;//

    @Column(nullable = false)
    private Date createdDateTime;//

    @Column(nullable = false)
    private Integer lastUpdateUserId;//

    @Column(nullable = false)
    private String lastUpdateUserName;//

    @Column(nullable = false)
    private Date lastUpdateDateTime;//

    @OneToMany(mappedBy = "menuId")
    private List<Action> actions;

}
