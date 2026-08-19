-- 003-create-carts.sql
-- Carts table: represents shopping carts owned by users.

CREATE TABLE IF NOT EXISTS carts (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id         BIGINT UNSIGNED NOT NULL,
    status          ENUM('ACTIVE', 'ABANDONED', 'RECOVERED', 'EXPIRED') NOT NULL DEFAULT 'ACTIVE',
    total_amount    DECIMAL(12, 2)  NOT NULL DEFAULT 0.00,
    abandoned_at    TIMESTAMP       NULL,
    recovered_at    TIMESTAMP       NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    CONSTRAINT fk_carts_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_carts_user_id ON carts (user_id);
CREATE INDEX idx_carts_status ON carts (status);
CREATE INDEX idx_carts_abandoned ON carts (abandoned_at);
