package com.example.demo.Repository;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClienteRepository extends GenericRepository<Cliente,Long> {
    @Query(value = "SELECT id_cliente FROM cliente WHERE usuario_id LIKE :id_usuario",nativeQuery = true)
    Long findbyId_usuario(@Param("id_usuario") String id_usuario);

    @Query(value = "SELECT cliente.id_cliente, usuario.nombre,COUNT(pedido.precio_total) AS cantidad_pedidos, " +
            "IFNULL(SUM(pedido.precio_total),0) AS importe_total FROM usuario " +
            "INNER JOIN cliente ON cliente.usuario_id = usuario.id LEFT JOIN pedido ON cliente.id_cliente = pedido.cliente_id " +
            "GROUP BY usuario.id, usuario.nombre", nativeQuery = true)
    Page<ProyeccionEstadisticaClienteTotalPedidos> findTotalPedidosDeUsuario(Pageable pageable);


    @Query(value = "SELECT pedido.id AS pedido_id ,pedido.es_envio, precio_total, pedido.fecha_pedido, " +
            "COUNT(pedido_has_producto.pedido_id) AS total_pedidos FROM pedido " +
            "LEFT JOIN pedido_has_producto ON pedido.id = pedido_has_producto.pedido_id WHERE pedido.cliente_id = :id_cliente " +
            "GROUP BY pedido_has_producto.pedido_id, pedido.id ORDER BY pedido.fecha_pedido ASC",nativeQuery = true)
    Page<ProyeccionHistorialPedidoUsuario> historialPedidoUsuario(@Param("id_cliente") Long id_cliente, Pageable pageable);

}
