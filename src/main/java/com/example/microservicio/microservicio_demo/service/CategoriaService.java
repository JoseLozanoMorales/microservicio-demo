package com.example.microservicio.microservicio_demo.service;

import com.example.microservicio.microservicio_demo.dto.CategoriaCreateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Service
public class CategoriaService {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcCall insertarCategoria;

    public CategoriaService(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;

        this.insertarCategoria = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("public")
                .withProcedureName("insertar_categoria")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_nombre", Types.VARCHAR),
                        new SqlParameter("p_descripcion", Types.VARCHAR)
                );
    }

    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT * FROM fn_listar_categorias()");
    }

    public void insertar(CategoriaCreateRequest req) {
        var in = new MapSqlParameterSource()
                // Si CategoriaCreateRequest es record: usa req.nombre() / req.descripcion()
                .addValue("p_nombre", req.nombre())
                .addValue("p_descripcion", req.descripcion());

        insertarCategoria.execute(in);
    }
}
