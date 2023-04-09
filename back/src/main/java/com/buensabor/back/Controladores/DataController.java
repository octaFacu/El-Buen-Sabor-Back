package com.buensabor.back.Controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @GetMapping("/api/data")
    public String getData() {
        return "Hello from Spring Boot!";
    }
}






