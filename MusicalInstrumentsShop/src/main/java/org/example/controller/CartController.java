package org.example.controller;

import org.example.model.cart.Order;
import org.example.model.security.ERole;
import org.example.service.CartService;
import org.example.service.CustomerService;
import org.example.service.OrderService;
import org.example.service.SecurityService;
import org.example.view.CartView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class CartController {
    private final CartView cartView;
    private final SecurityService securityService;
    private final OrderService orderService;
    private final CartService cartService;
    private final CustomerService customerService;

    public CartController(CartView cartView, SecurityService securityService, OrderService orderService, CartService cartService, CustomerService customerService) {
        this.cartView = cartView;
        this.securityService = securityService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.customerService = customerService;
        this.cartView.setCheckoutButtonListener(new CheckoutButtonListener());
        this.cartView.setUseFidelityListener(new UseFidelityListener());
    }

    private class UseFidelityListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            int fidelityPointsToUse = cartView.getTfUseFidelity();
            int totalPrice = cartView.getTotalPrice();
            int newTotalPrice = totalPrice - fidelityPointsToUse;
            cartView.setlTotalPrice(newTotalPrice);

        }
    }

    private class CheckoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (isCheckoutAllowed() && haveEnoughFidelityPoints()) {
                    Order order = createOrder();
                    boolean paymentSuccessful = processPayment();

                    if (paymentSuccessful) {
                        addOrder(order);
                        cartService.substractFidelityPoints(cartView.getTfUseFidelity());
                        cartService.addFidelityPoints(cartView.calculateTotalPriceInt());
                        handleSuccessfulPayment();
                    } else {
                        handleFailedPayment();
                    }
                }
            } catch (SQLException | RuntimeException ex) {
                handleException(ex);
            }
        }

        private boolean isCheckoutAllowed() throws SQLException {
            if (Objects.equals(cartService.getCheckout(), "no")) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The cart has already been checked out.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        private boolean haveEnoughFidelityPoints() throws SQLException {
            if (securityService.getCurrentUserRole().equals(ERole.GUEST))
                return true;
            if (cartView.getTfUseFidelity() <= cartView.getFidelityPoints()) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Not enough fidelity points.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        private Order createOrder() {
            Order order = new Order();
            order.setUserId(securityService.getCurrentUserId());
            order.setNrProducts(cartView.getNrProducts());
            order.setTotalPrice(cartView.getTotalPrice());
            return order;
        }

        private boolean processPayment() {
            Random random = new Random();
            int randomNumber = random.nextInt(100);
            if (randomNumber < 80) {
                return true;
            } else {
                int randomError = random.nextInt(2);
                if (randomError == 0) {
                    throw new RuntimeException("Not enough funds.");
                } else {
                    throw new RuntimeException("Database error, please try again later.");
                }
            }
        }

        private void addOrder(Order order) throws SQLException {
            orderService.addOrder(order);
        }

        private void handleSuccessfulPayment() {
            if (securityService.getCurrentUserRole() == ERole.GUEST) {
                cartView.setVisibleForGuest(true);
            }
            JOptionPane.showMessageDialog(null, "Payment successful. Order placed.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        private void handleFailedPayment() {
            JOptionPane.showMessageDialog(null, "Payment processing failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        private void handleException(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
