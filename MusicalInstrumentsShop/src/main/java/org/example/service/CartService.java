package org.example.service;

import org.example.model.cart.EProduct;
import org.example.model.cart.Fidelity;
import org.example.model.cart.Product;
import org.example.repository.cart.CartRepository;
import org.example.repository.role.CustomerRepository;

import java.sql.SQLException;
import java.util.List;

public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    private final SecurityService securityService;


    public CartService(CartRepository cartRepository, SecurityService securityService, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.securityService = securityService;
        this.customerRepository=customerRepository;
    }
    public int getFidelityPoints() throws SQLException {
        long userId = securityService.getCurrentUserId();
        int fidelity = customerRepository.getFidelity(userId);
        return fidelity;
    }
    public void deleteFlotingCart() throws SQLException {
        List<Long> guestsIds=securityService.getGuestIds();
        cartRepository.deleteFlotingCart(guestsIds);

    }
    public List<Product> populateCart() throws SQLException {
        long userId = securityService.getCurrentUserId();
        List<Product> products = cartRepository.getCart(userId);
        return products;
    }
    public void substractFidelityPoints(int usedFidelityPoints){
        long userId = securityService.getCurrentUserId();
        int remainingFidelityPoints= 0;
        try {
            remainingFidelityPoints = getFidelityPoints()-usedFidelityPoints;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            customerRepository.updateFidelity(userId,remainingFidelityPoints);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addFidelityPoints(int totalPrice){
        long userId = securityService.getCurrentUserId();
        int newFidelityPoints= 0;
        try {
            newFidelityPoints=getFidelityPoints()+totalPrice* Fidelity.getFidelityValue()/100;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            customerRepository.updateFidelity(userId,newFidelityPoints);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getCheckout() throws SQLException {
        long userId = securityService.getCurrentUserId();
        String checkout = cartRepository.getCheckout(userId);
        return checkout;
    }
    public void addProductToCart(EProduct product) throws SQLException {
        try {
            long userId = securityService.getCurrentUserId();
            cartRepository.addProductToCart(userId, product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
