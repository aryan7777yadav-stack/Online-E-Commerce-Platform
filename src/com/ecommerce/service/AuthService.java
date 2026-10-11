package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.dao.impl.UserDAOImpl;
import java.util.*;

/**
 * Authentication Service - handles login/registration
 * Tries DB first, falls back to in-memory for Review 1 demo without MySQL
 */
public class AuthService {
    private final Map<String, User> memoryStore = new HashMap<>();

    public AuthService() {
        // Pre-seeded demo users for offline demo
        memoryStore.put("admin@ecommerce.com", new Admin(1, "Admin User", "admin@ecommerce.com", "admin123"));
        memoryStore.put("rahul@seller.com", new Seller(2, "Rahul Seller", "rahul@seller.com", "seller123", "Rahul Electronics"));
        memoryStore.put("amit@buyer.com", new Buyer(4, "Amit Buyer", "amit@buyer.com", "buyer123", "Delhi, India"));
    }

    public User login(String email, String password) throws Exception {
        // Try DB first
        try {
            UserDAOImpl dao = new UserDAOImpl();
            Optional<User> opt = dao.getUserByEmail(email);
            if (opt.isPresent() && opt.get().getPassword().equals(password)) {
                return opt.get();
            }
        } catch (Exception e) {
            System.out.println("DB not available, using in-memory store: " + e.getMessage());
        }
        // Fallback to memory
        User u = memoryStore.get(email);
        if (u != null && u.getPassword().equals(password)) return u;
        throw new Exception("Invalid email or password");
    }

    public boolean register(User user) {
        if (memoryStore.containsKey(user.getEmail())) return false;
        try {
            new UserDAOImpl().createUser(user);
            return true;
        } catch (Exception e) {
            // DB failed - store in memory
            memoryStore.put(user.getEmail(), user);
            return true;
        }
    }

    public Collection<User> getAllDemoUsers() { return memoryStore.values(); }
}
