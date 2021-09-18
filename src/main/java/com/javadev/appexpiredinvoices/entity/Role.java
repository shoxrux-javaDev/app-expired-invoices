package com.javadev.appexpiredinvoices.entity;

import com.javadev.appexpiredinvoices.entity.template.AbsLong;
import com.javadev.appexpiredinvoices.enums.EnumRole;
import com.javadev.appexpiredinvoices.enums.Permission;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbsLong implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    private EnumRole roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
