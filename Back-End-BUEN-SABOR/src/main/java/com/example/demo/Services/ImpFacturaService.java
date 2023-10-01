package com.example.demo.Services;
import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.MetodoDePago;
import com.example.demo.Repository.FacturaRepository;
import com.example.demo.Repository.MetodoDePagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpFacturaService extends GenericServiceImpl<Factura,Long> implements FacturaService{

    @Autowired
    MetodoDePagoRepository metodoDePagoRepository;

    @Autowired
    FacturaRepository facturaRepository;

    @Override
    public Factura saveFacturaMP(Factura factura) throws Exception {

        try{

            Factura facturaEntrante = factura;

            List<MetodoDePago> metdodosDePago = metodoDePagoRepository.findAll();

            //Le asigno el metodo de pago de mercado pago a la factura
            for (int i = 0; i < metdodosDePago.size(); i++){
                if(metdodosDePago.get(i).getTipo().toLowerCase().equals("mercado pago")){
                    System.out.println("Si es mercado pago");
                    facturaEntrante.setMetodoDePago(metdodosDePago.get(i));
                    break;
                }
            }

            //Genero el numero de factura a la misma

            long numeroFactura;
            Factura ultimaFactura = facturaRepository.findUltimaFactura();

            if(ultimaFactura != null){
                numeroFactura = Long.parseLong(ultimaFactura.getNumeroFactura()) + 1;
                facturaEntrante.setNumeroFactura(String.valueOf(numeroFactura));
            }else{
                facturaEntrante.setNumeroFactura("000000000001");
            }

            Factura facturaTerminada = facturaRepository.save(facturaEntrante);

            return facturaTerminada;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
