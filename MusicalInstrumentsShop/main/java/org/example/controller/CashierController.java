package org.example.controller;

import org.example.model.cart.Order;
import org.example.model.role.Customer;
import org.example.model.validation.Notification;
import org.example.service.CustomerService;
import org.example.service.OrderService;
import org.example.service.SecurityService;
import org.example.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CashierController {

    private final CashierView cashierView;
    private final LoginView loginView;
    private final OrderView orderView;
    private final SecurityService securityService;
    private final CustomerService customerService;
    private final OrderService orderService;



    public CashierController(LoginView loginView, CashierView cashierView, SecurityService securityService,CustomerService customerService,OrderService orderService,OrderView orderView) {
        this.cashierView = cashierView;
        this.loginView=loginView;
        this.orderView=orderView;


        this.securityService = securityService;
        this.customerService=customerService;
        this.orderService=orderService;
        this.cashierView.setViewButtonListener(new ViewButtonListener());
        this.cashierView.setProcessButtonListener(new ProcessButtonListener());
        this.cashierView.setAddButtonListener(new CashierController.AddButtonListener());
        this.cashierView.setLogoutButtonListener(new CashierController.LogoutButtonListener());
        this.cashierView.setUpdateButtonListener(new CashierController.UpdateButtonListener());
        this.cashierView.setDeleteButtonListener(new CashierController.DeleteButtonListener());
    }



    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = cashierView.getUsername();
            String password = cashierView.getPassword();
            String name = cashierView.getTfName();
            String address = cashierView.getTfAddress();
            String card = cashierView.getTfCard();
            String cnp = cashierView.getTfCNP();

            Notification<Customer> registerNotification = customerService.registerCustomer(username, password,name,cnp,card,address);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "add client successful!");
            }
        }
    }
    private class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginView.setVisible(true);
            cashierView.setVisible(false);
        }
    }
    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Customer> customers = customerService.getAllCustomers();
                showCustomerView(customers);
            } catch (SQLException t) {
                t.printStackTrace();
            }
        }
    }
    private class ProcessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Order> orders = orderService.getAllOrders();
                showOrdersView(orders);
            } catch (SQLException t) {
                t.printStackTrace();
            }
        }
    }
    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                Long id = Long.valueOf(cashierView.getTfUpdate());
                customerService.updateCustomer(id,cashierView.getTfName(),cashierView.getTfCNP(),cashierView.getTfCard(),cashierView.getTfAddress());
        }
    }


    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(cashierView.getTfDelete());
            customerService.deleteCustomer(id);
        }
    }

    private void showCustomerView(List<Customer> customers) {
        CustomerTableView customerTableView = new CustomerTableView(customers);
        customerTableView.setVisible(true);
    }
    private void showOrdersView(List<Order> orders) {
        orderView.setListOrders(orders);
        orderView.setVisible(true);

    }

}
