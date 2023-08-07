package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService extends GenericService<Cliente,Long> {
    Long findbyId_cliente(String id_usuario) throws Exception;
    Page<ProyeccionEstadisticaClienteTotalPedidos> ProyeccionEstadisticaClienteTotalPedidos(Pageable pageable) throws Exception;
    Page<ProyeccionHistorialPedidoUsuario> historialPedidoCliente(Long idCliente, Pageable pageable) throws Exception;
}
