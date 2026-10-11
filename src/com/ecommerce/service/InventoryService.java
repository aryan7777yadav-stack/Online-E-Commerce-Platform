package com.ecommerce.service;

import com.ecommerce.model.Product;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Demonstrates: Collections & Generics, Multithreading, Synchronization
 */
public class InventoryService {

    // Generic collection with synchronization
    private final ConcurrentHashMap<Integer, Product> inventory = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    // Generic method - demonstrates Generics
    public <T extends Product> void addProduct(T product) {
        inventory.put(product.getId(), product);
        System.out.println("Added: " + product.getName());
    }

    // Synchronized inventory update - demonstrates Synchronization
    public synchronized boolean updateStock(int productId, int quantity) {
        Product p = inventory.get(productId);
        if (p == null) return false;
        int newStock = p.getStockQuantity() + quantity;
        if (newStock < 0) throw new IllegalArgumentException("Insufficient stock");
        p.setStockQuantity(newStock);
        return true;
    }

    // Multithreaded order processing
    public Future<Boolean> processOrderAsync(int productId, int quantity) {
        return executor.submit(() -> {
            synchronized (this) {
                Product p = inventory.get(productId);
                if (p != null && p.getStockQuantity() >= quantity) {
                    Thread.sleep(100); // Simulate processing
                    p.setStockQuantity(p.getStockQuantity() - quantity);
                    return true;
                }
                return false;
            }
        });
    }

    // Collections & Streams - Low stock alert
    public List<Product> getLowStockProducts() {
        return inventory.values().stream()
                .filter(Product::isLowStock)
                .collect(Collectors.toList());
    }

    // Generic utility - Collections
    public Map<String, List<Product>> groupByCategory() {
        return inventory.values().stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    public Collection<Product> getAllProducts() { return inventory.values(); }
    public void shutdown() { executor.shutdown(); }
}
