-- Sample data for Country Chicken Business Management System

-- Insert sample customers
INSERT INTO customers (customer_code, name, email, phone, address, city, state, zip_code, registration_date, total_orders, total_spent, is_active, customer_type) VALUES
('CUST-001', 'John Smith', 'john.smith@email.com', '1234567890', '123 Main Street', 'New York', 'NY', '10001', '2024-01-15 10:30:00', 5, 12500.00, true, 'REGULAR'),
('CUST-002', 'Restaurant ABC', 'orders@restaurantabc.com', '2345678901', '456 Park Avenue', 'New York', 'NY', '10002', '2024-01-20 14:45:00', 12, 45000.00, true, 'WHOLESALE'),
('CUST-003', 'Mary Johnson', 'mary.j@email.com', '3456789012', '789 Broadway', 'Brooklyn', 'NY', '11201', '2024-02-05 09:15:00', 3, 7500.00, true, 'REGULAR'),
('CUST-004', 'Hotel Grand', 'purchase@hotelgrand.com', '4567890123', '321 Fifth Ave', 'Manhattan', 'NY', '10016', '2024-02-10 16:20:00', 8, 32000.00, true, 'WHOLESALE'),
('CUST-005', 'Robert Brown', 'robert.b@email.com', '5678901234', '654 Elm Street', 'Queens', 'NY', '11355', '2024-02-15 11:00:00', 2, 4000.00, true, 'RETAILER');

-- Insert sample inventory
INSERT INTO inventory (item_code, item_name, category, chicken_part, quantity, reorder_level, unit_price, unit, supplier, supplier_contact, last_restocked, expiry_date, storage_location, is_available) VALUES
('INV-001', 'Fresh Whole Chicken', 'FRESH', 'WHOLE', 100, 20, 200.00, 'KG', 'Fresh Farms Inc.', '555-0101', '2024-03-01 08:00:00', '2024-03-08 23:59:59', 'FRESH_SECTION', true),
('INV-002', 'Frozen Chicken Legs', 'FROZEN', 'LEGS', 150, 30, 180.00, 'KG', 'Cold Storage Co.', '555-0102', '2024-03-01 09:30:00', '2024-06-01 23:59:59', 'FREEZER_A', true),
('INV-003', 'Fresh Chicken Breast', 'FRESH', 'BREAST', 80, 15, 250.00, 'KG', 'Fresh Farms Inc.', '555-0101', '2024-03-02 10:15:00', '2024-03-09 23:59:59', 'FRESH_SECTION', true),
('INV-004', 'Organic Whole Chicken', 'FRESH', 'WHOLE', 50, 10, 350.00, 'KG', 'Organic Valley', '555-0103', '2024-03-02 14:20:00', '2024-03-09 23:59:59', 'ORGANIC_SECTION', true),
('INV-005', 'Chicken Wings', 'FROZEN', 'WINGS', 120, 25, 160.00, 'KG', 'Cold Storage Co.', '555-0102', '2024-03-01 11:45:00', '2024-06-01 23:59:59', 'FREEZER_B', true),
('INV-006', 'Chicken Thighs', 'FRESH', 'THIGHS', 60, 12, 190.00, 'KG', 'Fresh Farms Inc.', '555-0101', '2024-03-03 13:30:00', '2024-03-10 23:59:59', 'FRESH_SECTION', true);

-- Insert sample orders
INSERT INTO chicken_orders (order_number, customer_name, customer_phone, chicken_type, chicken_part, quantity, price_per_kg, total_price, status, order_date, delivery_date, delivery_address, special_instructions, created_at) VALUES
('ORD-A1B2C3', 'John Smith', '1234567890', 'FRESH', 'WHOLE', 10, 200.00, 2000.00, 'DELIVERED', '2024-03-01 10:30:00', '2024-03-01 15:00:00', '123 Main Street, New York, NY 10001', 'Please deliver before 5 PM', '2024-03-01 10:30:00'),
('ORD-D4E5F6', 'Restaurant ABC', '2345678901', 'FROZEN', 'LEGS', 50, 180.00, 9000.00, 'DELIVERED', '2024-03-01 14:45:00', '2024-03-02 09:00:00', '456 Park Avenue, New York, NY 10002', 'Morning delivery preferred', '2024-03-01 14:45:00'),
('ORD-G7H8I9', 'Mary Johnson', '3456789012', 'FRESH', 'BREAST', 15, 250.00, 3750.00, 'PROCESSING', '2024-03-02 11:20:00', NULL, '789 Broadway, Brooklyn, NY 11201', 'Cut into pieces', '2024-03-02 11:20:00'),
('ORD-J1K2L3', 'Hotel Grand', '4567890123', 'ORGANIC', 'WHOLE', 20, 350.00, 7000.00, 'CONFIRMED', '2024-03-02 16:10:00', '2024-03-03 11:00:00', '321 Fifth Ave, Manhattan, NY 10016', 'For hotel banquet', '2024-03-02 16:10:00'),
('ORD-M4N5O6', 'Robert Brown', '5678901234', 'FRESH', 'THIGHS', 8, 190.00, 1520.00, 'PENDING', '2024-03-03 09:45:00', NULL, '654 Elm Street, Queens, NY 11355', NULL, '2024-03-03 09:45:00'),
('ORD-P7Q8R9', 'John Smith', '1234567890', 'FROZEN', 'WINGS', 12, 160.00, 1920.00, 'DELIVERED', '2024-03-03 14:30:00', '2024-03-03 17:30:00', '123 Main Street, New York, NY 10001', NULL, '2024-03-03 14:30:00');