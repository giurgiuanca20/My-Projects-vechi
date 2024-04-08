package org.example.repository.cart;

import org.example.model.cart.EProduct;
import org.example.repository.cart.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.database.Constants.TABLES.PRODUCT;

public class ProductRepositorySQL implements ProductRepository {
  private final Connection connection;

  public ProductRepositorySQL(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(EProduct product) {
    try {
      String query = "INSERT IGNORE INTO " + PRODUCT + " (name, price) VALUES (?, ?)";
      try (PreparedStatement insertStatement = connection.prepareStatement(query)) {
        insertStatement.setString(1, product.getName());
        insertStatement.setInt(2, product.getPrice());
        insertStatement.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  @Override
  public Long getProductIdByName(String productName) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(
            "SELECT id FROM product WHERE name = ?")) {
      statement.setString(1, productName);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getLong("id");
      } else {
        throw new SQLException("No product ID found for the specified product name.");
      }
    }
  }
}
