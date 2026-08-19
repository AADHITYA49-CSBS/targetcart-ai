-- 002-create-products.sql
-- Products table: represents products available in the catalog.

CREATE TABLE IF NOT EXISTS products (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    sku             VARCHAR(64)     NOT NULL,
    name            VARCHAR(255)    NOT NULL,
    description     TEXT            NULL,
    category        VARCHAR(100)    NOT NULL,
    price           DECIMAL(10, 2)  NOT NULL,
    stock_quantity  INT UNSIGNED    NOT NULL DEFAULT 0,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    UNIQUE KEY uq_products_sku (sku)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_products_category ON products (category);
CREATE INDEX idx_products_price ON products (price);
