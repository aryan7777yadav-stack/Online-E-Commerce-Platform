package com.ecommerce.dao;

import com.ecommerce.model.Order;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    int createOrder(Order order) throws SQLException;
    List<Order> getOrdersByBuyer(int buyerId) throws SQLException;
    List<Order> getOrdersBySeller(int sellerId) throws SQLException;
    List<Order> getAllOrders() throws SQLException;
    boolean updateOrderStatus(int orderId, String status) throws SQLException;
}
