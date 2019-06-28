package com.personnel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_organ")
@Getter
@Setter
public class RoleOrgan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleOrganGenerator")
    @SequenceGenerator(name = "roleOrganGenerator", sequenceName = "roleOrgan_sequence",allocationSize = 1,initialValue = 1)
    private Integer id;

    @Column(nullable = false)
    private Integer roleId;

    @Column(nullable = false)
    private Integer organId;
}
