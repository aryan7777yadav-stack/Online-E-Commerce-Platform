package com.ecommerce.dao;

import com.ecommerce.model.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * DAO Interface - demonstrates Interfaces & Abstraction
 */
public interface UserDAO {
    boolean createUser(User user) throws SQLException;
    Optional<User> getUserById(int id) throws SQLException;
    Optional<User> getUserByEmail(String email) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    boolean updateUser(User user) throws SQLException;
    boolean deleteUser(int id) throws SQLException;
    List<User> getUsersByRole(String role) throws SQLException;
}
