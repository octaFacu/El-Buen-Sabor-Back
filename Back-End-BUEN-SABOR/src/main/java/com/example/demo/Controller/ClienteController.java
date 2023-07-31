package com.example.demo.Controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Services.ImpClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/cliente")
public class ClienteController extends GenericControllerImpl<Cliente,Long, ImpClienteService> {


    @GetMapping("/v1/{id_usuario}")
    public ResponseEntity<Long> buscarFavoritoPorUsuario(@PathVariable String id_usuario) throws Exception {
        try {
            Long proyeccion = service.findbyId_cliente(id_usuario);
            return ResponseEntity.ok(proyeccion);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener los datos de proyección.", e);
        }
    }

    @GetMapping("/totalPedidos")
    public ResponseEntity<List<ProyeccionEstadisticaClienteTotalPedidos>> ProyeccionEstadisticaClienteTotalPedidos() throws Exception {
        try{
            List<ProyeccionEstadisticaClienteTotalPedidos> proyeccion = service.ProyeccionEstadisticaClienteTotalPedidos();
            return ResponseEntity.ok(proyeccion);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener los datos de proyección.", e);
        }
    }


    @GetMapping("/historialPedidos/{idCliente}")
    public ResponseEntity<List<ProyeccionHistorialPedidoUsuario>> ProyeccionHistorialPedidos(@PathVariable Long idCliente) throws Exception {
        try{
            List<ProyeccionHistorialPedidoUsuario> proyeccion = service.historialPedidoCliente(idCliente);
            return ResponseEntity.ok(proyeccion);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener los datos de proyección.", e);
        }
    }


}
