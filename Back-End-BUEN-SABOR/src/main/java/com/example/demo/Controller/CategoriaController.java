package com.example.demo.Controller;

import com.example.demo.Entidades.Categoria;
import com.example.demo.Services.ImpCategoriaService;
import com.example.genericos.genericos.controllers.GenericControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/categoria")
public class CategoriaController extends GenericControllerImpl<Categoria, ImpCategoriaService> {


    @GetMapping("/subcategoria/{idCategoriaPadre}")
    public ResponseEntity<?> buscarSubCategoria(@PathVariable Long idCategoriaPadre) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findSubCategorias(idCategoriaPadre));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
