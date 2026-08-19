-- 001-create-users.sql
-- Users table: represents customers on the e-commerce platform.

CREATE TABLE IF NOT EXISTS users (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email           VARCHAR(255)    NOT NULL,
    first_name      VARCHAR(100)    NOT NULL,
    last_name       VARCHAR(100)    NOT NULL,
    is_vip          BOOLEAN         NOT NULL DEFAULT FALSE,
    total_orders    INT UNSIGNED    NOT NULL DEFAULT 0,
    total_spend     DECIMAL(12, 2)  NOT NULL DEFAULT 0.00,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    UNIQUE KEY uq_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_users_is_vip ON users (is_vip);
CREATE INDEX idx_users_created_at ON users (created_at);
