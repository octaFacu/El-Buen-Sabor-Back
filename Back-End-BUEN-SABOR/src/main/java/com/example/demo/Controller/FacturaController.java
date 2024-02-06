package com.example.demo.Controller;

import com.example.demo.Entidades.Cliente;
import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.Favorito;
import com.example.demo.Entidades.Pedido;
import com.example.demo.Helpers.InvoiceGenerator;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/factura")
public class FacturaController extends GenericControllerImpl<Factura,Long, ImpFacturaService> {

    private final ApplicationContext context;

    @Autowired
    public FacturaController(ApplicationContext context) {
        this.context = context;
    }

    @PublicEndpoint
    @PostMapping("/create")
    public ResponseEntity<?> createFacturaMercadoPago(@RequestBody Pedido pedido) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveFactura(pedido));

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    @PublicEndpoint
    @GetMapping("/pdf/{idFactura}")
    public ResponseEntity<?> createFacturaPDF(@PathVariable("idFactura") long idFc) throws Exception {


        try {
            Factura factura = service.findById(idFc);

            InvoiceGenerator generadorPDF = context.getBean(InvoiceGenerator.class);
            generadorPDF.generatePDFInvoice(factura);

            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
    @GetMapping("/notaCredito/{idFactura}")
    public ResponseEntity<?> createNotaCreditoPDF(@PathVariable("idFactura") long idFc) throws Exception {


        try {
            Factura factura = service.findById(idFc);

            InvoiceGenerator generadorPDF = context.getBean(InvoiceGenerator.class);
            generadorPDF.generatePDFNotaCredito(factura);

            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


   /* @PublicEndpoint
    @GetMapping("/FcPorPedido/{id_pedido}")
    public ResponseEntity<?> getFCbyPedido(@PathVariable String id_pedido) throws Exception{

        try{
            Factura factura = service.getClienteXUsuarioId(id_pedido);
            return ResponseEntity.status(HttpStatus.OK).body(factura);

        } catch (Exception e) {
            // Manejar excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al procesar la solicitud.");
        }
    }*/


}
