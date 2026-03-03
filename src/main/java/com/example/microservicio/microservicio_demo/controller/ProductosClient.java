package com.example.microservicio.microservicio_demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductosClient {

    private final WebClient webClient;

    public ProductosClient(WebClient.Builder builder,
                           @Value("${productos.base-url}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    // A: GET /api/productos
    public String listar() {
        return webClient.get()
                .uri("/api/productos")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // A: GET /api/productos/{id}
    public String obtenerPorId(Integer id) {
        return webClient.get()
                .uri("/api/productos/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // A: POST /api/productos
    public String crear(Object body) {
        return webClient.post()
                .uri("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // A: PUT /api/productos/{id}
    public String editar(Integer id, Object body) {
        return webClient.put()
                .uri("/api/productos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // A: DELETE /api/productos/{id}
    public String borrar(Integer id) {
        return webClient.delete()
                .uri("/api/productos/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}