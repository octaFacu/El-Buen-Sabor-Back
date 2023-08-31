package com.example.demo.Entidades.Wrapper;


import lombok.Getter;
import lombok.Setter;


public class RequestWrapper {


    /*@Getter @Setter
    private List<IngredientesDeProductos> ingredientes;*/

    @Getter @Setter
    private Long idProducto;

    @Getter @Setter
    private Long idIngrediente;

    @Getter @Setter
    private Long idMedida;

    @Getter @Setter
    private double cantidad;


}
