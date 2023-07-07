package com.example.demo.Services;

import com.example.demo.Entidades.Pedido;
import com.example.demo.Repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class ImpPedidoService extends GenericServiceImpl<Pedido,Long> implements PedidoService {

    @Autowired
    PedidoRepository repository;

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
        return repository.save(pedido);
    }



}
