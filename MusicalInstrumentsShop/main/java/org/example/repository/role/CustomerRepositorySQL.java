package org.example.repository.role;

import org.example.model.role.Customer;
import org.example.repository.cart.CartRepository;
import org.example.repository.security.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositorySQL implements CustomerRepository {
  private final Connection connection;
  private final UserRepository userRepository;
  private final CartRepository cartRepository;
  public CustomerRepositorySQL(Connection connection,UserRepository userRepository,CartRepository cartRepository) {

    this.connection = connection;
    this.userRepository=userRepository;
    this.cartRepository=cartRepository;
  }

  @Override
  public Customer addCustomer(Customer customer) throws SQLException {
    try {

      long userId=userRepository.insertUser(customer.getUsername(),customer.getPassword());

      long cartId =cartRepository.createCart();

      cartRepository.insertUserCart(userId,cartId);




      PreparedStatement insertCustomerStatement = connection.prepareStatement(
              "INSERT INTO customer (name, cnp, card, address, fidelity, points_fidelity) VALUES (?, ?, ?, ?, ?, ?)",
              Statement.RETURN_GENERATED_KEYS
      );
      insertCustomerStatement.setString(1, customer.getName());
      insertCustomerStatement.setString(2, customer.getCNP());
      insertCustomerStatement.setString(3, customer.getCard());
      insertCustomerStatement.setString(4, customer.getAddress());
      insertCustomerStatement.setString(5, "yes");
      insertCustomerStatement.setInt(6, 10); //pt a devenit fidel
      insertCustomerStatement.executeUpdate();

      ResultSet customerKeys = insertCustomerStatement.getGeneratedKeys();
      customerKeys.next();
      long customerId = customerKeys.getLong(1);

      PreparedStatement insertUserCustomerStatement = connection.prepareStatement(
              "INSERT INTO customer_user (user_id, customer_id) VALUES (?, ?)"
      );
      insertUserCustomerStatement.setLong(1, userId);
      insertUserCustomerStatement.setLong(2, customerId);
      insertUserCustomerStatement.executeUpdate();

      userRepository.insertUserRole(userId,3);
      customer.setId(customerId);

      return customer;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }
  }


  @Override
  public List<Customer> getAllCustomers() throws SQLException {
    List<Customer> customers = new ArrayList<>();

    try {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer");
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));
        customer.setName(resultSet.getString("name"));
        customer.setCNP(resultSet.getString("cnp"));
        customer.setCard(resultSet.getString("card"));
        customer.setAddress(resultSet.getString("address"));
        customer.setFidelity(resultSet.getString("fidelity"));
        customer.setPoints_fidelity(resultSet.getString("points_fidelity"));

        customers.add(customer);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }

    return customers;
  }

  @Override
  public int getFidelity(Long idUser) throws SQLException {

    int fidelity = 0;
    long idCustomer=0;
    try {
      PreparedStatement getUserIdStatement = connection.prepareStatement("SELECT customer_id FROM customer_user WHERE user_id = ?");
      getUserIdStatement.setLong(1, idUser);
      ResultSet userIdResultSet = getUserIdStatement.executeQuery();
      if (userIdResultSet.next()) {
         idCustomer = userIdResultSet.getLong("customer_id");
      }

      PreparedStatement statement = connection.prepareStatement("SELECT points_fidelity FROM customer WHERE id = ?");
      statement.setLong(1, idCustomer);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        fidelity = resultSet.getInt("points_fidelity");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }

    return fidelity;
  }

  @Override
  public void updateFidelity(Long idUser,int remainingFidelityPoints) throws SQLException {

    long idCustomer=0;
    try {
      PreparedStatement getUserIdStatement = connection.prepareStatement("SELECT customer_id FROM customer_user WHERE user_id = ?");
      getUserIdStatement.setLong(1, idUser);
      ResultSet userIdResultSet = getUserIdStatement.executeQuery();
      if (userIdResultSet.next()) {
        idCustomer = userIdResultSet.getLong("customer_id");
      }

      PreparedStatement statement = connection.prepareStatement("UPDATE customer SET points_fidelity = ? WHERE id = ?");
      statement.setInt(1, remainingFidelityPoints);
      statement.setLong(2, idCustomer);
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }
  }

  @Override
  public void updateCustomerById(Long customerId, String name, String cnp,String card,String address) throws SQLException {
    try {

      PreparedStatement updateStatement = connection.prepareStatement(
              "UPDATE customer SET name = ?, cnp = ?, card=?, address=? WHERE id = ?"
      );
      updateStatement.setString(1, name);
      updateStatement.setString(2, cnp);
      updateStatement.setString(3, card);
      updateStatement.setString(4, address);
      updateStatement.setLong(5, customerId);
      updateStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("An error occurred while updating the customer.", e);
    }
  }


  @Override
  public void deleteCutomerById(Long customerId) throws SQLException {
    try {
      Long userId=null;
      PreparedStatement selectStatement = connection.prepareStatement(
              "SELECT user_id FROM customer_user WHERE customer_id = ?");
      selectStatement.setLong(1, customerId);
      ResultSet resultSet = selectStatement.executeQuery();
      if (resultSet.next()) {
        userId=resultSet.getLong("user_id");
      }

      PreparedStatement deleteCashierUserStatement = connection.prepareStatement(
              "DELETE FROM customer_user WHERE customer_id = ?"
      );
      deleteCashierUserStatement.setLong(1, customerId);
      deleteCashierUserStatement.executeUpdate();


      PreparedStatement deleteOrderUserStatement = connection.prepareStatement(
              "DELETE FROM order_user WHERE user_id = ?"
      );
      deleteOrderUserStatement.setLong(1, userId);
      deleteOrderUserStatement.executeUpdate();


      Long cartId=null;
      PreparedStatement selectCartStatement = connection.prepareStatement(
              "SELECT cart_id FROM user_cart WHERE user_id = ?");
      selectCartStatement.setLong(1, userId);
      ResultSet cartResultSet = selectCartStatement.executeQuery();
      if (cartResultSet.next()) {
        cartId=cartResultSet.getLong("cart_id");
      }

      cartRepository.deleteFromCartId(userId);
      cartRepository.deleteFromUserCartById(userId);
      cartRepository.deleteCart(cartId);
      userRepository.deleteUserAndRolesById(userId);


      PreparedStatement deleteCashierStatement = connection.prepareStatement(
              "DELETE FROM customer WHERE id = ?"
      );
      deleteCashierStatement.setLong(1, customerId);
      deleteCashierStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("An error occurred while updating the cashier.", e);
    }
  }

}
