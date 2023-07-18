package com.example.demo.Controller;

import com.example.demo.Entidades.Pedido;
import com.example.demo.Entidades.Proyecciones.ProyeccionPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import com.example.demo.Services.ImpPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pedido")
public class PedidoController extends GenericControllerImpl<Pedido,Long, ImpPedidoService> {

    @GetMapping("usuario/{id}")
    public ResponseEntity<List<ProyeccionPedidoUsuario>> traerPedidosUsuario(@PathVariable("id") long id) throws Exception {
        try {
            List<ProyeccionPedidoUsuario> pedido = service.getPedidoUsuario(id);
            return ResponseEntity.ok(pedido);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("producto/{idPedido}")
    public ResponseEntity<List<ProyeccionProductosDePedido>> traerProductosDePedido(@PathVariable("idPedido") long idPedido) throws Exception {
        try {
            List<ProyeccionProductosDePedido> productos = service.getProductosDePedido(idPedido);
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
