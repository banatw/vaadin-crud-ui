package com.example.application.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyCustomizeUserDetail extends User {

    public MyCustomizeUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        //TODO Auto-generated constructor stub
    }

}
