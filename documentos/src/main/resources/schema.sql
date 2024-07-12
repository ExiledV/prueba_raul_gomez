CREATE TABLE documentos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    order_priority VARCHAR(1) NOT NULL,
    order_date DATE NOT NULL,
    region VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    item_type VARCHAR(255) NOT NULL,
    sales_channel VARCHAR(255) NOT NULL,
    ship_date DATE NOT NULL,
    units_sold BIGINT NOT NULL,
    unit_price DOUBLE NOT NULL,
    unit_cost DOUBLE NOT NULL,
    total_revenue DOUBLE NOT NULL,
    total_cost DOUBLE NOT NULL,
    total_profit DOUBLE NOT NULL,
    PRIMARY KEY (id)
);