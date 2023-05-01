package com.example.demo.Services;


import com.example.demo.Entidades.Producto;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ImpProductoService extends GenericServiceImpl<Producto,Long> implements ProductoService{
}
