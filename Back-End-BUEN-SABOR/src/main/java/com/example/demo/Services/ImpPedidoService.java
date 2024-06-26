package com.example.demo.Services;

import com.example.demo.Entidades.*;

import com.example.demo.Entidades.Proyecciones.ProyeccionDatosFactura;
import com.example.demo.Entidades.Wrapper.RequestPedido;
import com.example.demo.Repository.*;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;



@Service
public class ImpPedidoService extends GenericServiceImpl<Pedido,Long> implements PedidoService {

    private static final Logger logger = Logger.getLogger(ImpPedidoService.class.getName());

    @Autowired
    PedidoRepository repository;

    @Autowired
    PedidoHasProductoRepository pedidoHasProductoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    IngredienteDeProductoRepository ingredienteDeProductoRepository;

    private final WebSocketService notificationService;

    public ImpPedidoService(WebSocketService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public Pedido save(Pedido pedido){
        /*pedido.setFechaPedido(Timestamp.valueOf(LocalDateTime.now())); // establece la fecha actual en el pedido
        LocalDate currentDate = LocalDate.now(); // tomamos el dia actual
        LocalDate pedidoDate = pedido.getFechaPedido().toLocalDateTime().toLocalDate(); // tomamos el dia del pedido
        Integer maxNumeroPedidoDia = repository.getMaxNumeroPedidoDiaByFechaPedido(Timestamp.valueOf(currentDate.atStartOfDay())); // realizamos una query para tomar el maximo numero del pedido
        if (currentDate.isEqual(pedidoDate)) { // si la fecha del pedido es igual a la del dia actual
            pedido.setNumeroPedidoDia(maxNumeroPedidoDia != null ? maxNumeroPedidoDia + 1 : 1); // si es el primer pedido se setea en 1 si no se autoIncrementa
        } else {
            pedido.setNumeroPedidoDia(1); // si es un nuevo pedido dia se setea en 1
        }*/
        String attributeValue = pedido.getEstado(); // Replace with the actual attribute value
        logger.severe("I'm about to send a message. "+ attributeValue);
        notificationService.sendNotification("/topic/pedidos", attributeValue);
        logger.severe("I sent the message and now i'm saving the pedido.");
        return repository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido update(Long Id, Pedido pedido){

        String attributeValue = pedido.getEstado(); // Replace with the actual attribute value
        logger.severe("I'm about to send a message. "+ attributeValue);
        notificationService.sendNotification("/topic/pedidos", attributeValue);
        logger.severe("I sent the message and now i'm updating the pedido.");
        return repository.save(pedido);
    }

    public List<Pedido> buscarPedidoPorEstado(String estadoProducto) throws Exception{
        try{
            List<Pedido> pedidosEstado = repository.buscarPedidoPorEstado(estadoProducto);

            return pedidosEstado;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public List<Pedido> buscarPedidoPorDelivery(String idDelivery) throws Exception{
        try{
            List<Pedido> pedidosEstado = repository.buscarPedidoPorDelivery(idDelivery);

            return pedidosEstado;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public List<PedidoHasProducto> buscarPedidoProductos(Long idPedido) throws Exception{

        try{
            List<PedidoHasProducto> productosPedido = repository.buscarPedidoProductos(idPedido);

            return productosPedido;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ProyeccionProductosDePedido> getProductosDePedido(long idPedido) throws Exception {
        try{
            return repository.getProductosDePedido(idPedido);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean validoStockPedido(RequestPedido pedido) throws Exception {
        System.out.println("Entro a validar stock");
        try{

            //Utilizo Set porque no permite elementos repetidos
            boolean terminoValidacion = true;
            Set<Ingrediente> ingredientesActuales = new HashSet<>();

            //Primero busco el total de los ingredientes
            for (PedidoHasProducto pedidoHasProducto : pedido.getPedidoHasProducto()) {
                Producto producto = pedidoHasProducto.getProducto();

                List<IngredientesDeProductos> ingredientesDeProducto = ingredienteDeProductoRepository.findIngredientesPorProductoId(producto.getId());

                for (IngredientesDeProductos ingrediente : ingredientesDeProducto) {
                    ingredientesActuales.add(ingrediente.getIngrediente());
                }
            }

            //PARA CADA PRODUCTO CON SU CANTIDAD
            for (PedidoHasProducto pedidoHasProducto : pedido.getPedidoHasProducto()) {

                int cantidad = pedidoHasProducto.getCantidad();
                Producto producto = pedidoHasProducto.getProducto();

                List<IngredientesDeProductos> ingredientesDeProducto = ingredienteDeProductoRepository.findIngredientesPorProductoId(producto.getId());

                //PARA CADA INGREDIENTE DE EL PRODUCTO, CALCULO CUANTO STOCK HAY QUE RESTAR
                for (IngredientesDeProductos ingredienteDeProducto : ingredientesDeProducto) {

                    double stockARestar = 0;

                    //Chequear tipo de medida -- si tiene padre, calcular dividido unidades para padre
                    if(ingredienteDeProducto.getUnidadmedida().getId() != ingredienteDeProducto.getIngrediente().getUnidadmedida().getId()){
                        stockARestar = cantidad * (ingredienteDeProducto.getCantidad() / ingredienteDeProducto.getUnidadmedida().getUnidadesParaPadre());
                    }else{
                        stockARestar = cantidad * ingredienteDeProducto.getCantidad();
                    }


                    //PARA CADA INGREDIENTE CON SU STOCK, LE RESTO "stockARestar"
                    for (Ingrediente i : ingredientesActuales) {
                        if (i.getId() == ingredienteDeProducto.getIngrediente().getId()) {
                            System.out.println("Valido " + i.getNombre() + " con " + ingredienteDeProducto.getIngrediente().getNombre() + " para " + producto.getDenominacion() + " en stock");
                            double stockRestado = i.getStockActual() - stockARestar;
                            i.setStockActual(stockRestado);
                            if(stockRestado == 0 || stockRestado < 0) {
                                terminoValidacion = false;
                                break;
                            }
                        }
                    }
                }
            }

            return terminoValidacion;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /*@Override
    public Pedido getUltimoPedidoByClienteId(long idCliente) throws Exception {
        try{
            return repository.findUltimoPedidoByClienteId(idCliente);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }*/

    @Override
    public RequestPedido savePedidoAndPedidoHasProdcuto(RequestPedido pedido) throws Exception {

        try{
            //Creo un nuevo pedido para evitar errores al momento de asignar valores desde el back, ej: fecha del pedido, numero pedido dia
            RequestPedido pedidoEntrante = pedido;

            //Agrega la fecha del pedido
            Instant instant = Instant.now(); // Obtener la instancia actual de Instant
            Timestamp fechaPedido = Timestamp.from(instant);
            pedidoEntrante.getPedido().setFechaPedido(fechaPedido);

            //Agrega el numero pedido dia
            LocalDate currentDate = LocalDate.now(); // tomamos el dia actual
            String[] pedidoDate = pedidoEntrante.getPedido().getFechaPedido().toString().split(" "); // tomamos el dia del pedido
            Integer maxNumeroPedidoDia = repository.getMaxNumeroPedidoDiaByFechaPedido(Timestamp.valueOf(currentDate.atStartOfDay())); // realizamos una query para tomar el maximo numero del pedido

            if (currentDate.toString().equals(pedidoDate[0])) { // si la fecha del pedido es igual a la del dia actual
                pedidoEntrante.getPedido().setNumeroPedidoDia(maxNumeroPedidoDia != null ? maxNumeroPedidoDia + 1 : 1); // si es el primer pedido se setea en 1 si no se autoIncrementa
            } else {
                pedidoEntrante.getPedido().setNumeroPedidoDia(1); // si es un nuevo pedido dia se setea en 1
            }

            List<PedidoHasProducto> pedidoHasProducto = pedidoEntrante.getPedidoHasProducto();

            //Creo el tiempo estimado de entrega del pedido
            Calendar cal = Calendar.getInstance();
            // Tiempo que sumo para calcular la hora estimada final
            int hSuma = 00;
            int mSuma = 00;

            for (int i = 0; i < pedidoHasProducto.size(); i++) {
                String tiempoCocina = pedidoHasProducto.get(i).getProducto().getTiempoCocina();

                if (tiempoCocina != null && !tiempoCocina.isEmpty()) {
                    int horas = Integer.parseInt(tiempoCocina.substring(0, 2));
                    int minutos = Integer.parseInt(tiempoCocina.substring(3, 5));

                    if (hSuma < horas) {
                        hSuma = horas;
                    } else if (mSuma < minutos) {
                        mSuma = minutos;
                    }
                }
            }

            cal.add(Calendar.MINUTE, mSuma);
            cal.add(Calendar.HOUR_OF_DAY, hSuma);
            //Si el pedido es un envio se le agrega mas tiempo
            if(pedidoEntrante.getPedido().getEsEnvio()){
                cal.add(Calendar.MINUTE, 15);
            }
            // Obtener la hora estimada final como objeto Time y lo asigno al pedidoEntrante
            Time tiempoEstimado = new Time(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), 00);
            pedidoEntrante.getPedido().setHoraEstimada(tiempoEstimado);

            //Guardo el pedido completado sin sus productos
            //Cambiar la llamada al repositorio por una llamada intermedia al servicio del save?
            //Pedido pedidoCliente = repository.save(pedidoEntrante.getPedido());
            Pedido pedidoCliente = save(pedidoEntrante.getPedido());

            //Asigo el pedido a los productos para persistirlos
            for (int i = 0; i < pedidoHasProducto.size(); i++){

                PedidoHasProducto php = pedidoHasProducto.get(i);
                php.setPedido(pedidoCliente);

                pedidoHasProductoRepository.save(php);
            }

            RequestPedido pedidoTerminado = new RequestPedido();
            pedidoTerminado.setPedido(pedidoCliente);
            pedidoTerminado.setPedidoHasProducto(pedidoHasProducto);

            return pedidoTerminado;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ProyeccionDatosFactura getDatosFactura(long idPedido) throws Exception {
        try {
            return repository.getDatosFactura(idPedido);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
