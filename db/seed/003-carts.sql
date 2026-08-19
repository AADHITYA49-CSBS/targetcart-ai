-- 003-carts.sql
-- Seed data: fictional carts in various states.

-- Active carts
INSERT INTO carts (user_id, status, total_amount, abandoned_at, recovered_at) VALUES
(1, 'ACTIVE',     162.98, NULL, NULL),
(2, 'ACTIVE',      59.99, NULL, NULL),
(4, 'ACTIVE',      89.99, NULL, NULL);

-- Abandoned carts
INSERT INTO carts (user_id, status, total_amount, abandoned_at, recovered_at) VALUES
(3, 'ABANDONED',  189.98, '2026-08-15 14:30:00', NULL),
(5, 'ABANDONED',   32.99, '2026-08-16 09:15:00', NULL),
(7, 'ABANDONED',  239.98, '2026-08-17 18:45:00', NULL),
(1, 'ABANDONED',   79.98, '2026-08-18 11:20:00', NULL);

-- Recovered carts
INSERT INTO carts (user_id, status, total_amount, abandoned_at, recovered_at) VALUES
(6, 'RECOVERED',  149.99, '2026-08-10 16:00:00', '2026-08-11 10:30:00'),
(4, 'RECOVERED',   53.98, '2026-08-12 08:45:00', '2026-08-12 14:15:00'),
(3, 'RECOVERED',  109.98, '2026-08-13 20:10:00', '2026-08-14 09:00:00');

-- Expired carts
INSERT INTO carts (user_id, status, total_amount, abandoned_at, recovered_at) VALUES
(8, 'EXPIRED',    149.99, '2026-07-20 12:00:00', NULL),
(5, 'EXPIRED',     44.99, '2026-07-25 15:30:00', NULL);
