package com.example.microservicio.microservicio_demo.dto;

public record ProductoImagenCreateRequest(
        String url,
        Boolean portada,
        Boolean galeria
) {}