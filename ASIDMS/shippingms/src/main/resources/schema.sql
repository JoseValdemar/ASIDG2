DROP TABLE IF EXISTS shipping;

CREATE TABLE IF NOT EXISTS shipping_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  address VARCHAR(255) NOT NULL,
  city VARCHAR(100) NOT NULL,
  postal_code VARCHAR(20) NOT NULL,
  email VARCHAR(255) NOT NULL,
  shipping_date TIMESTAMP NOT NULL,
  status VARCHAR(50) DEFAULT 'pendente',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO shipping_order (first_name, last_name, address, city, postal_code, email, shipping_date, status)
VALUES 
  ('João', 'Silva', 'Rua das Flores 123', 'Braga', '4710-000', 'joao@email.com', CURRENT_TIMESTAMP, 'pendente'),
  ('Ana', 'Costa', 'Av. Central 456', 'Porto', '4000-100', 'ana@email.com', CURRENT_TIMESTAMP, 'pendente'),
  ('Pedro', 'Oliveira', 'Rua do Sol 789', 'Lisboa', '1100-200', 'pedro@email.com', CURRENT_TIMESTAMP, 'pendente');
