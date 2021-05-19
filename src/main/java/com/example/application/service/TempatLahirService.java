package com.example.application.service;

import com.example.application.entity.TempatLahir;
import com.example.application.repo.TempatLahirRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempatLahirService {
    private TempatLahirRepo tempatLahirRepo;

    @Autowired
    public TempatLahirService(TempatLahirRepo repo) {
        this.tempatLahirRepo = repo;
    }

    public TempatLahir addTempatLahir(TempatLahir tempatLahir) {
        return tempatLahirRepo.save(tempatLahir);
    }

    public TempatLahir updateTempatLahir(TempatLahir tempatLahir) {
        return tempatLahirRepo.save(tempatLahir);
    }

    public void deleteTempatLahir(Integer id) {
        tempatLahirRepo.deleteById(id);
    }

}
