package com.example.demo.Entidades.Wrapper;

import com.example.demo.Entidades.Producto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoParaPedidoMP {

    private Producto producto;

    private int cantidad;

}
