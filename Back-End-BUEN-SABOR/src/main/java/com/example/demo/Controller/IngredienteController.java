package com.example.demo.Controller;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Services.ImpIngredienteService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/ingrediente")
public class IngredienteController extends GenericControllerImpl<Ingrediente, ImpIngredienteService> {

    @GetMapping("/porCategoria/{idCategoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable Long idCategoria) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findByCategoria(idCategoria));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
