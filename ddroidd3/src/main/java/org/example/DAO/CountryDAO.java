package org.example.DAO;

import org.example.Model.Country;
import org.example.database.Connect;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class CountryDAO extends AbstractDAO<Country> {
    public static JComboBox<String> createCountryComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();

        comboBox.setPreferredSize(new Dimension(200, 30));
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

        String query = "SELECT name FROM country";

        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String countryName = resultSet.getString("name");
                comboBoxModel.addElement(countryName);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error creating combobox combo box: " + e.getMessage());
        }
        comboBox.setModel(comboBoxModel);
        return comboBox;
    }

    public static JComboBox<String> createCityComboBox(String tableName) {
        JComboBox<String> comboBox = new JComboBox<>();

        comboBox.setPreferredSize(new Dimension(200, 30));
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

        String query = "SELECT name FROM city_" + tableName;

        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String countryName = resultSet.getString("name");
                comboBoxModel.addElement(countryName);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error creating combobox combo box: " + e.getMessage());
        }
        comboBox.setModel(comboBoxModel);
        return comboBox;
    }

    public static String[] modifyCityComboBox(String tableName) {
        List<String> cityNames = new ArrayList<>();

        String query = "SELECT name FROM city_" + tableName;

        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String cityName = resultSet.getString("name");
                cityNames.add(cityName);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error fetching city names from the database: " + e.getMessage());
        }

        return cityNames.toArray(new String[0]);
    }

}
