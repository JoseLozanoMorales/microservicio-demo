package com.example.microservicio.microservicio_demo.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductosClient {

    private final WebClient webClient;

    public ProductosClient(
            WebClient.Builder builder,
            @Value("${productos.base-url}") String baseUrl
    ) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public String getProductos() {
        return webClient.get()
                .uri("/api/productos")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String editarProducto(Integer id, Object body) {
        return webClient.put()
                .uri("/api/productos/{id}", id)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String borrarProducto(Integer id) {
        return webClient.delete()
                .uri("/api/productos/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}