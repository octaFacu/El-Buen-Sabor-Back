package com.example.demo.Services;

import com.example.demo.Entidades.Direccion;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ImpDireccionService extends GenericServiceImpl<Direccion,Long> implements DireccionService {
}
