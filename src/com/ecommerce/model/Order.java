package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    public enum OrderStatus { PENDING, SHIPPED, DELIVERED, CANCELLED }

    private int id;
    private int buyerId;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private List<OrderItem> items = new ArrayList<>();

    public Order() { this.status = OrderStatus.PENDING; this.orderDate = LocalDateTime.now(); }
    public Order(int id, int buyerId, double totalAmount) {
        this.id = id; this.buyerId = buyerId; this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING; this.orderDate = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public List<OrderItem> getItems() { return items; }
    public void addItem(OrderItem item) { items.add(item); }

    @Override
    public String toString() {
        return String.format("Order{id=%d, buyerId=%d, total=%.2f, status=%s}", id, buyerId, totalAmount, status);
    }

    public static class OrderItem {
        private int id, orderId, productId, quantity;
        private double priceAtPurchase;
        public OrderItem(int productId, int quantity, double priceAtPurchase) {
            this.productId = productId; this.quantity = quantity; this.priceAtPurchase = priceAtPurchase;
        }
        public int getProductId() { return productId; }
        public int getQuantity() { return quantity; }
        public double getPriceAtPurchase() { return priceAtPurchase; }
    }
}
