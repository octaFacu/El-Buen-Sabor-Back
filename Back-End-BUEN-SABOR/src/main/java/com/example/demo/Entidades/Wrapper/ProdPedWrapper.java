package com.example.demo.Entidades.Wrapper;

import lombok.Getter;
import lombok.Setter;

public class ProdPedWrapper {
    @Getter
    @Setter
    private Long producto_id;

    @Getter @Setter
    private Long pedido_id;

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private int cantidad;

    public ProdPedWrapper(Long id, int cantidad, Long pedido_id, Long producto_id) {
        this.id = id;
        this.cantidad = cantidad;
        this.pedido_id = pedido_id;
        this.producto_id = producto_id;
    }

}
