package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;

import java.util.List;

public interface ClienteService extends GenericService<Cliente,Long> {
    Long findbyId_cliente(String id_usuario) throws Exception;
    List<ProyeccionEstadisticaClienteTotalPedidos> ProyeccionEstadisticaClienteTotalPedidos() throws Exception;
    List<ProyeccionHistorialPedidoUsuario> historialPedidoCliente(Long idCliente) throws Exception;
}
