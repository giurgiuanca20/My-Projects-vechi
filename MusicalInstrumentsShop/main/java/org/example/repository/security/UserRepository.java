package org.example.repository.security;

import org.example.model.security.ERole;
import org.example.model.security.User;
import org.example.model.validation.Notification;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
  Notification<User> findByUsernameAndPassword(String username, String password);

  User create(User user) throws SQLException;
  User createGuest(User user) throws SQLException;

  void deleteAll();
  Long insertUser(String username,String password) throws SQLException;

  ERole findRoleByUsernameAndPassword(String username, String password) throws SQLException;
  Long findIdByUsernameAndPassword(String username, String password) throws SQLException;
  Long createUserForAccount(String username,String password)throws SQLException;
  void insertUserRole(Long userId,int role) throws SQLException;
  List<Long> getGuestIds() throws SQLException;
  void deleteUserAndRolesById(Long userId) throws SQLException;
}
