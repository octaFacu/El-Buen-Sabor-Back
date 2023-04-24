package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "unidadmedida")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UnidadDeMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String denominacion;

    @ManyToOne
    @JoinColumn(name = "padre_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UnidadDeMedida padre;

}