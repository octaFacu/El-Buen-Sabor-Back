package com.example.demo.Services;

import com.example.demo.Entidades.Pedido;

import com.example.demo.Entidades.PedidoHasProducto;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Wrapper.ProdPedWrapper;
import com.example.demo.Repository.PedidoRepository;
import com.example.demo.Repository.ProductoRepository;
import com.example.demo.Entidades.Proyecciones.ProyeccionPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;



@Service
public class ImpPedidoService extends GenericServiceImpl<Pedido,Long> implements PedidoService {

    private static final Logger logger = Logger.getLogger(ImpPedidoService.class.getName());

    @Autowired
    PedidoRepository repository;

    @Autowired
    ProductoRepository productoRepository;

    private final WebSocketService notificationService;

    public ImpPedidoService(WebSocketService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public Pedido save(Pedido pedido){
        pedido.setFechaPedido(Timestamp.valueOf(LocalDateTime.now())); // establece la fecha actual en el pedido
        LocalDate currentDate = LocalDate.now(); // tomamos el dia actual
        LocalDate pedidoDate = pedido.getFechaPedido().toLocalDateTime().toLocalDate(); // tomamos el dia del pedido
        Integer maxNumeroPedidoDia = repository.getMaxNumeroPedidoDiaByFechaPedido(Timestamp.valueOf(currentDate.atStartOfDay())); // realizamos una query para tomar el maximo numero del pedido
        if (currentDate.isEqual(pedidoDate)) { // si la fecha del pedido es igual a la del dia actual
            pedido.setNumeroPedidoDia(maxNumeroPedidoDia != null ? maxNumeroPedidoDia + 1 : 1); // si es el primer pedido se setea en 1 si no se autoIncrementa
        } else {
            pedido.setNumeroPedidoDia(1); // si es un nuevo pedido dia se setea en 1
        }
        String attributeValue = pedido.getEstado(); // Replace with the actual attribute value
        logger.severe("I'm about to send a message. "+ attributeValue);
        notificationService.sendNotification("/topic/pedidos", attributeValue);
        logger.severe("I sent the message and now i'm saving the pedido.");
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


}
