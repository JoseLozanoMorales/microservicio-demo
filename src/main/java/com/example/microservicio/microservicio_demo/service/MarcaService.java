package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.MarcaCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Service
public class MarcaService {

    private final JdbcTemplate jdbc;

    public MarcaService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT * FROM fn_listar_marcas()");
    }

    public void insertar(MarcaCreateRequest req) {
        // tu insert actual o llamar función/procedure
    }
}
