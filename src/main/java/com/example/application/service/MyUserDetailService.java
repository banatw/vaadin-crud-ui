package com.example.application.service;

import com.example.application.entity.AppUser;
import com.example.application.model.MyCustomizeUserDetail;
import com.example.application.repo.AppUserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AppUserRepo appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        AppUser appUser = appUserService.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("fdfdfd");
        }
        MyCustomizeUserDetail mUserDetail = new MyCustomizeUserDetail(appUser.getUsername(), appUser.getPassword(),
                AuthorityUtils.createAuthorityList(appUser.getRole()));
        return mUserDetail;
    }

}
