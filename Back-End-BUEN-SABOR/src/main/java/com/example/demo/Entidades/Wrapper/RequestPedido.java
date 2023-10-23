package com.example.demo.Entidades.Wrapper;

import com.example.demo.Entidades.Pedido;
import com.example.demo.Entidades.PedidoHasProducto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestPedido {

    private Pedido pedido;

    private List<PedidoHasProducto> pedidoHasProducto;

}
