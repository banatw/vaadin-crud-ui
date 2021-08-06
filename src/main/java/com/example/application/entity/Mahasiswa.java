package com.example.application.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mahasiswa")
@SQLDelete(sql = "UPDATE mahasiswa SET deleted = true WHERE id_mahasiswa=?")
@Where(clause = "deleted=false")
public class Mahasiswa  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idMahasiswa;

    @NotBlank
    private String nama;

    private LocalDate tglLahir;

    @OneToOne
    private TempatLahir tempatLahir;

    @UpdateTimestamp
    private LocalDateTime auditDate;

    private boolean deleted = Boolean.FALSE;

}
