package org.example.controller;

import org.example.model.cart.Fidelity;
import org.example.model.role.Cashier;
import org.example.model.validation.Notification;
import org.example.service.CashierService;
import org.example.view.AdministratorView;
import org.example.view.CashierTableView;
import org.example.view.LoginView;
import org.example.view.ReportView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AdministratorController {

    private final AdministratorView administratorView;
    private final LoginView loginView;
    private final ReportView reportView;
    private final CashierService cashierService;


    public AdministratorController(LoginView loginView, AdministratorView administratorView, CashierService cashierService, ReportView reportView) {
        this.administratorView = administratorView;
        this.loginView = loginView;
        this.reportView = reportView;

        this.cashierService = cashierService;

        initializeListeners();
    }

    private void initializeListeners() {
        administratorView.setLogoutButtonListener(e -> handleLogout());
        administratorView.setCreateButtonListener(e -> handleCreateCashier());
        administratorView.setViewButtonListener(e -> handleViewCashiers());
        administratorView.setDeleteButtonListener(e -> handleDeleteCashier());
        administratorView.setUpdateButtonListener(e -> handleUpdateCashier());
        administratorView.setGenerateButtonListener(e -> handleGenerateReport());
        administratorView.setFidelityButtonListener(e -> handleFidelity());
    }

    private void handleLogout() {
        loginView.setVisible(true);
        administratorView.setVisible(false);
    }

    private void handleFidelity() {
        int fidelity = Integer.parseInt(administratorView.getTfFidelity());
        Fidelity.setFidelityValue(fidelity);
    }
    private void handleCreateCashier() {
        String username = administratorView.getUsername();
        String password = administratorView.getPassword();
        String name = administratorView.getTfName();
        String phone = administratorView.getTfPhone();

        Notification<Cashier> registerNotification = cashierService.registerCashier(username, password, name, phone);

        if (registerNotification.hasErrors()) {
            JOptionPane.showMessageDialog(administratorView.getContentPane(), registerNotification.getFormattedErrors());
        } else {
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Cashier created successfully!");
        }
    }

    private void handleViewCashiers() {
        try {
            List<Cashier> cashiers = cashierService.getAllCashiers();
            showCashierView(cashiers);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Failed to fetch cashiers!");
        }
    }

    private void showCashierView(List<Cashier> cashiers) {
        CashierTableView cashierTableView = new CashierTableView(cashiers);
        cashierTableView.setVisible(true);
    }

    private void handleDeleteCashier() {
        Long id = Long.valueOf(administratorView.getTfDelete());
        cashierService.deleteCashier(id);

    }
    private void handleUpdateCashier() {
        Long id = Long.valueOf(administratorView.getTfUpdate());
        cashierService.updateCashier(id, administratorView.getTfName(), administratorView.getTfPhone());
    }

    private void handleGenerateReport() {
        reportView.setVisible(true);
        administratorView.setVisible(false);
    }
}
