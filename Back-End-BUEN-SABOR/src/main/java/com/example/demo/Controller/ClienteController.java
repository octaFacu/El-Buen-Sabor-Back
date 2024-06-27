package com.example.demo.Controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import com.example.demo.Security.AdminOnly;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/cliente")
public class ClienteController extends GenericControllerImpl<Cliente,Long, ImpClienteService> {


    @PublicEndpoint
    @GetMapping("/v1/{id_usuario}")
    public ResponseEntity<Long> buscarFavoritoPorUsuario(@PathVariable String id_usuario) throws Exception {
        try {
            Long proyeccion = service.findbyId_cliente(id_usuario);
            return ResponseEntity.ok(proyeccion);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener los datos de proyección.", e);
        }
    }


    @PublicEndpoint
    @GetMapping("/historialPedidos/{idCliente}")
    public ResponseEntity<?>
    ProyeccionHistorialPedidos(@PathVariable Long idCliente,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "2") Integer size,
                               @RequestParam(required = false) Date fechaInicio,
                               @RequestParam(required = false) Date fechaFin)
                               throws Exception {
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<ProyeccionHistorialPedidoUsuario> proyeccion = service.historialPedidoCliente(idCliente, pageable, fechaInicio, fechaFin);
            return ResponseEntity.ok(proyeccion);
        } catch (Exception e) {
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("errorStatus", 500);
            respuestaDelError.put("msj", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaDelError);
        }
    }

    @PublicEndpoint
    @GetMapping("/obtener-estadisticas-pedido")
    public ResponseEntity<?> obtenerEstadisticasPedido(
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            @RequestParam(required = false) String campoOrden,
            @RequestParam(required = false) String direccionOrden,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProyeccionEstadisticaClienteTotalPedidos> estadisticas = service.obtenerEstadisticasPedido(
                    fechaInicio, fechaFin, campoOrden, direccionOrden, pageable
            );
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("errorStatus", 500);
            respuestaDelError.put("msj", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaDelError);
        }
    }

    //El id del usuario es el id de Auth0
    @PublicEndpoint
    @GetMapping("/clienteXUsuarioId/{id_usuario}")
    public ResponseEntity<?> getClienteXUsuarioId(@PathVariable String id_usuario) throws Exception{

        try{
            Cliente cliente = service.getClienteXUsuarioId(id_usuario);
            return ResponseEntity.status(HttpStatus.OK).body(cliente);

        } catch (Exception e) {
            // Manejar excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al procesar la solicitud.");
        }
    }
    @DeleteMapping("/usuario/{id_usuario}") // URL completa será /cliente/usuario/{id_usuario}
    public ResponseEntity<Void> deleteByUsuarioId(@PathVariable("id_usuario") String idUsuario) throws Exception {
        try {
            service.deleteByUsuarioId(idUsuario);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new Exception("Error al intentar eliminar el cliente: " + e.getMessage());
        }
    }

}
