package com.example.demo.Services;

import com.example.demo.Entidades.Factura;

public interface FacturaService extends GenericService<Factura,Long> {

    public Factura saveFacturaMP(Factura pedido) throws Exception;
}
