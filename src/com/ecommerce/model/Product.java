package com.ecommerce.model;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private int sellerId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String category;
    private String imageUrl;
    private String status;
    private LocalDateTime createdAt;

    public Product() { this.createdAt = LocalDateTime.now(); this.status = "ACTIVE"; }

    public Product(int id, int sellerId, String name, String description, double price, int stockQuantity, String category) {
        this.id = id; this.sellerId = sellerId; this.name = name;
        this.description = description; this.price = price;
        this.stockQuantity = stockQuantity; this.category = category;
        this.status = "ACTIVE"; this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public boolean isLowStock() { return stockQuantity < 10; }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=%.2f, stock=%d, category='%s'}", id, name, price, stockQuantity, category);
    }
}
