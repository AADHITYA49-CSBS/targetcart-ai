-- 002-products.sql
-- Seed data: fictional products.

INSERT INTO products (sku, name, description, category, price, stock_quantity) VALUES
('ELEC-001',  'Wireless Bluetooth Headphones',  'Noise-cancelling over-ear headphones with 30-hour battery',  'Electronics',   149.99, 150),
('ELEC-002',  'USB-C Charging Cable',            '2-meter braided USB-C to USB-C cable',                      'Electronics',   12.99,  500),
('ELEC-003',  'Portable Power Bank',             '20000mAh fast-charging power bank',                         'Electronics',   39.99,  200),
('CLOTH-001', 'Classic Cotton T-Shirt',          '100% organic cotton crew-neck t-shirt',                      'Clothing',      24.99,  300),
('CLOTH-002', 'Slim Fit Denim Jeans',            'Stretch denim jeans with slim fit',                          'Clothing',      59.99,  120),
('CLOTH-003', 'Waterproof Running Jacket',       'Lightweight breathable running jacket',                      'Clothing',      89.99,  80),
('HOME-001',  'Ceramic Coffee Mug Set',          'Set of 4 handcrafted ceramic mugs',                          'Home & Kitchen', 34.99,  250),
('HOME-002',  'Scented Soy Candle',              'Lavender and vanilla scented soy candle, 8oz',               'Home & Kitchen', 18.99,  400),
('BOOK-001',  'Modern Web Development Guide',    'Comprehensive guide to full-stack web development',         'Books',         44.99,  100),
('BOOK-002',  'Data Structures Illustrated',     'Visual guide to common data structures and algorithms',     'Books',         32.99,  75),
('SPRT-001',  'Yoga Mat Premium',                'Non-slip 6mm thick yoga mat with carrying strap',           'Sports',        29.99,  180),
('SPRT-002',  'Stainless Steel Water Bottle',    'Double-wall insulated 750ml water bottle',                  'Sports',        19.99,  350);
