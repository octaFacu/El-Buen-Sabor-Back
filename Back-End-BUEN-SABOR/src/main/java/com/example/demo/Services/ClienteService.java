package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ClienteService extends GenericService<Cliente,Long> {
    Long findbyId_cliente(String id_usuario) throws Exception;

    List<ProyeccionEstadisticaClienteTotalPedidos> ProyeccionEstadisticaClienteTotalPedidosSinPages() throws Exception;

    Page<ProyeccionHistorialPedidoUsuario> historialPedidoCliente(Long idCliente, Pageable pageable, Date fechaInicio, Date fechaFin) throws Exception;

    List<ProyeccionHistorialPedidoUsuario> historialPedidoClienteSinPage(Long idCliente) throws Exception;

    Cliente getClienteXUsuarioId(String id_usuario) throws Exception;




}
