package com.example.demo.Controller;

import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Wrapper.RequestWrapper;
import com.example.demo.Services.ImpIngredienteDeProductoService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/ingredienteProd")
public class IngredienteDeProductoController extends GenericControllerImpl<IngredientesDeProductos, ImpIngredienteDeProductoService> {

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> updateIngredientes(@RequestBody RequestWrapper request) {
        try {


                service.saveIngredientes(request);

            return ResponseEntity.status(HttpStatus.OK).body("Ingrediente agregado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
