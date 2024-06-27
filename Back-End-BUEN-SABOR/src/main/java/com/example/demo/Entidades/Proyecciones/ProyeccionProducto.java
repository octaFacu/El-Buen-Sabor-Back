package com.example.demo.Entidades.Proyecciones;

public interface ProyeccionProducto {

    int getId();
    String getDenominacion();
    boolean isEsManufacturado();
    String getTiempoCocina();
    String getDescripcion();
    String getReceta();
    double getCostoTotal();
    String getImagen();
    double getPrecioTotal();
    boolean isActivo();
    Integer getCategoriaProductoId();  // Aquí asumiendo que el id de la categoría es int

}
