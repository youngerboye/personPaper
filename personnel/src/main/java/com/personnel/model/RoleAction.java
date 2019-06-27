package com.personnel.model;




import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role_action")
@Getter
@Setter
@NoArgsConstructor
public class RoleAction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleActionGenerator")
    @SequenceGenerator(name = "roleActionGenerator", sequenceName = "roleActionNew_sequence",allocationSize = 1,initialValue = 1)
    private Integer id;

    @Column(nullable = false)
    private Integer roleId;

    @Column(nullable = false)
    private Integer actionId;

    @Column(nullable = false)
    private Integer menuId;

    @OneToOne
    @JoinColumn(name="id", referencedColumnName="actionId")
    private Action action;

    public RoleAction(Integer roleId, Integer actionId, Integer menuId) {
        this.roleId = roleId;
        this.actionId = actionId;
        this.menuId = menuId;
    }

}
