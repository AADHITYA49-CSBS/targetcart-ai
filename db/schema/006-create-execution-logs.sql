-- 006-create-execution-logs.sql
-- Execution logs table: tracks AI/campaign execution metrics.

CREATE TABLE IF NOT EXISTS execution_logs (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    campaign_id     BIGINT UNSIGNED NOT NULL,
    status          ENUM('STARTED', 'SUCCESS', 'FAILED') NOT NULL DEFAULT 'STARTED',
    started_at      TIMESTAMP       NOT NULL,
    completed_at    TIMESTAMP       NULL,
    duration_ms     INT UNSIGNED    NULL,
    model_name      VARCHAR(100)    NULL,
    input_tokens    INT UNSIGNED    NULL,
    output_tokens   INT UNSIGNED    NULL,
    error_message   TEXT            NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    CONSTRAINT fk_execution_logs_campaign FOREIGN KEY (campaign_id) REFERENCES campaigns (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_execution_logs_campaign_id ON execution_logs (campaign_id);
CREATE INDEX idx_execution_logs_status ON execution_logs (status);
CREATE INDEX idx_execution_logs_created_at ON execution_logs (created_at);
