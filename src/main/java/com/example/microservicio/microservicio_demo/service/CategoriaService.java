package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.CategoriaCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoriaService {

    private final JdbcTemplate jdbc;

    public CategoriaService(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT * FROM fn_listar_categorias()");
    }

    public void insertar(CategoriaCreateRequest req) {
        jdbc.update(
                "CALL public.insertar_categoria(?, ?)",
                req.nombre(),
                req.descripcion()
        );
    }
}