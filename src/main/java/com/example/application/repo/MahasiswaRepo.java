package com.example.application.repo;

import com.example.application.entity.Mahasiswa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepo extends JpaRepository<Mahasiswa, Integer> {

}
