package com.example.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Double precioTotal;

    @Column(nullable = false)
    private Boolean esEnvio;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time horaEstimada;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp fechaPedido;


    private int numeroPedidoDia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", nullable = true)
    @JsonIgnoreProperties({"usuario","hibernateLazyInitializer", "handler"})
    private Direccion direccion;

    /*@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "pedido_has_producto",
            joinColumns = @JoinColumn(name = "pedido_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id",referencedColumnName = "id")
    )
    private Collection<Producto> productos;*/
}
