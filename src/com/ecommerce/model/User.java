package com.ecommerce.model;

import java.time.LocalDateTime;

/**
 * Abstract User class - demonstrates Inheritance & Abstraction
 */
public abstract class User {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    protected Role role;
    protected LocalDateTime createdAt;

    public User() {
        this.createdAt = LocalDateTime.now();
    }

    public User(int id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    // Abstract method - Polymorphism
    public abstract String getDashboardType();
    public abstract void displayInfo();

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', email='%s', role=%s}", id, name, email, role);
    }
}
