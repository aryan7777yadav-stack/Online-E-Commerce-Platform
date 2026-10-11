package com.ecommerce.model;

public class Buyer extends User {
    private String address;

    public Buyer() { super(); this.role = Role.BUYER; }
    public Buyer(int id, String name, String email, String password, String address) {
        super(id, name, email, password, Role.BUYER);
        this.address = address;
    }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override public String getDashboardType() { return "Buyer Dashboard"; }
    @Override public void displayInfo() {
        System.out.println("[BUYER] " + name + " | Address: " + address + " - Browses and purchases products");
    }
}
