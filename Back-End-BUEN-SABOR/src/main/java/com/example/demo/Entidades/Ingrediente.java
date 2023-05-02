package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String nombre;
    @Column(nullable = false)
    private Double precioCompra;
    @Column(nullable = false)
    private Double stockActual;
    @Column(nullable = false)
    private Double stockMinimo;
    @Column(nullable = false)
    private Double stockMaximo;
    @Column(nullable = false)
    private Boolean activo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_de_medida_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UnidadDeMedida unidadmedida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_ingrediente_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CategoriaIngrediente categoriaIngrediente;

}
