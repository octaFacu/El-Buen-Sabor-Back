package com.example.demo.Repository;

import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends GenericRepository<Factura, Long> {

    // Consulta personalizada para obtener la última factura
    @Query("SELECT f FROM Factura f ORDER BY f.id DESC LIMIT 1")
    Factura findUltimaFactura();

    @Query(value = "SELECT * FROM factura WHERE factura.pedido_id = :idPedido", nativeQuery = true)
    Factura findFacturaByPedidoId(@Param("idPedido") long idPedido);

    // Define una consulta personalizada para verificar si ya existe una factura con el mismo pedidoId
    @Query(value = "SELECT COUNT(*) FROM factura WHERE pedido_id = :pedidoId", nativeQuery = true)
    int countByPedidoId(@Param("pedidoId") long pedidoId);

}
