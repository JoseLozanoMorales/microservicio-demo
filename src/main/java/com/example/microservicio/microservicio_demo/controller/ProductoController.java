package com.example.microservicio.microservicio_demo.controller;

import com.example.microservicio.microservicio_demo.dto.ProductoCreateRequest;
import com.example.microservicio.microservicio_demo.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProductoCreateRequest req) {
        service.insertar(req);
        return ResponseEntity.ok().build();
    }
}
