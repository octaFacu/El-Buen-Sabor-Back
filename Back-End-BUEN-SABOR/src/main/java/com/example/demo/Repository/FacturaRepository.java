package com.example.demo.Repository;

import com.example.demo.Entidades.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends GenericRepository<Factura, Long> {

    // Consulta personalizada para obtener la última factura
    @Query("SELECT f FROM Factura f ORDER BY f.id DESC LIMIT 1")
    Factura findUltimaFactura();
}
