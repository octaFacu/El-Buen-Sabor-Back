package com.example.demo.Controller;


import com.example.demo.Entidades.Producto;

import com.example.demo.Entidades.Wrapper.RequestPedido;
import com.example.demo.Security.AdminOnly;
import com.example.demo.Security.CocineroOnly;
import com.example.demo.Security.PublicEndpoint;
import com.example.demo.Services.ImpProductoService;

import com.example.demo.Entidades.Proyecciones.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

import java.util.Date;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/producto")
public class ProductoController extends GenericControllerImpl<Producto,Long, ImpProductoService> {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @PublicEndpoint
    @GetMapping("/ingredientes/{idProducto}")
    public ResponseEntity<?> buscarIngredientes(@PathVariable Long idProducto) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findIngredientes(idProducto));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }




    //Filtro por nombre de producto
    @PublicEndpoint
    @GetMapping("/filtro")
    public ResponseEntity<?> filtro(
            @RequestParam(required = false) String filter
    ){

        try {
            List<Producto> productos = service.filtro(filter);
            if(productos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"Error, no hay nada que mostrar\"}");
            }
            return ResponseEntity.status(HttpStatus.OK).body(productos);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }

    //Filtro paginado por nombre de producto
    @PublicEndpoint
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
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"Error, no hay nada que mostrar\"}");
            }
            return ResponseEntity.status(HttpStatus.OK).body(productos);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }

    //Filtro paginado por CATEGORIA de producto
    @PublicEndpoint
    @GetMapping("/filtroCategoriaPaginado")
    public ResponseEntity<?> filtroCategoriaPaginado(
            @RequestParam(required = false) Long filter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ){

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Producto> productos = service.filtroCategoriaPaginado(pageable, filter);
            if(productos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"Error, no hay nada que mostrar\"}");
            }
            return ResponseEntity.status(HttpStatus.OK).body(productos);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }

    //Filtro por CATEGORIA de producto
    @PublicEndpoint
    @GetMapping("/filtroCategoria")
    public ResponseEntity<?> filtroCategoria(
            @RequestParam(required = false) Long filter
    ){

        try {
            List<Producto> productos = service.filtroCategoria(filter);
            if(productos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"Error, no hay nada que mostrar\"}");
            }
            return ResponseEntity.status(HttpStatus.OK).body(productos);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }

    @PublicEndpoint
    @PostMapping("/stockProducto")
    public ResponseEntity<?> StockProducto(@RequestBody Producto producto){
        try{
            int stockPosible = service.TraerStockProducto(producto);

            return ResponseEntity.status(HttpStatus.OK).body(stockPosible);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }

    @AdminOnly
    @GetMapping("/rankingProductos/comida")
    public ResponseEntity<?> rankingProductosComida(
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            @RequestParam(required = false) String direccionOrden,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProyeccionRankingProductos> productos = service.rankingProductosComida(fechaInicio,fechaFin,direccionOrden, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }

    @AdminOnly
    @GetMapping("/rankingProductos/bebida")
    public ResponseEntity<?> rankingProductosBebidas(
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            @RequestParam(required = false) String direccionOrden,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProyeccionRankingProductos> productos = service.rankingProductosBebida(fechaInicio,fechaFin,direccionOrden, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }

    @AdminOnly
    @GetMapping("/informeGanancias/grafico")
    public ResponseEntity<?> generarGraficoInformeGanancias(@RequestParam(required = false) Date fechaInicio, @RequestParam(required = false) Date fechaFin){
        try {

            List<ProyeccionInformeConPorcentaje> productos = service.graficicoInfomeGanancia(fechaInicio,fechaFin);
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde\"}");
        }
    }

    @AdminOnly
    @GetMapping("/informeGanancias/graficoRentabilidad")
    public ResponseEntity<?> generarGraficoRentabilidad(@RequestParam(required = false) Date fechaInicio, @RequestParam(required = false) Date fechaFin) throws Exception {
        try {

            List<ProyeccionInformeRentabilidad> productos = service.graficoRentabilidad(fechaInicio,fechaFin);
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        }catch (Exception e){
          throw new Exception(e.getMessage());
        }
    }

    @AdminOnly
    @GetMapping("/informeGanancias/graficoGananciaMes")
    public ResponseEntity<?> generarInformeGananciasPorMes() throws Exception {
        try {

            List<ProyeccionGananciaMes> productos = service.graficoGananciasMes();
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Retorna los prodcutos mas vendidos, con un maximo de 3
    @PublicEndpoint
    @GetMapping("/masVendidos")
    public ResponseEntity<?> masVendidos() throws Exception {
        try {
            List<Producto> productos = service.findMasVendidos();
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
