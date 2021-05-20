package com.example.application.repo;

import java.util.List;

import com.example.application.entity.Mahasiswa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepo extends JpaRepository<Mahasiswa, Integer> {
    public List<Mahasiswa> findByNamaContainsIgnoreCase(String nama,Pageable pageable);

    public Integer countByNamaContainsIgnoreCase(String nama);
}
