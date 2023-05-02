package com.example.demo.Entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MetodoDePago")
public class MetodoDePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 13)
    private String tipo;

}
