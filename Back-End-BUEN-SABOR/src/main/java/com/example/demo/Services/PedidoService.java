package com.example.demo.Services;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Pedido;
import com.example.genericos.genericos.services.GenericService;

import java.util.List;

public interface PedidoService extends GenericService<Pedido, Long> {
    public Pedido save(Pedido pedido);
    public List<Pedido> buscarPedidoPorEstado(String estadoProducto) throws Exception;
}
