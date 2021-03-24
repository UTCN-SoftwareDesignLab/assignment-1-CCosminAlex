package repository.userRole;

import model.User;

import java.sql.*;

public class UserRole {
    private final Connection connection;

    public UserRole(Connection connection) {
        this.connection = connection;
    }

    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user_role values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setInt(1, user.getId());
            insertUserStatement.setInt(2, 2);
            System.out.println("Adasda");
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
