package com.example.demo.Services;

import com.example.demo.Entidades.MetodoDePago;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ImpMetodoDePagoService extends GenericServiceImpl<MetodoDePago,Long> implements MetodoDePagoService{
}
