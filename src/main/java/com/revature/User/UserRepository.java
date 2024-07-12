package com.revature.User;

import java.sql.*;

import com.revature.util.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;


public class UserRepository {

    public List<User> findAll() {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            List<User> users = new ArrayList<>();

            String sql = "SELECT * FROM users;";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {

                users.add(generateUserFromResultSet(rs));
            }

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }





    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User generateUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));

        return user;
    }
}

