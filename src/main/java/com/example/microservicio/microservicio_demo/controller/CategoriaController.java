package com.example.microservicio.microservicio_demo.controller;

import com.example.microservicio.microservicio_demo.dto.CategoriaCreateRequest;
import com.example.microservicio.microservicio_demo.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;
    private final JdbcTemplate jdbc;

    public CategoriaController(CategoriaService service, JdbcTemplate jdbc) {
        this.service = service;
        this.jdbc = jdbc;
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("""
                SELECT id_categoria, nombre, descripcion
                FROM categoria
                ORDER BY id_categoria
                """);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaCreateRequest req) {
        service.insertar(req);
        return ResponseEntity.ok().build();
    }
}
