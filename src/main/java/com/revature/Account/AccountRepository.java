package com.revature.Account;

import com.revature.User.User;
import com.revature.util.ConnectionFactory;
import com.revature.util.exceptions.*;
import com.revature.util.interfaces.Crudable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements Crudable<Account> {

    // TODO
    @Override
    public boolean update(Account updatedAccount) {

        return false;
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

            String sql = "INSERT INTO account(user_id, account_type, balance) VALUES(?,?,?)";
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
            String sql = "select * from accounts where accountId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new DataNotFoundException("No account with that information found");
            }

            Account account = new Account();

            // TODO: Review me; copy pasta
            account.setAccountId(resultSet.getInt("account_id"));

            return account;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public Account findByAccountIdAndUser(int  accountId, int userId) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "select * from accounts where account_id = ? and user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new DataNotFoundException("No account with that information found");
            }

            Account account = new Account();

            account.setAccountId(resultSet.getInt("account_id"));
            account.setUserId(resultSet.getInt("user_id"));

            return account;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
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


}
