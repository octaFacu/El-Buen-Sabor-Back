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
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonIgnoreProperties("favoritos") // Ignorar la propiedad "favoritos" en la entidad Cliente
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonIgnoreProperties("favoritos") // Ignorar la propiedad "favoritos" en la entidad Producto
    private Producto producto;

    private boolean activo;
}
