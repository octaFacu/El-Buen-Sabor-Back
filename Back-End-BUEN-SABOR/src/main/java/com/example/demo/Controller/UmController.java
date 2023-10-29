package com.example.demo.Controller;

import com.example.demo.Entidades.UnidadDeMedida;
import com.example.demo.Security.CocineroOnly;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpUmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/unidadDeMedida")
public class UmController extends GenericControllerImpl<UnidadDeMedida,Long, ImpUmService> {

    @CocineroOnly
    @GetMapping("/subcategorias/{idPadre}")
    public ResponseEntity<?> buscarSubcategoriasPorPadreId(@PathVariable Long idPadre) throws Exception {
        try {
            List<UnidadDeMedida> subUnidades = service.findSubUM(idPadre);
            return ResponseEntity.status(HttpStatus.OK).body(subUnidades);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
