package com.example.microservicio.microservicio_demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// 1. Marca la clase como un controlador que gestiona peticiones REST
@RestController public class HolaMundoController {
    // 2. Mapea esta función para responder a peticiones GET en la ruta /hola
    @GetMapping("/hola")
    public String decirHola() {
        // 3. Devuelve la respuesta, que Spring Boot convierte a texto plano (o JSON por defecto)
        return "¡Hola! Este es mi primer microservicio con Spring Boot.";
    }
}