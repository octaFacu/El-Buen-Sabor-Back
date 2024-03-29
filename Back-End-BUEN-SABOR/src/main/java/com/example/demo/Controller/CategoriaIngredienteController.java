package com.example.demo.Controller;

import com.example.demo.Entidades.CategoriaIngrediente;
import com.example.demo.Security.CocineroOnly;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpCategoriaIngredienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"})
//@CrossOrigin(origins = "*")
@RequestMapping(path = "/categoriaIngrediente")
public class CategoriaIngredienteController extends GenericControllerImpl<CategoriaIngrediente,Long, ImpCategoriaIngredienteService> {

    @PublicEndpoint
    @GetMapping("/subcategoria/{idCategoriaPadre}")
    public ResponseEntity<?> buscarSubCategoria(@PathVariable Long idCategoriaPadre) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findSubCategorias(idCategoriaPadre));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PublicEndpoint
    @GetMapping("/padres")
    public ResponseEntity<?> buscarCategoriaPadres() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findCategoriaPadres());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @CocineroOnly
    @GetMapping("/padresConHijos")
    public ResponseEntity<?> buscarCategoriaPadresConHijos() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findCategoriaPadresConHijos());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
