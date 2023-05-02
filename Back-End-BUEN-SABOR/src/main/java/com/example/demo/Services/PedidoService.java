package com.example.demo.Services;

import com.example.demo.Entidades.Pedido;
import com.example.genericos.genericos.services.GenericService;

public interface PedidoService extends GenericService<Pedido, Long> {
    public Pedido save(Pedido pedido);
}
