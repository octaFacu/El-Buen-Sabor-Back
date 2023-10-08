package com.example.demo.Services;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Pedido;

import com.example.demo.Entidades.PedidoHasProducto;

import com.example.demo.Entidades.Proyecciones.ProyeccionDatosFactura;
import com.example.demo.Entidades.Proyecciones.ProyeccionPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import com.example.demo.Entidades.Wrapper.RequestPedido;
import org.springframework.data.repository.query.Param;

import java.util.List;


import java.util.List;

public interface PedidoService extends GenericService<Pedido, Long> {
    public Pedido save(Pedido pedido);

    public List<Pedido> buscarPedidoPorEstado(String estadoProducto) throws Exception;
    public List<PedidoHasProducto> buscarPedidoProductos(Long idPedido) throws Exception;

    List<ProyeccionProductosDePedido> getProductosDePedido(long idPedido) throws Exception;

    RequestPedido savePedidoAndPedidoHasProdcuto(RequestPedido pedido) throws Exception;

    ProyeccionDatosFactura getDatosFactura(long idPedido) throws Exception;

    //Pedido getUltimoPedidoByClienteId(long idCliente) throws Exception;
}
