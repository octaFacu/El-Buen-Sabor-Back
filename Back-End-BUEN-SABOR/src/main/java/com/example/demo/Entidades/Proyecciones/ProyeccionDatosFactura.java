package com.example.demo.Entidades.Proyecciones;

import java.util.Date;

public interface ProyeccionDatosFactura {

    int getId();
    int getNumero_factura();
    boolean getEs_envio();
    int getNumero_pedido_dia();
    int getPrecio_total();
    String getTipo();
    Date getFecha_pedido();
    String getNombre();
    String getApellido();
}
