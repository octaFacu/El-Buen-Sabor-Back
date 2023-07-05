package com.example.demo.Controller;


import com.example.demo.Entidades.Producto;
import com.example.demo.Services.ImpProductoService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/producto")
public class ProductoController extends GenericControllerImpl<Producto,Long, ImpProductoService> {

    @GetMapping("/ingredientes/{idProducto}")
    public ResponseEntity<?> buscarIngredientes(@PathVariable Long idProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findIngredientes(idProducto));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
