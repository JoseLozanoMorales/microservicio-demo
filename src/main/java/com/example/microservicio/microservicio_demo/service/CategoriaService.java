package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.CategoriaCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;

@Service
public class CategoriaService {

    private final SimpleJdbcCall insertarCategoria;

    public CategoriaService(JdbcTemplate jdbcTemplate) {
        this.insertarCategoria = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("public")
                .withProcedureName("insertar_categoria")
                .declareParameters(
                        new SqlParameter("p_nombre", Types.VARCHAR),
                        new SqlParameter("p_descripcion", Types.VARCHAR)
                );
    }

    public void insertar(CategoriaCreateRequest r) {
        var in = new MapSqlParameterSource()
                .addValue("p_nombre", r.nombre())
                .addValue("p_descripcion", r.descripcion());
        insertarCategoria.execute(in);
    }
}
