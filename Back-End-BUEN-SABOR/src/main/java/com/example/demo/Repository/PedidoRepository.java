package com.example.demo.Repository;

import com.example.demo.Entidades.Pedido;
import com.example.demo.Entidades.Proyecciones.ProyeccionPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PedidoRepository extends GenericRepository<Pedido, Long> {

    @Query("SELECT MAX(p.numeroPedidoDia) FROM Pedido p WHERE DATE(p.fechaPedido) = DATE(:fechaPedido)")
    Integer getMaxNumeroPedidoDiaByFechaPedido(@Param("fechaPedido") Timestamp fechaPedido);


    @Query(value = "SELECT pedido_has_producto.pedido_id, COUNT(*) AS total_pedidos, pedido.fecha_pedido, pedido.precio_total FROM pedido_has_producto " +
            "INNER JOIN pedido ON pedido_has_producto.pedido_id = pedido.id WHERE pedido.cliente_id = :id " +
            "GROUP BY pedido_has_producto.pedido_id, pedido.fecha_pedido, pedido.precio_total", nativeQuery = true)
    List<ProyeccionPedidoUsuario> getPedidoUsuario(@Param("id") long id);

    @Query(value = "SELECT pedido_has_producto.pedido_id, " +
            "producto.id AS producto_id, producto.denominacion, " +
            "COUNT(*) AS cantidad, producto.precio_total * COUNT(*) AS precio_total, " +
            "producto.imagen FROM pedido_has_producto INNER JOIN producto ON pedido_has_producto.producto_id = producto.id " +
            "WHERE pedido_id = :idPedido GROUP BY pedido_has_producto.pedido_id, producto.id, " +
            "producto.denominacion, producto.precio_total, producto.imagen;", nativeQuery = true)
    List<ProyeccionProductosDePedido> getProductosDePedido(@Param("idPedido") long idPedido);

}
