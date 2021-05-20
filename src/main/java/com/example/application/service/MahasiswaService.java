package com.example.application.service;

import java.util.List;

import com.example.application.entity.Mahasiswa;
import com.example.application.repo.MahasiswaRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MahasiswaService {
    private MahasiswaRepo mahasiswaRepo;

    @Autowired
    public MahasiswaService(MahasiswaRepo repo) {
        this.mahasiswaRepo = repo;
    }

    public Mahasiswa addMahasiswa(Mahasiswa mahasiswa) {
        return mahasiswaRepo.save(mahasiswa);
    }

    public Mahasiswa updateMahasiswa(Mahasiswa mahasiswa) {
        return mahasiswaRepo.save(mahasiswa);
    }

    public void deleteMahasiswa(Mahasiswa mahasiswa) {
        mahasiswaRepo.delete(mahasiswa);
    }

    public List<Mahasiswa> findAll() {
        return mahasiswaRepo.findAll();
    }

    public Page<Mahasiswa> findAll(Pageable pageable) {
        return mahasiswaRepo.findAll(pageable);
    }
}
