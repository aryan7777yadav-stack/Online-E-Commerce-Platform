-- E-Commerce Platform Database Schema
-- Run: mysql -u root -p < database/schema.sql
-- Or: Copy paste in MySQL Workbench

DROP DATABASE IF EXISTS ecommerce_db;
CREATE DATABASE ecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ecommerce_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','SELLER','BUYER') NOT NULL,
    store_name VARCHAR(100) NULL,
    address VARCHAR(255) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    seller_id INT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
    category VARCHAR(80),
    image_url VARCHAR(255),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_seller (seller_id),
    INDEX idx_category (category)
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING','SHIPPED','DELIVERED','CANCELLED') DEFAULT 'PENDING',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (buyer_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    price_at_purchase DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE wishlist (
    id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_id INT NOT NULL,
    product_id INT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(buyer_id, product_id),
    FOREIGN KEY (buyer_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE system_activity (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    action VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Dummy Data for Review 1 Demo
INSERT INTO users (name, email, password, role) VALUES ('Admin User', 'admin@ecommerce.com', 'admin123', 'ADMIN');
INSERT INTO users (name, email, password, role, store_name) VALUES ('Rahul Seller', 'rahul@seller.com', 'seller123', 'SELLER', 'Rahul Electronics');
INSERT INTO users (name, email, password, role, store_name) VALUES ('Priya Seller', 'priya@seller.com', 'seller123', 'SELLER', 'Priya Fashion');
INSERT INTO users (name, email, password, role, address) VALUES ('Amit Buyer', 'amit@buyer.com', 'buyer123', 'BUYER', 'Delhi, India');
INSERT INTO users (name, email, password, role, address) VALUES ('Neha Buyer', 'neha@buyer.com', 'buyer123', 'BUYER', 'Mumbai, India');

INSERT INTO products (seller_id, name, description, price, stock_quantity, category) VALUES
(2, 'Wireless Headphones', 'Noise cancelling Bluetooth 5.0', 2999.00, 50, 'Electronics'),
(2, 'Smart Watch', 'Fitness tracker with heart rate monitor', 4999.00, 8, 'Electronics'),
(3, 'Cotton T-Shirt', 'Premium cotton regular fit', 599.00, 120, 'Fashion'),
(3, 'Running Shoes', 'Lightweight breathable running shoes', 2499.00, 5, 'Fashion'),
(2, 'Laptop Stand', 'Adjustable aluminum laptop stand', 1299.00, 35, 'Electronics');

INSERT INTO orders (buyer_id, total_amount, status) VALUES (4, 5998.00, 'PENDING'), (5, 2499.00, 'SHIPPED');
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (1, 1, 2, 2999.00), (2, 4, 1, 2499.00);

SELECT 'Database setup complete!' AS Status;
