package com.example.demo.Entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1)
    private String tipo;


    private Double montoDescuento;

    @Column(nullable = false)
    private Integer numeroFactura;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @OneToOne
    @JoinColumn(name = "metodo_pago_id", nullable = false)
    private MetodoDePago MetodoDePago;

    private boolean activo;
}
