package com.example.demo.Entidades.Proyecciones;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface ProyeccionHistorialPedidoUsuario extends ProyeccionPedidoUsuario{


    boolean getEs_envio();
}
