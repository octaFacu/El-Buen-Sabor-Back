package com.example.demo.Services;

import com.example.demo.Entidades.Pedido;
import com.example.demo.Entidades.Proyecciones.ProyeccionPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoService extends GenericService<Pedido, Long> {
    public Pedido save(Pedido pedido);

    List<ProyeccionProductosDePedido> getProductosDePedido(long idPedido) throws Exception;
}
