package com.example.demo.Controller;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.Wrapper.RequestWrapper;
import com.example.demo.Security.CocineroOnly;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpIngredienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/ingrediente")
public class IngredienteController extends GenericControllerImpl<Ingrediente,Long, ImpIngredienteService> {

    private static final Logger logger = LoggerFactory.getLogger(ImpIngredienteService.class);

    @CocineroOnly
    @GetMapping("/porCategoria/{idCategoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable Long idCategoriaIngrediente) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findByCategoriaIngrediente(idCategoriaIngrediente));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @CocineroOnly
    @PostMapping("/costo")
    public ResponseEntity<?> buscarCosto(@RequestBody RequestWrapper ingrediente) throws Exception{

        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findCosto(ingrediente));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
