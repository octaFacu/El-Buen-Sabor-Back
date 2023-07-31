package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import com.example.demo.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProyeccionEstadisticaClienteTotalPedidos> ProyeccionEstadisticaClienteTotalPedidos() throws Exception {
        try {
            return repositorio.findTotalPedidosDeUsuario();
        }catch (Exception e){
            throw new Exception("Error al traer los datos de la proyeccion ",e);
        }
    }

    @Override
    public List<ProyeccionHistorialPedidoUsuario> historialPedidoCliente(Long idCliente) throws Exception {
        try {
            return repositorio.historialPedidoUsuario(idCliente);
        }catch (Exception e){
            throw new Exception("Error al traer los datos de la proyeccion ",e);
        }
    }
}
