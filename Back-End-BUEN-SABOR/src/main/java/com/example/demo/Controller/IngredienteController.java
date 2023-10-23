package com.example.demo.Controller;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.Wrapper.RequestWrapper;
import com.example.demo.Services.ImpIngredienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/ingrediente")
public class IngredienteController extends GenericControllerImpl<Ingrediente,Long, ImpIngredienteService> {

    @GetMapping("/porCategoria/{idCategoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable Long idCategoriaIngrediente) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findByCategoriaIngrediente(idCategoriaIngrediente));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/costo/{idIngrediente}")
    public ResponseEntity<?> buscarCosto(@RequestBody RequestWrapper ingrediente) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findCosto(ingrediente));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
