package org.example.repository.role;

import org.example.model.role.Cashier;
import org.example.repository.security.UserRepository;
import org.example.service.SecurityService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CashierRepositorySQL implements CashierRepository {
  private final Connection connection;
  private final UserRepository userRepository;


  public CashierRepositorySQL(Connection connection, UserRepository userRepository) {
    this.connection = connection;
    this.userRepository=userRepository;
  }



  @Override
  public void deleteCashierById(Long cashierId) throws SQLException {
    try {
      Long userId=null;
      PreparedStatement selectStatement = connection.prepareStatement(
              "SELECT user_id FROM user_cashier WHERE cashier_id = ?");
      selectStatement.setLong(1, cashierId);
      ResultSet resultSet = selectStatement.executeQuery();
      if (resultSet.next()) {
        userId=resultSet.getLong("user_id");
      }

      PreparedStatement deleteCashierUserStatement = connection.prepareStatement(
              "DELETE FROM user_cashier WHERE cashier_id = ?"
      );
      deleteCashierUserStatement.setLong(1, cashierId);
      deleteCashierUserStatement.executeUpdate();


      userRepository.deleteUserAndRolesById(userId);


      PreparedStatement deleteCashierOrderStatement = connection.prepareStatement(
              "DELETE FROM cashier_order WHERE cashier_id = ?"
      );
      deleteCashierOrderStatement.setLong(1, cashierId);
      deleteCashierOrderStatement.executeUpdate();



      PreparedStatement deleteCashierStatement = connection.prepareStatement(
              "DELETE FROM cashier WHERE id = ?"
      );
      deleteCashierStatement.setLong(1, cashierId);
      deleteCashierStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("An error occurred while updating the cashier.", e);
    }
  }





  @Override
  public Cashier createCashier(Cashier cashier) throws SQLException {
    try {

      Long userId = userRepository.createUserForAccount(cashier.getUsername(),cashier.getPassword());

      PreparedStatement insertCashierStatement = connection.prepareStatement(
              "INSERT INTO cashier (name, phone) VALUES (?, ?)",
              Statement.RETURN_GENERATED_KEYS
      );
      insertCashierStatement.setString(1, cashier.getName());
      insertCashierStatement.setString(2, cashier.getPhone());
      insertCashierStatement.executeUpdate();

      ResultSet cashierKeys = insertCashierStatement.getGeneratedKeys();
      cashierKeys.next();
      long cashierId = cashierKeys.getLong(1);

      PreparedStatement insertUserCashierStatement = connection.prepareStatement(
              "INSERT INTO user_cashier (user_id, cashier_id) VALUES (?, ?)"
      );
      insertUserCashierStatement.setLong(1, userId);
      insertUserCashierStatement.setLong(2, cashierId);
      insertUserCashierStatement.executeUpdate();

      userRepository.insertUserRole(userId,2);

      cashier.setId(cashierId);

      return cashier;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }
  }

  @Override
  public void updateCashierById(Long cashierId, String name, String phone) throws SQLException {
    try {

      PreparedStatement updateStatement = connection.prepareStatement(
              "UPDATE cashier SET name = ?, phone = ? WHERE id = ?"
      );
      updateStatement.setString(1, name);
      updateStatement.setString(2, phone);
      updateStatement.setLong(3, cashierId);
      updateStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("An error occurred while updating the cashier.", e);
    }
  }


  @Override
  public List<Cashier> getAllCashiers() throws SQLException {
    List<Cashier> cashiers = new ArrayList<>();

    try {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM cashier");
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Cashier cashier = new Cashier();
        cashier.setId(resultSet.getLong("id"));
        cashier.setName(resultSet.getString("name"));
        cashier.setPhone(resultSet.getString("phone"));

        cashiers.add(cashier);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }

    return cashiers;
  }

  @Override
  public Long getCashierIdFromUserId(Long idUser) throws SQLException {
   Long idCashier=null;
    try (PreparedStatement getCashierIdStatement = connection.prepareStatement(
            "SELECT cashier_id FROM user_cashier WHERE user_id = ?")) {
      getCashierIdStatement.setLong(1, idUser);
      ResultSet cashierResultSet = getCashierIdStatement.executeQuery();
      if (cashierResultSet.next()) {
        idCashier = cashierResultSet.getLong("cashier_id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }

    return idCashier;
  }
}
