-- 001-users.sql
-- Seed data: fictional users.

INSERT INTO users (email, first_name, last_name, is_vip, total_orders, total_spend) VALUES
('alice.johnson@example.com',  'Alice',   'Johnson',  TRUE,  12, 1489.50),
('bob.smith@example.com',      'Bob',     'Smith',    FALSE, 3,  127.99),
('carlos.rivera@example.com',  'Carlos',  'Rivera',   TRUE,  22, 3412.75),
('diana.lee@example.com',      'Diana',   'Lee',      FALSE, 7,  543.20),
('ethan.brown@example.com',    'Ethan',   'Brown',    FALSE, 1,  59.99),
('fiona.garcia@example.com',   'Fiona',   'Garcia',   TRUE,  15, 2105.30),
('george.kim@example.com',     'George',  'Kim',      FALSE, 5,  312.45),
('hannah.davis@example.com',   'Hannah',  'Davis',    FALSE, 0,  0.00);
