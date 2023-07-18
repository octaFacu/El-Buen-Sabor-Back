package com.example.demo.Controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductoFavorito;
import com.example.demo.Services.ImpClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            throw new Exception(e.getMessage());
        }
    }


}
