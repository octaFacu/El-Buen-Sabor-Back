package com.example.demo.Controller;

import com.example.demo.Entidades.Factura;
import com.example.demo.Services.ImpFacturaService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/factura")
public class FacturaController extends GenericControllerImpl<Factura,Long, ImpFacturaService> {

    /*
    @PostMapping("/createFacturaMP")
    public ResponseEntity<?> createFacturaMercadoPago(@RequestBody Factura factura) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveFacturaMP(factura));

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    */


}
