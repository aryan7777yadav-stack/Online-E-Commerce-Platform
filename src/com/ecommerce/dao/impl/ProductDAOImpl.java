package com.ecommerce.dao.impl;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;
import com.ecommerce.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements ProductDAO {

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setSellerId(rs.getInt("seller_id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setStockQuantity(rs.getInt("stock_quantity"));
        p.setCategory(rs.getString("category"));
        p.setImageUrl(rs.getString("image_url"));
        p.setStatus(rs.getString("status"));
        return p;
    }

    @Override
    public boolean addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (seller_id, name, description, price, stock_quantity, category, image_url, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, product.getSellerId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getStockQuantity());
            ps.setString(6, product.getCategory());
            ps.setString(7, product.getImageUrl());
            ps.setString(8, product.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    @Override public Optional<Product> getProductById(int id) throws SQLException {
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return Optional.of(mapRow(rs)); }
        }
        return Optional.empty();
    }

    @Override public List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM products")) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override public List<Product> getProductsBySeller(int sellerId) throws SQLException {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE seller_id=?")) {
            ps.setInt(1, sellerId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapRow(rs)); }
        }
        return list;
    }

    @Override public boolean updateProduct(Product p) throws SQLException {
        String sql = "UPDATE products SET name=?, description=?, price=?, stock_quantity=?, category=? WHERE id=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName()); ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice()); ps.setInt(4, p.getStockQuantity());
            ps.setString(5, p.getCategory()); ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override public boolean deleteProduct(int id) throws SQLException {
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE id=?")) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }

    @Override public List<Product> searchProducts(String keyword) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%"); ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapRow(rs)); }
        }
        return list;
    }

    @Override public List<Product> getLowStockProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM products WHERE stock_quantity < 10")) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }
}
