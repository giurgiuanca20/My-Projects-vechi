package org.example.repository.security;

import org.example.model.security.ERole;
import org.example.model.security.User;
import org.example.model.security.UserBuilder;
import org.example.model.validation.Notification;
import org.example.repository.cart.CartRepository;
import org.example.repository.cart.CartRepositorySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.database.Constants.TABLES.USER;

public class UserRepositorySQL implements UserRepository {

  private final Connection connection;
  private final RoleRepository roleRepository;

  public UserRepositorySQL(Connection connection, RoleRepository roleRepository) {
    this.connection = connection;
    this.roleRepository = roleRepository;
  }

  @Override
  public Notification<User> findByUsernameAndPassword(String username, String password) {
    Notification<User> resultNotification = new Notification<>();
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
          "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      if (userResultSet.next()) {
        User user = new UserBuilder()
            .setUsername(userResultSet.getString("username"))
            .setPassword(userResultSet.getString("password"))
            .setRoles(roleRepository.findRolesForUser(userResultSet.getLong("id")))
            .build();
        resultNotification.setResult(user);
        return resultNotification;
      } else {
        resultNotification.addError("Invalid username or password.");
      }
    } catch (SQLException e) {
      System.out.println(e);
      resultNotification.addError("Something is wrong with the database.");
    }
    return resultNotification;
  }

  @Override
  public void insertUserRole(Long userId,int role) throws SQLException{
    PreparedStatement insertUserRoleStatement = connection.prepareStatement(
            "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)"
    );
    insertUserRoleStatement.setLong(1, userId);
    insertUserRoleStatement.setInt(2, role);
    insertUserRoleStatement.executeUpdate();
  }

  @Override
  public Long insertUser(String username,String password) throws SQLException{
    PreparedStatement insertUserStatement = connection
            .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
    insertUserStatement.setString(1, username);
    insertUserStatement.setString(2, password);
    insertUserStatement.executeUpdate();
    ResultSet rs = insertUserStatement.getGeneratedKeys();
    rs.next();
    Long userId = rs.getLong(1);
    return userId;
  }

  @Override
  public User create(User user) throws SQLException {
    try {
      Long userId =insertUser(user.getUsername(), user.getPassword());
      user.setId(userId);

      insertUserRole(userId,1);

      roleRepository.addRolesToUser(user, user.getRoles());

      return user;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }
  }

  @Override
public Long createUserForAccount(String username,String password)throws SQLException {
    Long userId=null;
    try {
  PreparedStatement insertUserStatement = connection.prepareStatement(
          "INSERT INTO user (username, password) VALUES (?, ?)",
          Statement.RETURN_GENERATED_KEYS
  );
  insertUserStatement.setString(1, username);
  insertUserStatement.setString(2, password);
  insertUserStatement.executeUpdate();

  ResultSet userKeys = insertUserStatement.getGeneratedKeys();
  userKeys.next();
  userId = userKeys.getLong(1);

  }catch (SQLException e) {
    e.printStackTrace();
    throw new SQLException(e);
  }
    return userId;
}





  @Override
  public User createGuest(User user) throws SQLException {
    try {
      Long userId = createUserForAccount(user.getUsername(),user.getPassword());
      user.setId(userId);

      insertUserRole(userId,4);

      PreparedStatement insertCartStatement = connection.prepareStatement(
              "INSERT INTO cart (checkout) VALUES (?)",
              Statement.RETURN_GENERATED_KEYS
      );
      insertCartStatement.setString(1, "no");
      insertCartStatement.executeUpdate();

      ResultSet cartKeys = insertCartStatement.getGeneratedKeys();
      cartKeys.next();
      long cartId = cartKeys.getLong(1);


      PreparedStatement insertUserCartStatement = connection.prepareStatement(
              "INSERT INTO user_cart (user_id,cart_id) VALUES (?,?)",
              Statement.RETURN_GENERATED_KEYS
      );
      insertUserCartStatement.setLong(1, userId);
      insertUserCartStatement.setLong(2, cartId);
      insertUserCartStatement.executeUpdate();

      ResultSet user_cartKeys = insertUserCartStatement.getGeneratedKeys();
      user_cartKeys.next();

      return user;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }
  }








  @Override
  public void deleteAll() {
    try {
      Statement statement = connection.createStatement();
      String sql = "DELETE from user where id >= 0";
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ERole findRoleByUsernameAndPassword(String username, String password) throws SQLException {
    try {
      PreparedStatement statement = connection.prepareStatement(
              "SELECT r.role FROM user u " +
                      "JOIN user_role ur ON u.id = ur.user_id " +
                      "JOIN role r ON ur.role_id = r.id " +
                      "WHERE u.username = ? AND u.password = ?");
      statement.setString(1, username);
      statement.setString(2, password);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return ERole.valueOf(resultSet.getString("role"));
      } else {
        throw new SQLException("No role found for the specified username and password.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("An error occurred while fetching the role.", e);
    } catch (IllegalArgumentException e) {
      throw new SQLException("Invalid role found for the specified username and password.", e);
    }
  }
  @Override
  public Long findIdByUsernameAndPassword(String username, String password) throws SQLException {
    try {
      PreparedStatement statement = connection.prepareStatement(
              "SELECT id FROM user WHERE username = ? AND password = ?");
      statement.setString(1, username);
      statement.setString(2, password);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return Long.valueOf(resultSet.getString("id"));
      } else {
        throw new SQLException("No id found for the specified username and password.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("An error occurred while fetching the id.", e);
    } catch (IllegalArgumentException e) {
      throw new SQLException("Invalid id found for the specified username and password.", e);
    }
  }
  @Override
  public List<Long> getGuestIds() throws SQLException {
    List<Long> userIds = new ArrayList<>();
    try {
      PreparedStatement statement = connection.prepareStatement(
              "SELECT user_id FROM user_role WHERE role_id = ?");
      statement.setInt(1, 4);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        userIds.add(resultSet.getLong("user_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("An error occurred while fetching user ids by role id.", e);
    }
    return userIds;
  }


  @Override
  public void deleteUserAndRolesById(Long userId) throws SQLException {
    try {
      PreparedStatement deleteUserRoleStatement = connection.prepareStatement(
              "DELETE FROM user_role WHERE user_id = ?"
      );
      deleteUserRoleStatement.setLong(1, userId);
      deleteUserRoleStatement.executeUpdate();

      PreparedStatement deleteUserStatement = connection.prepareStatement(
              "DELETE FROM user WHERE id = ?"
      );
      deleteUserStatement.setLong(1, userId);
      deleteUserStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("An error occurred while deleting user and associated roles.", e);
    }
  }



}
