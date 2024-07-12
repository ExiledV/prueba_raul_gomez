package com.prueba.documentos.gomez.raul.documentos.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.prueba.documentos.gomez.raul.documentos.models.DocumentoEntity;
import com.prueba.documentos.gomez.raul.documentos.utils.PaginationRequest;

@Repository
public class DocumentoRepository {

    private final JdbcTemplate jdbcTemplate;

    public DocumentoRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(DocumentoEntity documentoEntity) {

        String sql = "INSERT INTO documentos (order_id, order_priority, order_date, region, country, item_type, sales_channel, ship_date, units_sold, unit_price, unit_cost, total_revenue, total_cost, total_profit) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                documentoEntity.getOrderId(),
                documentoEntity.getOrderPriority(),
                //Convertimos a sql.Date
                new Date(documentoEntity.getOrderDate().getTime()),
                documentoEntity.getRegion(),
                documentoEntity.getCountry(),
                documentoEntity.getItemType(),
                documentoEntity.getSalesChannel(),
                new Date(documentoEntity.getShipDate().getTime()),
                documentoEntity.getUnitsSold(),
                documentoEntity.getUnitPrice(),
                documentoEntity.getUnitCost(),
                documentoEntity.getTotalRevenue(),
                documentoEntity.getTotalCost(),
                documentoEntity.getTotalProfit());
    }

    public void saveAll(List<DocumentoEntity> documentosEntity) {
        String sql = "INSERT INTO documentos (order_id, order_priority, order_date, region, country, item_type, sales_channel, ship_date, units_sold, unit_price, unit_cost, total_revenue, total_cost, total_profit) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                DocumentoEntity documentoEntity = documentosEntity.get(i);
                ps.setLong(1, documentoEntity.getOrderId());
                ps.setString(2, documentoEntity.getOrderPriority());
                ps.setDate(3, new Date(documentoEntity.getOrderDate().getTime()));
                ps.setString(4, documentoEntity.getRegion());
                ps.setString(5, documentoEntity.getCountry());
                ps.setString(6, documentoEntity.getItemType());
                ps.setString(7, documentoEntity.getSalesChannel());
                ps.setDate(8, new Date(documentoEntity.getShipDate().getTime()));
                ps.setLong(9, documentoEntity.getUnitsSold());
                ps.setDouble(10, documentoEntity.getUnitPrice());
                ps.setDouble(11, documentoEntity.getUnitCost());
                ps.setDouble(12, documentoEntity.getTotalRevenue());
                ps.setDouble(13, documentoEntity.getTotalCost());
                ps.setDouble(14, documentoEntity.getTotalProfit());
            }

            @Override
            public int getBatchSize() {
                return documentosEntity.size();
            }
        });
    }

    public List<DocumentoEntity> findAll(PaginationRequest<DocumentoEntity> page) {
        String sql = "SELECT * FROM documentos";

        return jdbcTemplate.query(page.getQueryBySql(sql), new DocumentoEntityRowMapper(), page.getSize(), page.getOffset());
    }

}
