package com.example.demo.Services;

import com.example.demo.Entidades.Ingrediente;
import com.example.demo.Entidades.IngredientesDeProductos;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.UnidadDeMedida;
import com.example.demo.Entidades.Wrapper.RequestWrapper;
import com.example.demo.Repository.IngredienteRepository;
import com.example.demo.Repository.ProductoRepository;
import com.example.demo.Repository.UmRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImpIngredienteService extends GenericServiceImpl<Ingrediente,Long> implements IngredienteService {

    @Autowired
     private IngredienteRepository repositorio;

    @Autowired
    private UmRepository medidaRepository;

    private ImpIngredienteDeProductoService IngredienteProdService = new ImpIngredienteDeProductoService();
    private ImpProductoService ProductoService = new ImpProductoService();


    @Override
    public List<Ingrediente> findByCategoriaIngrediente(Long idCategoriaIngrediente) throws Exception {
        try{
            List<Ingrediente> ingredientesPorCategoria = repositorio.findByCategoriaIngrediente(idCategoriaIngrediente);
            return  ingredientesPorCategoria;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public double findCosto(RequestWrapper ingrediente) throws Exception{
        try{
            Long idIng = ingrediente.getIdIngrediente();
            double cantidad = ingrediente.getCantidad();
            Long idMedida = ingrediente.getIdMedida();


            float costo = repositorio.findCosto(idIng);
            Optional<Ingrediente> ing = repositorio.findById(idIng);
            Optional<UnidadDeMedida> medida = medidaRepository.findById(idMedida);

            if (ing.isPresent() && medida.isPresent()) {
                Ingrediente ingred = ing.get();
                UnidadDeMedida unidadDeMedida = medida.get();

                if (unidadDeMedida.getId() != ingred.getUnidadmedida().getId()) {
                    double unidadesPadre = unidadDeMedida.getUnidadesParaPadre();

                    //Solo va a funcionar si es el hijo directo de la medida del ing
                    cantidad = cantidad / unidadesPadre;
                }
            }


            return costo * cantidad;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }
            //Cambiar costos de productos que tienen los ingredientes
            @Override
            @Transactional
            public Ingrediente update(Long id, Ingrediente entity) throws Exception {
                try {
                    Optional<Ingrediente> entidad = repository.findById(id);
                    Ingrediente entidadActualizar = entidad.get();

                    //Para actualizar costos
                    List<IngredientesDeProductos> ingredientesProd = new ArrayList<IngredientesDeProductos>();
                    List<Double> costo = new ArrayList<Double>();

                    //Comparar costos de ambos
                    if(entidadActualizar.getPrecioCompra() != entity.getPrecioCompra()){
                        //Si no son iguales, buscar los ingredientes de producto con mismo id
                        ingredientesProd = IngredienteProdService.getByIngredientes(id);


                        //Recorrer los ingredientes para actualizar los costos
                        for(int i = 0; i < ingredientesProd.size(); i++){
                            IngredientesDeProductos ing = ingredientesProd.get(i);

                            //Tomar el costo antiguo del producto y del ingrediente
                            double costoActual = ing.getProducto().getCostoTotal();

                            //Buscar segun la cantidad y la medida -- por FindCosto
                            double costoIngAnt = findCosto(new RequestWrapper(ing.getProducto().getId(),
                                    ing.getIngrediente().getId(), ing.getUnidadmedida().getId(), ing.getCantidad()));

                            //Sacar el costo total no incluyendo el ingrediente cambiado
                            costo.add(costoActual - costoIngAnt);

                        }

                    }

                    entidadActualizar = repository.save(entity);

                    //Comparar costos de ambos
                    if(ingredientesProd.size() != 0){

                        //Recorrer los ingredientes para actualizar los costos
                        for(int i = 0; i < ingredientesProd.size(); i++){
                            IngredientesDeProductos ing = ingredientesProd.get(i);

                            //Buscar segun la cantidad y la medida -- por FindCosto
                            double costoIngNuevo = findCosto(new RequestWrapper(ing.getProducto().getId(),
                                    ing.getIngrediente().getId(), ing.getUnidadmedida().getId(), ing.getCantidad()));

                            //Tomar el nuevo costo del producto
                            double nuevoCosto = costo.get(i) + costoIngNuevo;
                            Producto prod = ing.getProducto();

                            prod.setCostoTotal(nuevoCosto);

                            //Actualizar el producto
                            ProductoService.update(ing.getProducto().getId(), prod);
                        }

                    }


                    return entidadActualizar;
                }catch (Exception e){
                    throw new Exception(e.getMessage());
                }
            }


}
