package org.example.controller;

import org.example.model.security.ERole;
import org.example.model.security.User;
import org.example.model.validation.Notification;
import org.example.service.SecurityService;
import org.example.view.AdministratorView;
import org.example.view.CashierView;
import org.example.view.LoginView;
import org.example.view.SearchView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.UUID;

public class LoginController {

    private final LoginView loginView;
    private final SearchView searchView;
    private final CashierView cashierView;
    private final SecurityService securityService;
    private final AdministratorView administratorView;
    private ERole role;

    public ERole getRole() {
        return role;
    }

    public LoginController(LoginView loginView, SearchView searchView, CashierView cashierView, AdministratorView administratorView, SecurityService securityService) {
        this.loginView = loginView;
        this.searchView = searchView;
        this.cashierView = cashierView;
        this.administratorView = administratorView;

        this.securityService = securityService;

        this.loginView.setLoginButtonListener(new LoginButtonListener());
        this.loginView.setGuestButtonListener(new GuestButtonListener());
        this.loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            try {
                role = securityService.findRoleByUsernameAndPasswor(username, password);
                Long userId = securityService.findIdByUsernameAndPasswor(username, password);
                securityService.setCurrentUserId(userId);
            } catch (SQLException t) {
                t.printStackTrace();
            }

            Notification<User> res = securityService.login(username, password);
            if (!res.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                if (role == ERole.CUSTOMER) {
                    loginView.setVisible(false);
                    searchView.setVisible(true);
                    securityService.setCurrentUserRole(ERole.CUSTOMER);
                } else if (role == ERole.EMPLOYEE) {
                    loginView.setVisible(false);
                    cashierView.setVisible(true);
                    securityService.setCurrentUserRole(ERole.EMPLOYEE);
                } else if (role == ERole.ADMINISTRATOR) {
                    loginView.setVisible(false);
                    administratorView.setVisible(true);
                }

            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), res.getFormattedErrors());
            }

        }

    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> registerNotification = securityService.register(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "register successful!");
            }
        }
    }

    private class GuestButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String uniqueID = UUID.randomUUID().toString();
            String uniqueNumber = uniqueID.substring(0, 8);
            String username = "Guest" + uniqueNumber + "@gmail.com";
            String password = "guest11!";
            try {
                securityService.registerGuest(username, password);

                Long userId = securityService.findIdByUsernameAndPasswor(username, password);
                securityService.setCurrentUserId(userId);
            } catch (SQLException t) {
                t.printStackTrace();
            }
            Notification<User> res = securityService.login(username, password);
            if (!res.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Guest successful!");

            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), res.getFormattedErrors());
            }

            loginView.setVisible(false);
            securityService.setCurrentUserRole(ERole.GUEST);
            searchView.setVisible(true);
        }
    }
}
