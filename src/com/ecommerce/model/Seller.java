package com.ecommerce.model;

public class Seller extends User {
    private String storeName;

    public Seller() { super(); this.role = Role.SELLER; }
    public Seller(int id, String name, String email, String password, String storeName) {
        super(id, name, email, password, Role.SELLER);
        this.storeName = storeName;
    }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    @Override public String getDashboardType() { return "Seller Dashboard"; }
    @Override public void displayInfo() {
        System.out.println("[SELLER] " + name + " | Store: " + storeName + " - Lists products, manages inventory");
    }
}
