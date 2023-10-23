package com.example.demo.Entidades.Wrapper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class RequestWrapper {


    @Getter @Setter
    private Long idProducto;

    @Getter @Setter
    private Long idIngrediente;

    @Getter @Setter
    private Long idMedida;

    @Getter @Setter
    private double cantidad;




}
