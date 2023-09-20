package com.example.demo.Entidades.Wrapper;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDataMP {

    private UserAuth0MP usuario;

    private List<ProductoParaPedidoMP> productos;

}
