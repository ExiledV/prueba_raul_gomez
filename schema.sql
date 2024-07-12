CREATE TABLE documentos (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    order_priority VARCHAR(1) NOT NULL,
    order_date DATE NOT NULL,
    region VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    item_type VARCHAR(255) NOT NULL,
    sales_channel VARCHAR(255) NOT NULL,
    ship_date DATE NOT NULL,
    units_sold BIGINT NOT NULL,
    unit_price DOUBLE PRECISION NOT NULL,
    unit_cost DOUBLE PRECISION NOT NULL,
    total_revenue DOUBLE PRECISION NOT NULL,
    total_cost DOUBLE PRECISION NOT NULL,
    total_profit DOUBLE PRECISION NOT NULL
);
