package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Account findById(int id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException((long) id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException((long) id, Account.class.getSimpleName());
        }
    }

    @Override
    public Account findByClientId(int idC) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where idClient=" + idC;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException((long) idC, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException((long) idC, Account.class.getSimpleName());
        }
    }

    @Override
    public boolean create(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (?, ?, ?, ?, ?)");
            insertStatement.setInt(1, account.getId());
            insertStatement.setString(2, account.getType());
            insertStatement.setInt(3, account.getBalance());
            insertStatement.setDate(5, account.getCreation_date());
            insertStatement.setInt(4, account.getClient_id());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id = " + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE account SET id = ?, type = ?, balance = ?, creation_date= ?, client_id= ? WHERE id = " + account.getId());
            insertUserStatement.setLong(1, account.getId());
            insertUserStatement.setString(2, account.getType());
            insertUserStatement.setInt(3, account.getBalance());
            insertUserStatement.setDate(4, account.getCreation_date());
            insertUserStatement.setInt(5, account.getClient_id());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getInt("id"))
                .setType(rs.getString("type"))
                .setBalance(rs.getInt("balance"))
                .setDateOfCreation()
                .setIdClient(rs.getInt("client_id"))
                .build();
    }
}
