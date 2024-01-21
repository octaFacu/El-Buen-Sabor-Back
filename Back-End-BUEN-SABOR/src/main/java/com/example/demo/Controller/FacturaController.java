package com.example.demo.Controller;

import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.Pedido;
import com.example.demo.Helpers.InvoiceGenerator;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpFacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/factura")
public class FacturaController extends GenericControllerImpl<Factura,Long, ImpFacturaService> {


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

            InvoiceGenerator generadorPDF = new InvoiceGenerator();
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

            InvoiceGenerator generadorPDF = new InvoiceGenerator();
            generadorPDF.generatePDFNotaCredito(factura);

            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
