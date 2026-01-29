package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.MarcaCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MarcaService {

    private final JdbcTemplate jdbc;

    public MarcaService(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT * FROM fn_listar_marcas()");
    }

    public void insertar(MarcaCreateRequest req) {
        jdbc.update(
                "CALL public.insertar_marca(?)",
                req.nombre() // o req.getNombre() si no es record
        );
    }
}