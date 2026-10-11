package com.ecommerce.dao;

import com.ecommerce.model.Product;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    boolean addProduct(Product product) throws SQLException;
    Optional<Product> getProductById(int id) throws SQLException;
    List<Product> getAllProducts() throws SQLException;
    List<Product> getProductsBySeller(int sellerId) throws SQLException;
    boolean updateProduct(Product product) throws SQLException;
    boolean deleteProduct(int id) throws SQLException;
    List<Product> searchProducts(String keyword) throws SQLException;
    List<Product> getLowStockProducts() throws SQLException;
}
