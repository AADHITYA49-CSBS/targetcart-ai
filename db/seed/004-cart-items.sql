-- 004-cart-items.sql
-- Seed data: cart items for seeded carts.

-- Cart 1: ACTIVE, user 1, total 162.98
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 149.99),
(1, 2, 1, 12.99);

-- Cart 2: ACTIVE, user 2, total 59.99
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(2, 5, 1, 59.99);

-- Cart 3: ACTIVE, user 4, total 89.99
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(3, 6, 1, 89.99);

-- Cart 4: ABANDONED, user 3, total 189.98
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(4, 1, 1, 149.99),
(4, 3, 1, 39.99);

-- Cart 5: ABANDONED, user 5, total 32.99
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(5, 10, 1, 32.99);

-- Cart 6: ABANDONED, user 7, total 224.97
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(6, 6, 1, 89.99),
(6, 1, 1, 149.99);

-- Cart 7: ABANDONED, user 1, total 74.98
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(7, 5, 1, 59.99),
(7, 12, 1, 19.99);

-- Cart 8: RECOVERED, user 6, total 149.99
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(8, 1, 1, 149.99);

-- Cart 9: RECOVERED, user 4, total 59.98
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(9, 7, 1, 34.99),
(9, 8, 1, 18.99);

-- Cart 10: RECOVERED, user 3, total 104.98
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(10, 6, 1, 89.99),
(10, 12, 1, 19.99);

-- Cart 11: EXPIRED, user 8, total 149.99
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(11, 1, 1, 149.99);

-- Cart 12: EXPIRED, user 5, total 44.99
INSERT INTO cart_items (cart_id, product_id, quantity, unit_price) VALUES
(12, 9, 1, 44.99);
