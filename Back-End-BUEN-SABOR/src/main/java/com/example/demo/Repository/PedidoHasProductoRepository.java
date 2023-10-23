package com.example.demo.Repository;

import com.example.demo.Entidades.PedidoHasProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//public interface PedidoHasProductoRepository extends GenericRepository<PedidoHasProducto, Long>{
public interface PedidoHasProductoRepository extends JpaRepository<PedidoHasProducto, Long> {
}
