package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.MarcaCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;

@Service
public class MarcaService {

    private final SimpleJdbcCall insertarMarca;

    public MarcaService(JdbcTemplate jdbcTemplate) {
        this.insertarMarca = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("public")
                .withProcedureName("insertar_marca")
                .declareParameters(
                        new SqlParameter("p_nombre", Types.VARCHAR)
                );
    }

    public void insertar(MarcaCreateRequest r) {
        var in = new MapSqlParameterSource()
                .addValue("p_nombre", r.nombre());
        insertarMarca.execute(in);
    }
}