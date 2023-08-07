package com.example.demo.Controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Excepciones.PaginaVaciaException;
import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Services.ImpClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> ProyeccionEstadisticaClienteTotalPedidos
            (@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size) throws Exception {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProyeccionEstadisticaClienteTotalPedidos> proyeccion = service.ProyeccionEstadisticaClienteTotalPedidos(pageable);
            return ResponseEntity.ok(proyeccion);
        }  catch (Exception e) {
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("errorStatus", 500);
            respuestaDelError.put("msj", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaDelError);
        }
    }


    @GetMapping("/historialPedidos/{idCliente}")
    public ResponseEntity<?>
    ProyeccionHistorialPedidos(@PathVariable Long idCliente, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size)
            throws Exception {
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<ProyeccionHistorialPedidoUsuario> proyeccion = service.historialPedidoCliente(idCliente, pageable);
            return ResponseEntity.ok(proyeccion);
        } catch (Exception e) {
            Map<String, Object> respuestaDelError = new HashMap<>();
            respuestaDelError.put("errorStatus", 500);
            respuestaDelError.put("msj", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaDelError);
        }
    }


}
