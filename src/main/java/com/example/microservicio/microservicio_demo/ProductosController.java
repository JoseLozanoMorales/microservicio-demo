package com.example.microservicio.microservicio_demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductosController {

    private final JdbcTemplate jdbc;

    public ProductosController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/productos")
    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT * FROM public.fn_listar_productos()");
    }
}