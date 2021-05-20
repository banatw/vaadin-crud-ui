package com.example.application.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TempatLahir {
    @Id
    private Integer idTempatLahir;

    private String nama;

}
