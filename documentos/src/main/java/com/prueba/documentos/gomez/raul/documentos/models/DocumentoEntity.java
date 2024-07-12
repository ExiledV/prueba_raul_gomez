package com.prueba.documentos.gomez.raul.documentos.models;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class DocumentoEntity {

    //Formato de las fechas del documento
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    //Número de datos que tiene un documento
    static final Integer DATA_LINE_LENGTH = 14;

    //Posiciones de cada dato en un csv
    static final Integer REGION_POSITION = 0;
    static final Integer COUNTRY_POSITION = 1;
    static final Integer ITEM_TYPE_POSITION = 2;
    static final Integer SALES_CHANNEL_POSITION = 3;
    static final Integer ORDER_PRIORITY_POSITION = 4;
    static final Integer ORDER_DATE_POSITION = 5;
    static final Integer ORDER_ID_POSITION = 6;
    static final Integer SHIP_DATE_POSITION = 7;
    static final Integer UNITS_SOLD_POSITION = 8;
    static final Integer UNIT_PRICE_POSITION = 9;
    static final Integer UNIT_COST_POSITION = 10;
    static final Integer TOTAL_REVENUE_POSITION = 11;
    static final Integer TOTAL_COST_POSITION = 12;
    static final Integer TOTAL_PROFIT_POSITION = 13;

    @NonNull
    private Long orderId;

    @NonNull
    private String orderPriority;

    @NonNull
    private Date orderDate;

    @NonNull
    private String region;

    @NonNull
    private String country;

    @NonNull
    private String itemType;

    @NonNull
    private String salesChannel;

    @NonNull
    private Date shipDate;

    @NonNull
    private Long unitsSold;

    @NonNull
    private Double unitPrice;

    @NonNull
    private Double unitCost;

    @NonNull
    private Double totalRevenue;

    @NonNull
    private Double totalCost;

    @NonNull
    private Double totalProfit;

    public static final DocumentoEntity generateFromCsvLine(String[] line) throws IllegalArgumentException{

        if (line.length != DATA_LINE_LENGTH) {
            throw new IllegalArgumentException("Cada fila debe tener 13 datos, se han recibido " + line.length);
        }
        

        try {

            return DocumentoEntity.builder()
                    .orderId(Long.parseLong(line[ORDER_ID_POSITION]))
                    .orderPriority(line[ORDER_PRIORITY_POSITION])
                    .orderDate(DocumentoEntity.getDateFromString(line[ORDER_DATE_POSITION]))
                    .region(line[REGION_POSITION])
                    .country(line[COUNTRY_POSITION])
                    .itemType(line[ITEM_TYPE_POSITION])
                    .salesChannel(line[SALES_CHANNEL_POSITION])
                    .shipDate(DocumentoEntity.getDateFromString(line[SHIP_DATE_POSITION]))
                    .unitsSold(Long.parseLong(line[UNITS_SOLD_POSITION]))
                    .unitPrice(Double.parseDouble(line[UNIT_PRICE_POSITION]))
                    .unitCost(Double.parseDouble(line[UNIT_COST_POSITION]))
                    .totalRevenue(Double.parseDouble(line[TOTAL_REVENUE_POSITION]))
                    .totalCost(Double.parseDouble(line[TOTAL_COST_POSITION]))
                    .totalProfit(Double.parseDouble(line[TOTAL_PROFIT_POSITION]))
                    .build();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error al convertir datos: " + e.getMessage(), e);
        }

    }

    // Getter para fecha con formateo adecuado
    public static Date getDateFromString(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
    }

    public static String dateStringFormatted(@NonNull Date date){
        return DATE_FORMAT.format(date);
    }

    public String[] formatToCsv(){
        return this.toString().split(";");
    }
 
    @Override
    public String toString() {
        
        return this.orderId + ";" + this.orderPriority + ";" + dateStringFormatted(this.orderDate) + ";" +
                this.region + ";" + this.itemType + ";" + this.salesChannel + dateStringFormatted(this.shipDate) + ";" +
                this.unitsSold + ";" + this.unitPrice + ";" + this.unitCost + ";" + this.totalRevenue + ";" +
                this.totalCost + ";" + this.totalProfit;
    }

    

}
