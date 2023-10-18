package org.example.DAO;

import org.example.Model.Account;
import org.example.database.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class AccountDAO extends AbstractDAO<Account> {
    public int findIdByAccount(String username, String password) {
        int i = -1;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id FROM account WHERE username=? AND password=?";

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                i = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving id findNameByID: " + "DAO:findNameByID " + e.getMessage());
        } finally {
            Connect.close(resultSet);
            Connect.close(statement);
            Connect.close(connection);
        }
        return i;
    }

    public int findTypeById(int id) {
        int i = -1;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT type FROM account WHERE id=?";

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                i = resultSet.getInt("type");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving type account: " + "DAO:findTypeById " + e.getMessage());
        } finally {
            Connect.close(resultSet);
            Connect.close(statement);
            Connect.close(connection);
        }
        return i;
    }

    public int findPersonByAccount(int id) {
        int i = -1;
        int type = findTypeById(id);
        if (type == 1) {
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            String query = "SELECT id FROM applicant WHERE account=?";

            try {
                connection = Connect.getConnection();
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    i = resultSet.getInt("id");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error retrieving type account: " + "DAO:findTypeById " + e.getMessage());
            } finally {
                Connect.close(resultSet);
                Connect.close(statement);
                Connect.close(connection);
            }
        } else {

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            String query = "SELECT id FROM employer WHERE account=?";

            try {
                connection = Connect.getConnection();
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    i = resultSet.getInt("id");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error retrieving type account: " + "DAO:findTypeById " + e.getMessage());
            } finally {
                Connect.close(resultSet);
                Connect.close(statement);
                Connect.close(connection);
            }
        }
        return i;
    }

}
