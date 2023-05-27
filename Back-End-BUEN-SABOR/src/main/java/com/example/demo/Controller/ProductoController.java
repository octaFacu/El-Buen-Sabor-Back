package com.example.demo.Controller;


import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Wrapper.RequestWrapper;
import com.example.demo.Services.ImpProductoService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/producto")
public class ProductoController extends GenericControllerImpl<Producto, ImpProductoService> {

    @GetMapping("/ingredientes/{idProducto}")
    public ResponseEntity<?> buscarIngredientes(@PathVariable Long idProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findIngredientes(idProducto));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @PutMapping("/{idProducto}")
    public ResponseEntity<?> updateProducto(@PathVariable Long idProducto, @RequestBody RequestWrapper request) {
        try {
            //Estamos buscando los ingredientes que ya estan vinculados en la tabla intermedia con
            //el producto que le estamos pasando
            List<IngredientesDeProductos> ingredientes = service.findIngredientes(request.getProducto().getId());
            if(ingredientes.size() != 0){
                service.deleteIngredientes(request.getProducto().getId());
            }
            if(request.getIngredientes().size() != 0){
                service.saveIngredientes(request.getIngredientes());
            }

            service.update(request.getProducto().getId(), request.getProducto());


            return ResponseEntity.status(HttpStatus.OK).body("Producto creado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
