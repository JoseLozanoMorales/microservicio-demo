package com.example.microservicio.microservicio_demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/api/principal")
public class PrincipalController {

    @GetMapping("/saludo")
    public String llamarDemo() {

        RestTemplate restTemplate = new RestTemplate();

        // Cambia la ruta si tu microservicio-demo tiene otro endpoint
        String respuesta = restTemplate.getForObject(
                "http://microservicio-demo:8080/api/categorias",
                String.class
        );

        return "Respuesta desde microservicio-demo: " + respuesta;
    }
}
