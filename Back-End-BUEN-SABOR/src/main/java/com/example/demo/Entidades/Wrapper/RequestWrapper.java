package com.example.demo.Entidades.Wrapper;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class RequestWrapper {

    @Getter @Setter
    private Producto producto;

    @Getter @Setter
    private List<IngredientesDeProductos> ingredientes;

    public RequestWrapper() {
        ingredientes = new ArrayList<>();
    }
}
