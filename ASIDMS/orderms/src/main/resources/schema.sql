CREATE TABLE IF NOT EXISTS outbox_event (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  aggregate_id BIGINT,
  aggregate_type VARCHAR(50),
  event_type VARCHAR(50),
  payload TEXT,
  status VARCHAR(16) DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
