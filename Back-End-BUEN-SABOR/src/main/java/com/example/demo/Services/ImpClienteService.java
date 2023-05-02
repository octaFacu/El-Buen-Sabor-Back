package com.example.demo.Services;

import com.example.demo.Entidades.Cliente;
import com.example.genericos.genericos.services.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ImpClienteService extends GenericServiceImpl<Cliente, Long> implements ClienteService {
}
