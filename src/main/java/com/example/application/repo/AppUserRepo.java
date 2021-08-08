package com.example.application.repo;

import java.util.List;

import com.example.application.entity.AppUser;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, Integer> {
    public List<AppUser> findByUsernameContainsIgnoreCase(String uName, Pageable pageable);

    public Integer countByUsernameContainsIgnoreCase(String uName);

    public AppUser findByUsername(String username);

}
