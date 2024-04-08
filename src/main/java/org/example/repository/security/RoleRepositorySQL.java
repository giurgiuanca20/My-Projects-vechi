package org.example.repository.security;

import org.example.model.security.ERole;
import org.example.model.security.Role;
import org.example.model.security.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.database.Constants.TABLES.ROLE;

public class RoleRepositorySQL implements RoleRepository {
  private final Connection connection;

  public RoleRepositorySQL(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(ERole role) {
    try (PreparedStatement insertStatement = connection.prepareStatement("INSERT IGNORE INTO " + ROLE + " (role) VALUES (?)")) {
      insertStatement.setString(1, role.toString());
      insertStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Role findRoleByTitle(ERole role) {
    try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + ROLE + " WHERE role = ?")) {
      statement.setString(1, role.toString());
      try (ResultSet roleResultSet = statement.executeQuery()) {
        if (roleResultSet.next()) {
          Long roleId = roleResultSet.getLong("id");
          String roleTitle = roleResultSet.getString("role");
          return new Role(roleId, roleTitle);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Role> findRolesForUser(long userId) {
    List<Role> userRoles = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement("SELECT r.id, r.role FROM " + ROLE + " r INNER JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = ?")) {
      statement.setLong(1, userId);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Long roleId = resultSet.getLong("id");
          String roleTitle = resultSet.getString("role");
          userRoles.add(new Role(roleId, roleTitle));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return userRoles;
  }

  @Override
  public void addRolesToUser(User user, List<Role> roles) {
    try {
      connection.setAutoCommit(false);

      try (PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM user_role WHERE user_id = ?")) {
        deleteStatement.setLong(1, user.getId());
        deleteStatement.executeUpdate();
      }

      try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO user_role (user_id, role_id) VALUES (?, ?)")) {
        for (Role role : roles) {
          insertStatement.setLong(1, user.getId());
          insertStatement.setLong(2, role.getId());
          insertStatement.addBatch();
        }
        insertStatement.executeBatch();
      }

      connection.commit();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException rollbackException) {
        rollbackException.printStackTrace();
      }
      e.printStackTrace();
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
