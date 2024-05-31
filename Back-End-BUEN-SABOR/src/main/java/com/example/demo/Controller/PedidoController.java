package com.example.demo.Controller;

import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.Pedido;
import com.example.demo.Entidades.Proyecciones.ProyeccionDatosFactura;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import com.example.demo.Entidades.Wrapper.RequestPedido;
import com.example.demo.Helpers.InvoiceGenerator;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpFacturaService;
import com.example.demo.Services.ImpIngredienteDeProductoService;
import com.example.demo.Services.ImpPedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pedido")

public class PedidoController extends GenericControllerImpl<Pedido, Long, ImpPedidoService> {


    @Autowired
    private ImpIngredienteDeProductoService servicioIng;

    @PublicEndpoint
    @GetMapping("/estado/{estadoProducto}")
    public ResponseEntity<?> buscarPedidoEstado(@PathVariable String estadoProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPedidoPorEstado(estadoProducto));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
    @GetMapping("/delivery/{idDelivery}")
    public ResponseEntity<?> buscarPedidoDelivery(@PathVariable String idDelivery) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPedidoPorDelivery(idDelivery));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
    @PostMapping("/create")
    public ResponseEntity<?> createEntity(@RequestBody Pedido pedido) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(pedido));

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
    @GetMapping("/productos/{idProducto}")
    public ResponseEntity<?> buscarPedidoProducto(@PathVariable Long idProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPedidoProductos(idProducto));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
    @GetMapping("producto/{idPedido}")
    public ResponseEntity<List<ProyeccionProductosDePedido>> traerProductosDePedido(@PathVariable("idPedido") long idPedido) throws Exception {
        try {
            List<ProyeccionProductosDePedido> productos = service.getProductosDePedido(idPedido);
            return ResponseEntity.ok(productos);

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
    @PostMapping("/createPedidoAndProducto")
    public ResponseEntity<?> createPedidoAndPedidoHasProdcuto(@RequestBody RequestPedido pedido) throws Exception {
        try {
            RequestPedido pedidoTerminado = service.savePedidoAndPedidoHasProdcuto(pedido);
            List<String> respuesta = servicioIng.updateStockIngredientesPedido(pedidoTerminado.getPedido().getId());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @PublicEndpoint
    @GetMapping("getDatoFactura/{idPedido}")
    public ResponseEntity<?> getDatosFactura(@PathVariable("idPedido")long idPedido) throws Exception {
        try {
            ProyeccionDatosFactura proyeccionDatosFactura = service.getDatosFactura(idPedido);
            return ResponseEntity.ok(proyeccionDatosFactura);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
    @PostMapping("/validoStockPedido")
    public ResponseEntity<?> validoStockPedido(@RequestBody RequestPedido pedido) throws Exception {
        try {
            boolean validacion = service.validoStockPedido(pedido);
            return ResponseEntity.ok(validacion);
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
