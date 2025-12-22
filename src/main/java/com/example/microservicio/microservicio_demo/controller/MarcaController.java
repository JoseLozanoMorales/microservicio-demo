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
    private final JdbcTemplate jdbc;

    public MarcaController(MarcaService service, JdbcTemplate jdbc) {
        this.service = service;
        this.jdbc = jdbc;
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("""
                SELECT id_marca, nombre
                FROM marca
                ORDER BY id_marca
                """);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MarcaCreateRequest req) {
        service.insertar(req);
        return ResponseEntity.ok().build();
    }
}
