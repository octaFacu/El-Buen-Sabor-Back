package com.example.demo.Controller;


import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Wrapper.RequestWrapper;
import com.example.demo.Services.ImpProductoService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/producto")
public class ProductoController extends GenericControllerImpl<Producto, ImpProductoService> {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);


    @GetMapping("/ingredientes/{idProducto}")
    public ResponseEntity<?> buscarIngredientes(@PathVariable Long idProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findIngredientes(idProducto));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
