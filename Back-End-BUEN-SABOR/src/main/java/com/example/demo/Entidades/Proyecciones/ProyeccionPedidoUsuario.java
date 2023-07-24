package com.example.demo.Entidades.Proyecciones;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface ProyeccionPedidoUsuario {

    long getPedido_id();
    int getTotal_pedidos();
    @JsonFormat(pattern = "dd MMMM yyyy", locale = "es_ES", timezone = "UTC")
    Date getFecha_pedido();
    Long getprecio_total();

}
