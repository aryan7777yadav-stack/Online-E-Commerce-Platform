package com.ecommerce.model;

public class Admin extends User {
    public Admin() { super(); this.role = Role.ADMIN; }
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, Role.ADMIN);
    }
    @Override public String getDashboardType() { return "Admin Dashboard"; }
    @Override public void displayInfo() {
        System.out.println("[ADMIN] " + name + " - Manages users, products, and orders");
    }
}
