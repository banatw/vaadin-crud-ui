package com.example.application;

import java.time.ZoneId;
import java.util.Random;

import com.example.application.entity.Mahasiswa;
import com.example.application.entity.TempatLahir;
import com.example.application.repo.TempatLahirRepo;
import com.example.application.service.MahasiswaService;
import com.example.application.service.TempatLahirService;
import com.github.javafaker.Faker;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.vaadin.artur.helpers.LaunchUtil;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private TempatLahirService tempatLahirService;

    @Autowired
    private TempatLahirRepo tempatLahirRepo;

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {
            TempatLahir tempatLahir = new TempatLahir();
            tempatLahir.setIdTempatLahir(i);
            tempatLahir.setNama(faker.address().cityName());
            tempatLahirService.addTempatLahir(tempatLahir);
        }

        for (int i = 0; i < 1000000; i++) {
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNama(faker.name().fullName());
            mahasiswa.setTglLahir(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            mahasiswa.setTempatLahir(tempatLahirRepo.findById(RandomUtils.nextInt(0, 10)).get());
            mahasiswaService.addMahasiswa(mahasiswa);
        }
    }

}
