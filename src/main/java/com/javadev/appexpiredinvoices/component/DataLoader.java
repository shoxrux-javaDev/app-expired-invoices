package com.javadev.appexpiredinvoices.component;

import com.javadev.appexpiredinvoices.entity.Customer;
import com.javadev.appexpiredinvoices.entity.Role;
import com.javadev.appexpiredinvoices.enums.EnumRole;
import com.javadev.appexpiredinvoices.enums.Permission;
import com.javadev.appexpiredinvoices.repo.CustomerRepo;
import com.javadev.appexpiredinvoices.repo.RoleRepo;
import com.javadev.appexpiredinvoices.util.AppConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

import static com.javadev.appexpiredinvoices.enums.Permission.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String initialMode;
    final RoleRepo roleRepo;
    final CustomerRepo customerRepo;
    final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepo roleRepo, CustomerRepo customerRepo,
                      PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {
        if (initialMode.equals("always")) {
            Permission[] values = Permission.values();

            Role admin = roleRepo.save(new Role(EnumRole.ADMIN));

            Role user = roleRepo.save(new Role(EnumRole.USER));

            customerRepo.save(new Customer("Shoxrux", passwordEncoder.encode("admin123"), "Uzbekistan",
                    "shoxruxsindarov1996@gmail.com", "Tashkent chilanzar 9", "+998998049358",
                    Collections.singleton(admin), Arrays.asList(values), null, true));


            customerRepo.save(new Customer("Abbos", passwordEncoder.encode("user123"), "Uzbekistan",
                    "xattabummar@gmail.com", "Tashkent Yunusobod", "+998996358978",
                    Collections.singleton(user), Arrays.asList(
                    ADD_PAYMENT, VIEW_PAYMENT,
                    VIEW_ORDER, VIEW_DETAIL), null, true));
        }
    }
}
