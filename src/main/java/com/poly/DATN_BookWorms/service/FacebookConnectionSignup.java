package com.poly.DATN_BookWorms.service;


import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Authorities;
import com.poly.DATN_BookWorms.entities.Roles;

import com.poly.DATN_BookWorms.repo.AuthoritiesRepo;
import com.poly.DATN_BookWorms.repo.RoleRepo;
import com.poly.DATN_BookWorms.utils.CRC32Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {
    private PasswordEncoder passwordEncoder;

    @Autowired
    CRC32Utils crc32Utils;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    AuthoritiesRepo authoritiesRepo;

    @Override
    public String execute(Connection<?> connection) {
        System.out.println("signup ===============");
        Roles role = roleRepo.findById("FACEBOOK_GUEST").get();

        if (role == null) {
            role = roleRepo.save(new Roles("FACEBOOK_GUEST","Facebook Guest", null));
        }

        long userId = crc32Utils.getCRC32Hash(connection.getDisplayName());
        Account account = new Account();
        account.setUserid(Long.toString(userId));
        account.setEmail(null);
        account.setFullname(null);
        account.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphabetic(8)));
        account.setUsername(connection.getDisplayName());
        //Create authority for new account
        long authorityId =  crc32Utils.getCRC32Hash(connection.getDisplayName()+role.getRoleid());
        authoritiesRepo.save(new Authorities(Long.toString(authorityId),account, role));

        return account.getUsername();
    }
}
