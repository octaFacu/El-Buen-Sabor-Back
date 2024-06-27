package com.example.demo.Services;


import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.PedidoHasProducto;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.*;
import com.example.demo.Entidades.Wrapper.RequestPedido;
import com.example.demo.Repository.IngredienteDeProductoRepository;
import com.example.demo.Repository.PedidoHasProductoRepository;
import com.example.demo.Repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class ImpProductoService extends GenericServiceImpl<Producto,Long> implements ProductoService{

    private static final Logger logger = Logger.getLogger(ImpProductoService.class.getName());

    @Autowired
    private ProductoRepository repository;

    @Autowired
    IngredienteDeProductoRepository ingredienteDeProductoRepository;

    @Autowired
    private PedidoHasProductoRepository PHPrepository;

    @Override
    public List<IngredientesDeProductos> findIngredientes(Long idProducto) throws Exception {
        try {
            List<IngredientesDeProductos> ingredientes = repository.findByIdProducto(idProducto);


            if (ingredientes == null) {
                // Inicializar ingredientes como una lista vacia para devolverlo
                ingredientes = new ArrayList<>();
            }

            //logger.severe("Ingredientes length: "+ingredientes.size());

            return ingredientes;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public Producto saveProducto(Producto producto) throws Exception {
        try {
            logger.severe("Producto a guardar: "+producto.toString());
            Producto prod = repository.save(producto);
            return prod;
        }  catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //Filtro por nombre de producto
    @Override
    public List<Producto> filtro(String filter) throws Exception {
        try{
            List<Producto> entities = repository.filtro(filter);
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Filtro paginado por nombre de producto
    @Override
    public Page<Producto> filtroPaginado(Pageable pageable, String filter) throws Exception {
        try{
            Page<Producto> entities = repository.filtroPaginado(pageable, filter);
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Filtro paginado por CATEGORIA de producto
    @Override
    public Page<Producto> filtroCategoriaPaginado(Pageable pageable, Long filter) throws Exception {
        try{
            Page<Producto> entities = repository.filtroCategoriaPaginado(pageable, filter);
            return entities;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    //Filtro por CATEGORIA de producto
    @Override
    public List<Producto> filtroCategoria(Long filter) throws Exception {
        try{
            List<Producto> entities = repository.filtroCategoria(filter);
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Page<ProyeccionRankingProductos> rankingProductosComida(Date fechaInicio, Date fechaFin, String campoDireccion,  Pageable pageable) throws Exception {
        List<ProyeccionRankingProductos> resultados = repository.rankingProductosComida(fechaInicio, fechaFin, campoDireccion);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), resultados.size());

        //  índice de inicio no exceda el tamaño de la lista de resultados
        start = Math.min(start, resultados.size());

        //  el índice de fin no sea menor que el índice de inicio
        end = Math.max(start, end);

        Page<ProyeccionRankingProductos> page = new PageImpl<>(resultados.subList(start, end), pageable, resultados.size());

        return page;
    }


    public Page<ProyeccionRankingProductos> rankingProductosBebida(Date fechaInicio, Date fechaFin, String campoDireccion,  Pageable pageable) throws Exception{
        List<ProyeccionRankingProductos> resultados = repository.rankingProductosBebidas(fechaInicio, fechaFin, campoDireccion);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), resultados.size());

        //  índice de inicio no exceda el tamaño de la lista de resultados
        start = Math.min(start, resultados.size());

        //  el índice de fin no sea menor que el índice de inicio
        end = Math.max(start, end);

        Page<ProyeccionRankingProductos> page = new PageImpl<>(resultados.subList(start, end), pageable, resultados.size());

        return page;
    }

    @Override
    public List<ProyeccionInformeConPorcentaje> graficicoInfomeGanancia(Date fechaInicio, Date fechaFin) throws Exception {
        try {
            List<ProyeccionGraficoInfomeGanancias> infomeGanancias = repository.graficoInformeGanancias(fechaInicio, fechaFin);
            List<ProyeccionInformeConPorcentaje> informeConPorcentajes = new ArrayList<>();

            // Calculamos el total de los precios
            double totalVentas = infomeGanancias.stream()
                    .mapToDouble(producto -> producto.getPrecio_total() * producto.getCantidad_pedida())
                    .sum();

            // calculamos los porcentajes de ventas para cada producto
            for (ProyeccionGraficoInfomeGanancias producto : infomeGanancias) {
                double porcentaje = ((producto.getPrecio_total() * producto.getCantidad_pedida())/ totalVentas) * 100;
                informeConPorcentajes.add(new ProyeccionInformeConPorcentaje(producto.getDenominacion(), porcentaje));
            }

            return informeConPorcentajes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ProyeccionInformeRentabilidad> graficoRentabilidad(Date fechaInicio, Date fechaFin) throws Exception {
        try{
            return repository.graficoRentabilidad(fechaInicio, fechaFin);
        }  catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ProyeccionGananciaMes> graficoGananciasMes() throws Exception {
        try{
            return repository.graficoGanancias();
        }  catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Producto> findMasVendidos() throws Exception {
        try{
            return repository.buscarMasVendidos();
        }  catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public int TraerStockProducto(Producto producto) throws Exception {
        System.out.println("Entro a recuperar stock de un producto");
        try{
            int cantidadPosible = -1;
            Set<Ingrediente> ingredientesActuales = new HashSet<>();

            //Primero busco el total de los ingredientes

                List<IngredientesDeProductos> ingredientesDeProducto = ingredienteDeProductoRepository.findIngredientesPorProductoId(producto.getId());

                for (IngredientesDeProductos ingrediente : ingredientesDeProducto) {
                    ingredientesActuales.add(ingrediente.getIngrediente());
                }


                //PARA CADA INGREDIENTE DE EL PRODUCTO, CALCULO CUANTOS PRODUCTOS SE PUEDEN COCINAR CON EL STOCK ACTUAL
                for (IngredientesDeProductos ingredienteDeProducto : ingredientesDeProducto) {

                    double cantidadNecesariaUnidad = 0;

                    //Chequear tipo de medida -- si tiene padre, calcular dividido unidades para padre
                    if(ingredienteDeProducto.getUnidadmedida().getId() != ingredienteDeProducto.getIngrediente().getUnidadmedida().getId()){
                        cantidadNecesariaUnidad = (ingredienteDeProducto.getCantidad() / ingredienteDeProducto.getUnidadmedida().getUnidadesParaPadre());
                    }else{
                        cantidadNecesariaUnidad = ingredienteDeProducto.getCantidad();
                    }


                    //PARA CADA INGREDIENTE CON SU STOCK, LE RESTO "stockARestar"
                    for (Ingrediente i : ingredientesActuales) {
                        if (i.getId() == ingredienteDeProducto.getIngrediente().getId()) {
                            System.out.println("Valido " + i.getNombre() + " con " + ingredienteDeProducto.getIngrediente().getNombre() + " para " + producto.getDenominacion() + " en stock");
                            double stockExacto = i.getStockActual() / cantidadNecesariaUnidad;
                            int cantidadPosibleIngrediente = (int) stockExacto;
                            if(cantidadPosibleIngrediente < cantidadPosible || cantidadPosible == -1) {
                                cantidadPosible = cantidadPosibleIngrediente;
                            }
                        }
                    }
                }
            return cantidadPosible;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int UpdateProducto(Producto producto) throws Exception{
        try {
            int updatedRows;
            if (producto.getImagen() == null || producto.getImagen().isEmpty()) {
                updatedRows = repository.updateProductoWithoutImage(
                        producto.getId(),
                        producto.getDenominacion(),
                        producto.getEsManufacturado(),
                        producto.getTiempoCocina(),
                        producto.getDescripcion(),
                        producto.getReceta(),
                        producto.getCostoTotal(),
                        producto.getPrecioTotal(),
                        producto.getActivo(),
                        producto.getStock(),
                        producto.getCategoriaProducto()
                );
            } else {
                updatedRows = repository.updateProductoWithImage(
                        producto.getId(),
                        producto.getDenominacion(),
                        producto.getEsManufacturado(),
                        producto.getTiempoCocina(),
                        producto.getDescripcion(),
                        producto.getReceta(),
                        producto.getCostoTotal(),
                        producto.getImagen(),
                        producto.getPrecioTotal(),
                        producto.getActivo(),
                        producto.getStock(),
                        producto.getCategoriaProducto()
                );
            }

            if (updatedRows == 0) {
                throw new Exception("Producto no encontrado y/o no actualizado");
            }

            return updatedRows;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }

}
