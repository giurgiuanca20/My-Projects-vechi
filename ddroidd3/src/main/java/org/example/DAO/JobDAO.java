package org.example.DAO;

import org.example.Model.Job;
import org.example.database.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class JobDAO extends AbstractDAO<Job> {

    public int findAccountByTitle(String title) {
        int i = -1;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT employer FROM job WHERE jobtitle=?";

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, title);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                i = resultSet.getInt("employer");
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
}
