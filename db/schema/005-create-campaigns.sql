-- 005-create-campaigns.sql
-- Campaigns table: represents recovery campaigns generated for customers/carts.

CREATE TABLE IF NOT EXISTS campaigns (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    cart_id         BIGINT UNSIGNED NOT NULL,
    user_id         BIGINT UNSIGNED NOT NULL,
    campaign_type   ENUM('ABANDONED_CART') NOT NULL DEFAULT 'ABANDONED_CART',
    subject         VARCHAR(255)    NOT NULL,
    content         TEXT            NOT NULL,
    status          ENUM('DRAFT', 'GENERATED', 'APPROVED', 'SENT', 'FAILED', 'CANCELLED') NOT NULL DEFAULT 'DRAFT',
    generated_at    TIMESTAMP       NULL,
    sent_at         TIMESTAMP       NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    CONSTRAINT fk_campaigns_cart FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
    CONSTRAINT fk_campaigns_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_campaigns_user_id ON campaigns (user_id);
CREATE INDEX idx_campaigns_cart_id ON campaigns (cart_id);
CREATE INDEX idx_campaigns_status ON campaigns (status);
CREATE INDEX idx_campaigns_created_at ON campaigns (created_at);
