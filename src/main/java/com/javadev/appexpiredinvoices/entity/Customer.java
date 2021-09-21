package com.javadev.appexpiredinvoices.entity;

import com.javadev.appexpiredinvoices.entity.template.AbsUUID;
import com.javadev.appexpiredinvoices.enums.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Customer extends AbsUUID implements UserDetails {

    @Size(min = 3, max = 14)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "text")
    private String address;

    @Size(max = 50)
    @Column(unique = true)
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roleList;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    @CollectionTable(name="rolepermssion",joinColumns = @JoinColumn(columnDefinition = "Customer_id"))
    private List<Permission> permissionList;

    private String emailCode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roleList.forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getAuthority())));
        permissionList.forEach(permission
                -> authorities.add(new SimpleGrantedAuthority(permission.name())));
        return authorities;
    }

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled;



    public Customer(String username, String password, String country,
                    String email, String address, String phone, Set<Role> roleList,
                    List<Permission> permissionList, String emailCode, boolean enabled) {
        this.username = username;
        this.password = password;
        this.country = country;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.roleList = roleList;
        this.permissionList = permissionList;
        this.emailCode = emailCode;
        this.enabled = enabled;
    }
}
