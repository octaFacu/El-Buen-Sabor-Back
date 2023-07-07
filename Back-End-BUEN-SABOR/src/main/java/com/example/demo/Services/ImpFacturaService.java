package com.example.demo.Services;

import com.example.demo.Entidades.Factura;
import org.springframework.stereotype.Service;

@Service
public class ImpFacturaService extends GenericServiceImpl<Factura,Long> implements FacturaService{
}
