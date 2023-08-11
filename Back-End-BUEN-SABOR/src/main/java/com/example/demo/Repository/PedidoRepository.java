package com.example.demo.Repository;

import com.example.demo.Entidades.Pedido;
import com.example.genericos.genericos.repositories.GenericRepository;
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
}
