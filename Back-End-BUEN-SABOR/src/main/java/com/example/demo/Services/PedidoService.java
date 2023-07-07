package com.example.demo.Services;

import com.example.demo.Entidades.Pedido;

public interface PedidoService extends GenericService<Pedido, Long> {
    public Pedido save(Pedido pedido);
}
