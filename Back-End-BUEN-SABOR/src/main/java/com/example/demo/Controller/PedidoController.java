package com.example.demo.Controller;

import com.example.demo.Entidades.Pedido;
import com.example.demo.Services.ImpPedidoService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pedido")
public class PedidoController extends GenericControllerImpl<Pedido, ImpPedidoService> {

    @GetMapping("/estado/{estadoProducto}")
    public ResponseEntity<?> buscarPedidoEstado(@PathVariable String estadoProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPedidoPorEstado(estadoProducto));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
