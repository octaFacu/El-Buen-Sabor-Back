package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import com.example.demo.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public List<ProyeccionEstadisticaClienteTotalPedidos> ProyeccionEstadisticaClienteTotalPedidosSinPages() throws Exception {
        try {
            List<ProyeccionEstadisticaClienteTotalPedidos> clientesPedidos = repositorio.findTotalPedidosDeUsuarioSinPage();
            return clientesPedidos;
        }
        catch (Exception e){
            throw new Exception("Error al traer los datos de la proyeccion ",e);
        }
    }

    @Override
    public Page<ProyeccionHistorialPedidoUsuario> historialPedidoCliente(Long idCliente, Pageable pageable, Date fechaInicio, Date fechaFin) throws Exception {
        try {
            Page<ProyeccionHistorialPedidoUsuario> historialPedido = repositorio.historialPedidoUsuario(idCliente, pageable, fechaInicio, fechaFin);
            return historialPedido;
        }catch (Exception e){
            throw new Exception("Error al traer los datos de la proyeccion ",e);
        }
    }

    @Override
    public List<ProyeccionHistorialPedidoUsuario> historialPedidoClienteSinPage(Long idCliente) throws Exception {
        try {
            List<ProyeccionHistorialPedidoUsuario> historialPedido = repositorio.historialPedidoUsuarioSinPage(idCliente);
            return historialPedido;
        }catch (Exception e){
            throw new Exception("Error al traer los datos de la proyeccion ",e);
        }
    }

    public Page<ProyeccionEstadisticaClienteTotalPedidos> obtenerEstadisticasPedido(
            Date fechaInicio, Date fechaFin, String campoOrden, String direccionOrden, Pageable pageable) {

        List<ProyeccionEstadisticaClienteTotalPedidos> resultados = repositorio.obtenerInformacionPedidos(
                fechaInicio, fechaFin, campoOrden, direccionOrden
        );

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), resultados.size());

        Page<ProyeccionEstadisticaClienteTotalPedidos> page = new PageImpl<>(resultados.subList(start, end), pageable, resultados.size());

        return page;
    }

    @Override
    public Cliente getClienteXUsuarioId(String id_usuario) throws Exception {
        try {
            Cliente cliente = repositorio.findClienteXUsuarioId(id_usuario);
            return cliente;
        }catch (Exception e){
            throw new Exception("Error al traer los datos de la proyeccion ",e);
        }
    }

}
