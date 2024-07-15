package com.revature.Account;

import com.revature.util.ConnectionFactory;
import com.revature.util.exceptions.DataNotFoundException;
import com.revature.util.interfaces.Crudable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountRepository implements Crudable<Account> {

    @Override
    public boolean update(Account updatedObject) {
        return false;
    }

    @Override
    public boolean delete(Account removedObject) {
        return false;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account create(Account newObject) {
        return null;
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
}
