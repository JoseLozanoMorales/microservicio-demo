package com.example.microservicio.microservicio_demo.controller;

import com.example.microservicio.microservicio_demo.dto.CategoriaCreateRequest;
import com.example.microservicio.microservicio_demo.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaCreateRequest req) {
        service.insertar(req); // tu inserción ya la tienes
        return ResponseEntity.ok().build();
    }
}
