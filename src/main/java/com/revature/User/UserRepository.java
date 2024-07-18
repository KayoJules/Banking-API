package com.revature.User;

import java.sql.*;

import com.revature.util.ConnectionFactory;
import com.revature.util.exceptions.*;
import com.revature.util.interfaces.Crudable;

import java.util.ArrayList;
import java.util.List;


public class UserRepository implements Crudable<User> {

    @Override
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

    // TODO
    @Override
    public User findById(int userId) {


        return null;
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

    // TODO
    @Override
    public boolean update(User updatedUser) {

        return false;
    }

    @Override
    public boolean delete(User removedUser) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "DELETE FROM users WHERE user_id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, removedUser.getUserId());
            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new DataNotFoundException("User with id " + removedUser.getUserId() + " does not exist. Please try again");
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User create(User newUser) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "INSERT INTO users(username, pass, first_name, last_name) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, newUser.getUsername());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getFirstName());
            preparedStatement.setString(4, newUser.getLastName());

            int checkInsert = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();


            if (checkInsert == 0 || !resultSet.next()) {
                throw new InvalidInputException("Something was wrong when entering " + newUser + " into the database");
            }

            newUser.setUserId(resultSet.getInt(1));
            return newUser;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }



}

