package org.example.service;

import org.example.model.security.*;
import org.example.model.validation.Notification;
import org.example.model.validation.UserValidator;
import org.example.repository.security.RoleRepository;
import org.example.repository.security.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SecurityService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private Long currentUserId;
  private ERole currentUserRole;

  public SecurityService(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public Notification<User> register(String username, String password) {
    Role customerRole = roleRepository.findRoleByTitle(ERole.ADMINISTRATOR);

    User user = createUser(username, password, Collections.singletonList(customerRole));

    UserValidator validator = new UserValidator(user);
    Notification<User> userRegistrationNotification = new Notification<>();

    if (!validator.validate()) {
      validator.getErrors().forEach(userRegistrationNotification::addError);
    } else {
      try {
        user.setPassword(encodePassword(password));
        User createdUser = userRepository.create(user);
        userRegistrationNotification.setResult(createdUser);
      } catch (SQLException e) {
        userRegistrationNotification.addError("Database error occurred during registration.");
      }
    }
    return userRegistrationNotification;
  }
  public List<Long> getGuestIds() throws SQLException{
    return userRepository.getGuestIds();
  }

  public Notification<User> registerGuest(String username, String password) {
    Role customerRole = roleRepository.findRoleByTitle(ERole.CUSTOMER);

    User user = createUser(username, password, Collections.singletonList(customerRole));

    UserValidator validator = new UserValidator(user);
    Notification<User> userRegistrationNotification = new Notification<>();

    if (!validator.validate()) {
      validator.getErrors().forEach(userRegistrationNotification::addError);
    } else {
      try {
        user.setPassword(encodePassword(password));
        User createdUser = userRepository.createGuest(user);
        userRegistrationNotification.setResult(createdUser);
      } catch (SQLException e) {
        userRegistrationNotification.addError("Database error occurred during guest registration.");
      }
    }
    return userRegistrationNotification;
  }

  private User createUser(String username, String password, List<Role> roles) {
    return new UserBuilder()
            .setUsername(username)
            .setPassword(password)
            .setRoles(roles)
            .build();
  }

  public void setCurrentUserId(Long userId) {
    currentUserId = userId;
  }

  public void setCurrentUserRole(ERole role) {
    currentUserRole = role;
  }

  public Long getCurrentUserId() {
    return currentUserId;
  }

  public ERole getCurrentUserRole() {
    return currentUserRole;
  }
  public ERole findRoleByUsernameAndPasswor(String username, String password) throws SQLException {
    return userRepository.findRoleByUsernameAndPassword(username, encodePassword(password));
  }
  public Long findIdByUsernameAndPasswor(String username, String password) throws SQLException {
    return userRepository.findIdByUsernameAndPassword(username, encodePassword(password));
  }

  public Notification<User> login(String username, String password) {
    return userRepository.findByUsernameAndPassword(username, encodePassword(password));
  }

  public String encodePassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
      StringBuilder hexString = new StringBuilder();

      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }

      return hexString.toString();
    } catch (NoSuchAlgorithmException ex) {
      throw new RuntimeException("Password encryption algorithm not found.");
    }
  }
}
