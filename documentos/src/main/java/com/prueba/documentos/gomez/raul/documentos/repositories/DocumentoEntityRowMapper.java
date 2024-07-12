package com.prueba.documentos.gomez.raul.documentos.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.prueba.documentos.gomez.raul.documentos.models.DocumentoEntity;

public final class DocumentoEntityRowMapper implements RowMapper<DocumentoEntity>{

    @Override
    public DocumentoEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return DocumentoEntity.builder()
                .orderId(rs.getLong("order_id"))
                .orderPriority(rs.getString("order_priority"))
                .orderDate(rs.getDate("order_date"))
                .region(rs.getString("region"))
                .country(rs.getString("country"))
                .itemType(rs.getString("item_type"))
                .salesChannel(rs.getString("sales_channel"))
                .shipDate(rs.getDate("ship_date"))
                .unitsSold(rs.getLong("units_sold"))
                .unitPrice(rs.getDouble("unit_price"))
                .unitCost(rs.getDouble("unit_cost"))
                .totalRevenue(rs.getDouble("total_revenue"))
                .totalCost(rs.getDouble("total_cost"))
                .totalProfit(rs.getDouble("total_profit"))
                .build();
    }
    
}
