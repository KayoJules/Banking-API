package com.revature.User;

import java.sql.*;

import com.revature.Account.Account;
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

    @Override
    public User findById(int userId) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "select * from users where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new DataNotFoundException("No user with that information found");
            }

            User user = new User();

            user.setUserId(resultSet.getInt("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("pass"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));

            return user;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND pass = ?";
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("pass"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username, pass) VALUES (?, ?)";
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
        user.setPassword(rs.getString("pass"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));

        return user;
    }

    @Override
    public boolean update(User updatedUser) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, pass = ? WHERE user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, updatedUser.getFirstName());
            preparedStatement.setString(2, updatedUser.getLastName());
            preparedStatement.setString(3, updatedUser.getUsername());
            preparedStatement.setString(4, updatedUser.getPassword());
            preparedStatement.setInt(5, updatedUser.getUserId());

            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new DataNotFoundException("User ID " + updatedUser.getUserId() + " does not exist. Please double check.");
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

