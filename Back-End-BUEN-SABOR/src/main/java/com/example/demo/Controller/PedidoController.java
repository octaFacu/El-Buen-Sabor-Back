package com.example.demo.Controller;

import com.example.demo.Entidades.Pedido;
import com.example.demo.Entidades.Proyecciones.ProyeccionDatosFactura;
import com.example.demo.Entidades.Proyecciones.ProyeccionPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import com.example.demo.Entidades.Wrapper.RequestPedido;
import com.example.demo.Services.ImpPedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pedido")

public class PedidoController extends GenericControllerImpl<Pedido, Long, ImpPedidoService> {

    @GetMapping("/estado/{estadoProducto}")
    public ResponseEntity<?> buscarPedidoEstado(@PathVariable String estadoProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPedidoPorEstado(estadoProducto));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createEntity(@RequestBody Pedido pedido) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(pedido));



        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/productos/{idProducto}")
    public ResponseEntity<?> buscarPedidoProducto(@PathVariable Long idProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPedidoProductos(idProducto));
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

    @PostMapping("/createPedidoAndProducto")
    public ResponseEntity<?> createPedidoAndPedidoHasProdcuto(@RequestBody RequestPedido pedido) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.savePedidoAndPedidoHasProdcuto(pedido));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("getDatoFactura/{idPedido}")
    public ResponseEntity<?> getDatosFactura(@PathVariable("idPedido")long idPedido) throws Exception {
        try {
            ProyeccionDatosFactura proyeccionDatosFactura = service.getDatosFactura(idPedido);
            return ResponseEntity.ok(proyeccionDatosFactura);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /*@GetMapping("/findUltimoPedidoByClienteId/{idCliente}")
    public ResponseEntity<?> getUltimoPedidoByClienteId(@PathVariable("idCliente") long idCliente) throws Exception {
        try {
            Pedido pedido = service.getUltimoPedidoByClienteId(idCliente);
            return ResponseEntity.ok(pedido);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }*/

}
