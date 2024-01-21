package com.example.demo.Services;
import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.MetodoDePago;
import com.example.demo.Entidades.Pedido;
import com.example.demo.Repository.FacturaRepository;
import com.example.demo.Repository.MetodoDePagoRepository;
import com.example.demo.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpFacturaService extends GenericServiceImpl<Factura,Long> implements FacturaService{

    @Autowired
    MetodoDePagoRepository metodoDePagoRepository;

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public Factura saveFacturaMP(Pedido pedido) throws Exception {

        try{

            Factura factura = new Factura();

            //Asigno el tipo de factura
            factura.setTipo("C");
            //Asigno el activo de factura
            factura.setActivo(true);

            List<MetodoDePago> metdodosDePago = metodoDePagoRepository.findAll();

            //Le asigno el metodo de pago de mercado pago a la factura
            for (int i = 0; i < metdodosDePago.size(); i++){
                if(metdodosDePago.get(i).getTipo().toLowerCase().equals("mercado pago")){
                    System.out.println("Si es mercado pago");
                    factura.setMetodoDePago(metdodosDePago.get(i));
                    break;
                }
            }

            //Genero el numero de factura a la misma
            long numeroFactura;
            Factura ultimaFactura = facturaRepository.findUltimaFactura();

            if(ultimaFactura != null){
                numeroFactura = Long.parseLong(ultimaFactura.getNumeroFactura()) + 1;
                factura.setNumeroFactura(String.format("%12s", String.valueOf(numeroFactura)).replace(' ', '0'));
            }else{
                factura.setNumeroFactura("000000000001");
            }

            //Asigno el pedido a la factura
            factura.setPedido(pedido);

            //Asigno si es que hay monto de descuento
            if (!pedido.getEsEnvio()){
                factura.setMontoDescuento((pedido.getPrecioTotal() * 10) / 90);
            }else{
                factura.setMontoDescuento(0.0);
            }

            Factura facturaTerminada = facturaRepository.save(factura);

            return facturaTerminada;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Factura saveFactura(Pedido pedido) throws Exception {

        try{

            Factura factura = new Factura();

            //Asigno el tipo de factura
            factura.setTipo("C");
            //Asigno el activo de factura
            factura.setActivo(true);

            List<MetodoDePago> metdodosDePago = metodoDePagoRepository.findAll();

            //Le asigno el metodo de pago de mercado pago a la factura
            for (int i = 0; i < metdodosDePago.size(); i++){
                if(metdodosDePago.get(i).getTipo().toLowerCase().equals("efectivo")){
                    factura.setMetodoDePago(metdodosDePago.get(i));
                    break;
                }
            }

            //Genero el numero de factura a la misma
            long numeroFactura;
            Factura ultimaFactura = facturaRepository.findUltimaFactura();

            if(ultimaFactura != null){
                numeroFactura = Long.parseLong(ultimaFactura.getNumeroFactura()) + 1;
                factura.setNumeroFactura(String.format("%12s", String.valueOf(numeroFactura)).replace(' ', '0'));
            }else{
                factura.setNumeroFactura("000000000001");
            }

            //Asigno el pedido a la factura
            factura.setPedido(pedido);

            //Asigno si es que hay monto de descuento
            if (!pedido.getEsEnvio()){
                factura.setMontoDescuento((pedido.getPrecioTotal() * 10) / 90);
            }else{
                factura.setMontoDescuento(0.0);
            }

            Factura facturaTerminada = facturaRepository.save(factura);

            return facturaTerminada;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
