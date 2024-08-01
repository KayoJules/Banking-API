package com.revature.Account;

import com.revature.User.User;
import com.revature.util.ConnectionFactory;
import com.revature.util.exceptions.*;
import com.revature.util.interfaces.Crudable;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements Crudable<Account> {

    @Override
    public boolean update(Account updatedAccount) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "UPDATE accounts SET user_id = ?, account_type = ?, balance = ? WHERE account_id = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, updatedAccount.getUserId());
            // TODO: MIGHT CAUSE ERROR; UPPERCASE MAYBE?
            preparedStatement.setString(2, updatedAccount.getAccountTypeAsString());
            preparedStatement.setBigDecimal(3, updatedAccount.getBalance());
            preparedStatement.setInt(4, updatedAccount.getAccountId());

            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new DataNotFoundException("Account ID " + updatedAccount.getAccountId() + " does not exist. Please double check.");
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Account removedAccount) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "DELETE FROM accounts WHERE account_id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, removedAccount.getUserId());
            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new DataNotFoundException("Account with id " + removedAccount.getUserId() + " does not exist. Please try again");
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Account> findAll() {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            List<Account> accounts = new ArrayList<>();

            String sql = "SELECT * FROM accounts;";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {

                accounts.add(generateUserFromResultSet(rs));
            }

            return accounts;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account create(Account newAccount) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "INSERT INTO accounts(user_id, account_type, balance) VALUES(?,?::acc_type,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, newAccount.getUserId());
            preparedStatement.setString(2, newAccount.getAccountTypeAsString());
            preparedStatement.setBigDecimal(3, newAccount.getBalance());

            int checkInsert = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();


            if (checkInsert == 0 || !resultSet.next()) {
                throw new InvalidInputException("Something was wrong when entering " + newAccount + " into the database");
            }

            newAccount.setUserId(resultSet.getInt(1));
            return newAccount;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account findById(int accountId) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "select * from accounts where account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new DataNotFoundException("No account with that information found");
            }

            Account account = new Account();

            account.setAccountId(resultSet.getInt("account_id"));
            account.setUserId(resultSet.getInt("user_id"));

            // TODO: THIS MIGHT CAUSE AN ERROR
            account.setAccountType(resultSet.getString("account_type"));
            account.setBalance(resultSet.getBigDecimal("balance"));

            return account;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public List<Account> getAccountsByUserId(int userId) {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "SELECT * FROM accounts WHERE user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setAccountType(Account.AccountType.valueOf(rs.getString("account_type")));
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private Account generateUserFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account();

        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));

        String accountTypeStr = rs.getString("account_type");
        account.setAccountType(accountTypeStr);

        return account;
    }

    public boolean deposit(int accountId, BigDecimal amount) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setInt(2, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(int accountId, BigDecimal amount) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setInt(2, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BigDecimal getBalance(int accountId) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "SELECT balance FROM accounts WHERE account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("balance");
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
