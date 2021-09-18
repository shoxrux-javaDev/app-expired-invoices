package com.javadev.appexpiredinvoices.service;

import com.javadev.appexpiredinvoices.entity.Customer;
import com.javadev.appexpiredinvoices.enums.EnumRole;
import com.javadev.appexpiredinvoices.payload.CustomerRegDto;
import com.javadev.appexpiredinvoices.payload.LoginDto;
import com.javadev.appexpiredinvoices.payload.Response;
import com.javadev.appexpiredinvoices.repo.CustomerRepo;
import com.javadev.appexpiredinvoices.repo.RoleRepo;
import com.javadev.appexpiredinvoices.security.JwtProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.javadev.appexpiredinvoices.enums.Permission.*;
import static com.javadev.appexpiredinvoices.enums.Permission.VIEW_DETAIL;

@Service
@Transactional
public class CustomerRegService implements UserDetailsService {
    final CustomerRepo customerRepo;
    final PasswordEncoder encoder;
    final RoleRepo roleRepo;
    final JavaMailSender mailSender;
    final AuthenticationManager authenticationManager;
    final JwtProvider jwtProvider;

    public CustomerRegService(CustomerRepo customerRepo,
                              PasswordEncoder encoder, RoleRepo roleRepo,
                              @Lazy JavaMailSender mailSender,
                              @Lazy AuthenticationManager authenticationManager,
                              JwtProvider jwtProvider) {
        this.customerRepo = customerRepo;
        this.encoder = encoder;
        this.roleRepo = roleRepo;
        this.mailSender = mailSender;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public Response postCustomerInfo(CustomerRegDto customerRegDto) {
        boolean trueOrFalse = customerRepo.existsByEmail(customerRegDto.getEmail());
        if (trueOrFalse) return new Response("as long as this account is available in system", false);
        Customer customer = new Customer();
        customer.setUsername(customerRegDto.getUsername());
        customer.setEmail(customerRegDto.getEmail());
        customer.setCountry(customerRegDto.getCountry());
        customer.setAddress(customerRegDto.getAddress());
        if (!customerRegDto.getPassword().equals(customerRegDto.getPrePassword()))
            return new Response("prePassword isn't correct", false);
        customer.setPassword(encoder.encode(customerRegDto.getPassword()));
        customer.setRoleList(Collections.singleton(roleRepo.findByRoleName(EnumRole.USER)));
        customer.setPermissionList(Arrays.asList(ADD_PAYMENT, VIEW_DETAIL,VIEW_PAYMENT,
                VIEW_ORDER,VIEW_INVOICE));
        customer.setEmailCode(UUID.randomUUID().toString());
        customer.setPhone(customerRegDto.getPhone());
        sendEmail(customer.getEmail(), customer.getEmailCode());
        customerRepo.save(customer);
        return new Response("Successfully registered,please check your email account",
                true);
    }

    public void sendEmail(String email, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("citytashkent2021@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setSubject("Check Account");
            mailMessage.setText("http://localhost:8181/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + email);
            mailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Response verifyEmail(String email, String emailCode) {
        Optional<Customer> optionalCustomer = customerRepo.findByEmailAndEmailCode(email, emailCode);
        if (optionalCustomer.isEmpty()) return new Response("account already activated", false);
        Customer customer = optionalCustomer.get();
        customer.setEnabled(true);
        customer.setEmailCode(null);
        customerRepo.save(customer);
        return new Response("successfully activated", true);
    }

    public Response loginToSystem(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    ));
            Customer customer = (Customer) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getEmail(),
                    customer.getAuthorities());
            return new Response("Token", true, token);
        } catch (BadCredentialsException badCredentialsException) {
            return new Response("password or email error", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
    }
}
