package com.personnel.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersGenerator")
    @SequenceGenerator(name = "usersGenerator", sequenceName = "usersNew_sequence",allocationSize = 1,initialValue = 1)
    private Integer id;// ID自增长主键
    @Column(nullable = false, unique = true)
    private String username;// 用户名,不为空，不能重复

    @Column(nullable = false)
    private Integer employeeId;// 真实姓名,不为空

    @Column(nullable = false,length = 1)
    private Integer userType;

    @Column(nullable = false,length = 64)
    private String password;// 密码,不为空

    @Column(nullable = false,length = 1)
    private Integer organizationId;

    @Column(nullable = false,length = 1)
    private Integer administratorLevel;

    @Column(nullable = false,length = 1)
    private Integer isAccountNonExpired;//

    @Column(nullable = false,length = 1)
    private Integer isAccountNonLocked;//

    @Column(nullable = false,length = 1)
    private Integer isCredentialsNonExpired;//

    @Column(nullable = false,length = 1)
    private Integer isEnabled;//

    @Column(nullable = false)
    private Integer createdUserId;//

    @Column(nullable = false,length = 55)
    private String createdUserName;//

    @Column(nullable = false)
    private Date createdDateTime;//

    @Column(nullable = false)
    private Integer lastUpdateUserId;//

    @Column(nullable = false,length = 55)
    private String lastUpdateUserName;//

    @Column(nullable = false)
    private Date lastUpdateDateTime;//

    private Integer membershipOrganizationId;

    @OneToMany(mappedBy = "userId",cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<UserRole> roles = new ArrayList<>();

    public Users(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public Users(Integer isAccountNonExpired, Integer isAccountNonLocked, Integer isCredentialsNonExpired, Integer isEnabled, Integer lastUpdateUserId, String lastUpdateUserName, Date lastUpdateDateTime) {
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.lastUpdateUserId = lastUpdateUserId;
        this.lastUpdateUserName = lastUpdateUserName;
        this.lastUpdateDateTime = lastUpdateDateTime;
    }
}
