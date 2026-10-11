package com.ecommerce.dao.impl;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import com.ecommerce.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override public int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (buyer_id, total_amount, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getBuyerId()); ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getStatus().name()); ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
        }
        return -1;
    }
    @Override public List<Order> getOrdersByBuyer(int buyerId) throws SQLException { return queryOrders("SELECT * FROM orders WHERE buyer_id=" + buyerId); }
    @Override public List<Order> getOrdersBySeller(int sellerId) throws SQLException {
        return queryOrders("SELECT DISTINCT o.* FROM orders o JOIN order_items oi ON o.id=oi.order_id JOIN products p ON oi.product_id=p.id WHERE p.seller_id=" + sellerId);
    }
    @Override public List<Order> getAllOrders() throws SQLException { return queryOrders("SELECT * FROM orders"); }
    @Override public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE orders SET status=? WHERE id=?")) {
            ps.setString(1, status); ps.setInt(2, orderId); return ps.executeUpdate() > 0;
        }
    }
    private List<Order> queryOrders(String sql) throws SQLException {
        List<Order> list = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Order o = new Order(rs.getInt("id"), rs.getInt("buyer_id"), rs.getDouble("total_amount"));
                o.setStatus(Order.OrderStatus.valueOf(rs.getString("status"))); list.add(o);
            }
        }
        return list;
    }
}
