package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Excepciones.PaginaVaciaException;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import com.example.demo.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpClienteService extends GenericServiceImpl<Cliente, Long> implements ClienteService {

    @Autowired
    ClienteRepository repositorio;
   @Override
    public Long findbyId_cliente(String id_usuario) throws Exception {
        try{
            return repositorio.findbyId_usuario(id_usuario);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<ProyeccionEstadisticaClienteTotalPedidos> ProyeccionEstadisticaClienteTotalPedidos(Pageable pageable) throws Exception {
        try {
            Page<ProyeccionEstadisticaClienteTotalPedidos> clientesPedidos = repositorio.findTotalPedidosDeUsuario(pageable);
            return clientesPedidos;
        }
        catch (Exception e){
            throw new Exception("Error al traer los datos de la proyeccion ",e);
        }
    }

    @Override
    public Page<ProyeccionHistorialPedidoUsuario> historialPedidoCliente(Long idCliente, Pageable pageable) throws Exception {
        try {
            Page<ProyeccionHistorialPedidoUsuario> historialPedido = repositorio.historialPedidoUsuario(idCliente, pageable);
            return historialPedido;
        }catch (Exception e){
            throw new Exception("Error al traer los datos de la proyeccion ",e);
        }
    }
}
