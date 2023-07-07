package com.example.demo.Controller;


import com.example.demo.Entidades.Producto;
import com.example.demo.Services.ImpProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    //Filtro paginado por nombre de producto
    @GetMapping("/filtroPaginado")
    public ResponseEntity<?> filtroPaginado(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ){

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Producto> productos = service.filtroPaginado(pageable, filter);
            if(productos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"Error, no hay nada que mostrar\"}");
            }
            return ResponseEntity.status(HttpStatus.OK).body(productos);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }


}
