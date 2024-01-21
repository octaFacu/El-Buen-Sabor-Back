package com.example.demo.Services;

import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.Pedido;

public interface FacturaService extends GenericService<Factura,Long> {

    public Factura saveFacturaMP(Pedido pedido) throws Exception;
    public Factura saveFactura(Pedido pedido) throws Exception;
}
