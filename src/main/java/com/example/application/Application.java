package com.example.application;

import java.time.ZoneId;
import java.util.Locale;

import com.example.application.entity.AppUser;
import com.example.application.entity.Mahasiswa;
import com.example.application.entity.TempatLahir;
import com.example.application.repo.AppUserRepo;
import com.example.application.repo.TempatLahirRepo;
import com.example.application.service.MahasiswaService;
import com.example.application.service.TempatLahirService;
import com.github.javafaker.Faker;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.artur.helpers.LaunchUtil;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private TempatLahirService tempatLahirService;

    @Autowired
    private AppUserRepo appUserRepo;

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("in", "ID"));
        for (int i = 0; i < 10; i++) {
            TempatLahir tempatLahir = new TempatLahir();
            tempatLahir.setIdTempatLahir(i);
            tempatLahir.setNama(faker.address().cityName());
            tempatLahirService.addTempatLahir(tempatLahir);
        }

        for (int i = 0; i < 100; i++) {
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNama(faker.name().fullName());
            mahasiswa.setTglLahir(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            mahasiswa.setTempatLahir(tempatLahirService.getById(RandomUtils.nextInt(0, 10)));
            mahasiswaService.addMahasiswa(mahasiswa);
        }

        AppUser aUser = new AppUser();
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        aUser.setUsername("admin");
        aUser.setPassword(encoder.encode("admin"));
        aUser.setRole("ROLE_ADMIN");
        appUserRepo.save(aUser);

        aUser = new AppUser();
        aUser.setUsername("user");
        aUser.setPassword(encoder.encode("user"));
        appUserRepo.save(aUser);
    }

}
