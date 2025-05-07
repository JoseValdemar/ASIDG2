CREATE DATABASE IF NOT EXISTS order_history;
USE order_history;

DROP TABLE IF EXISTS order_history;

CREATE TABLE order_history (
    order_id BIGINT PRIMARY KEY,
    user_id BIGINT,
    user_email VARCHAR(255),
    order_date DATETIME,
    total_price DOUBLE,
    shipping_address VARCHAR(255),
    shipping_date DATETIME,
    book_titles VARCHAR(500)
);

-- Dados de teste
INSERT INTO order_history (order_id, user_id, user_email, order_date, total_price, shipping_address, shipping_date, book_titles)
VALUES 
(1, 1, 'joao@example.com', NOW(), 39.98, 'Rua Exemplo 123', NOW(), 'Livro de Teste'),
(2, 2, 'ana@example.com', NOW(), 24.90, 'Avenida Central 45', NOW(), 'Livro A, Livro B'),
(3, 3, 'carlos@example.com', NOW(), 15.50, 'Rua das Flores 7', NOW(), 'Guia do Estudante'),
(4, 4, 'maria@example.com', NOW(), 52.75, 'Travessa do Sol 12', NOW(), 'Livro X, Livro Y, Livro Z'),
(5, 5, 'rui@example.com', NOW(), 18.00, 'Praça das Oliveiras', NOW(), 'Manual de Java');
