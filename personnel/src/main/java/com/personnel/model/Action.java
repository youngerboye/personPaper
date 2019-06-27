package com.personnel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "action")
@Getter
@Setter
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actionGenerator")
    @SequenceGenerator(name = "actionGenerator", sequenceName = "actionNew_sequence",allocationSize = 1,initialValue = 1)
    private Integer id;

    @Column(nullable = false)
    private Integer menuId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String code;

}
