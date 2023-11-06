package com.example.demo.Repository;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Pedido;

import com.example.demo.Entidades.PedidoHasProducto;
import com.example.demo.Entidades.Proyecciones.ProyeccionDatosFactura;
import com.example.demo.Entidades.Wrapper.ProdPedWrapper;

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


    @Query(value = "SELECT * FROM pedido WHERE estado LIKE :estado", nativeQuery = true)
    List<Pedido> buscarPedidoPorEstado(@Param("estado") String estadoProducto);

    @Query(value = "SELECT ped FROM PedidoHasProducto ped WHERE ped.pedido.id = :id")
    List<PedidoHasProducto> buscarPedidoProductos(@Param("id") Long idPedido);

    @Query(value = "SELECT * FROM pedido WHERE estado LIKE 'EnDelivery' AND delivery_id LIKE :idDelivery", nativeQuery = true)
    List<Pedido> buscarPedidoPorDelivery(@Param("idDelivery") String idDelivery);


    @Query(value = "SELECT pedido_has_producto.pedido_id, " +
            "producto.id AS producto_id, producto.denominacion, " +
            "COUNT(*) AS cantidad, producto.precio_total * COUNT(*) AS precio_total, " +
            "producto.imagen FROM pedido_has_producto INNER JOIN producto ON pedido_has_producto.producto_id = producto.id " +
            "WHERE pedido_id = :idPedido GROUP BY pedido_has_producto.pedido_id, producto.id, " +
            "producto.denominacion, producto.precio_total, producto.imagen;", nativeQuery = true)
    List<ProyeccionProductosDePedido> getProductosDePedido(@Param("idPedido") long idPedido);

    @Query(value ="SELECT factura.id,factura.numero_factura, pedido.es_envio, pedido.numero_pedido_dia, pedido.precio_total, factura.tipo, " +
            "pedido.fecha_pedido, usuario.nombre, usuario.apellido FROM factura INNER JOIN pedido ON pedido.id = :idPedido " +
            "INNER JOIN cliente ON pedido.cliente_id = cliente.id_cliente INNER JOIN usuario ON cliente.usuario_id = usuario.id" ,nativeQuery = true)
    ProyeccionDatosFactura getDatosFactura(@Param("idPedido") long idPedido);

    //@Query(value = "SELECT p FROM Pedido p WHERE p.cliente = :idCliente ORDER BY p.id DESC LIMIT 1")
    @Query(value = "SELECT * FROM pedido WHERE pedido.cliente_id = :idCliente ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Pedido findUltimoPedidoByClienteId(@Param("idCliente") long idCliente);

}
