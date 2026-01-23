package com.example.microservicio.microservicio_demo.controller;

import com.example.microservicio.microservicio_demo.dto.MarcaCreateRequest;
import com.example.microservicio.microservicio_demo.service.MarcaService;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService service;

    public MarcaController(MarcaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MarcaCreateRequest req) {
        service.insertar(req);
        return ResponseEntity.ok().build();
    }

}

