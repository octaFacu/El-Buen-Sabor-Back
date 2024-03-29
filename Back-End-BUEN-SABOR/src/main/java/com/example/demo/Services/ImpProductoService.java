package com.example.demo.Services;


import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.*;
import com.example.demo.Repository.PedidoHasProductoRepository;
import com.example.demo.Repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Date;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ImpProductoService extends GenericServiceImpl<Producto,Long> implements ProductoService{

    private static final Logger logger = Logger.getLogger(ImpProductoService.class.getName());

    @Autowired
    private ProductoRepository repository;

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

}
