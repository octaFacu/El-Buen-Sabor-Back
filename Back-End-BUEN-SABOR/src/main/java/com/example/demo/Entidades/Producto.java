package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.sql.Time;
import java.util.Collection;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String denominacion;

    @Column(nullable = false)
    private Boolean esManufacturado;

    @Column(nullable = true)
    private String tiempoCocina;

    @Column(nullable = false)
    private String descripcion;

    @Column(length = 1000, nullable = true)
    private String receta;

    @Column(nullable = false)
    private Double costoTotal;

    private String imagen;

    @Column(nullable = false)
    private Double precioTotal;

    @Column(nullable = false)
    private Boolean activo;

    //13062024 - PF - Añadido stock para no Manufacturados
    @Column(nullable = false)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_producto_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CategoriaProducto categoriaProducto;

}
