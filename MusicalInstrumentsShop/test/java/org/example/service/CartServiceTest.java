package org.example.service;

import org.example.model.cart.EProduct;
import org.example.model.cart.Fidelity;
import org.example.model.cart.Product;
import org.example.repository.cart.CartRepository;
import org.example.repository.role.CustomerRepository;
import org.example.service.CartService;
import org.example.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartServiceTest {

    private CartService cartService;

    @Mock
    private CartRepository mockCartRepository;

    @Mock
    private CustomerRepository mockCustomerRepository;

    @Mock
    private SecurityService mockSecurityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(mockCartRepository, mockSecurityService, mockCustomerRepository);
    }

    @Test
    void getFidelityPoints_Success() throws SQLException {
        long userId = 1L;
        int expectedFidelityPoints = 100;
        when(mockSecurityService.getCurrentUserId()).thenReturn(userId);
        when(mockCustomerRepository.getFidelity(userId)).thenReturn(expectedFidelityPoints);

        int fidelityPoints = cartService.getFidelityPoints();

        assertEquals(expectedFidelityPoints, fidelityPoints);
    }

    @Test
    void populateCart_Success() throws SQLException {
        long userId = 1L;
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product("Product 1", 10));
        expectedProducts.add(new Product("Product 2", 20));
        when(mockSecurityService.getCurrentUserId()).thenReturn(userId);
        when(mockCartRepository.getCart(userId)).thenReturn(expectedProducts);

        List<Product> products = cartService.populateCart();

        assertEquals(expectedProducts, products);
    }


    @Test
    void addProductToCart_Success() throws SQLException {
        long userId = 1L;
        EProduct product = EProduct.DRUMS;

        when(mockSecurityService.getCurrentUserId()).thenReturn(userId);
        doNothing().when(mockCartRepository).addProductToCart(userId, product);

        cartService.addProductToCart(product);

        verify(mockCartRepository, times(1)).addProductToCart(userId, product);
    }

    @Test
    void getCheckout_Success() throws SQLException {
        long userId = 1L;
        String expectedCheckout = "Sample checkout string";
        when(mockSecurityService.getCurrentUserId()).thenReturn(userId);
        when(mockCartRepository.getCheckout(userId)).thenReturn(expectedCheckout);

        CartService cartService = new CartService(mockCartRepository, mockSecurityService, mockCustomerRepository);
        String checkout = cartService.getCheckout();

        assertEquals(expectedCheckout, checkout);
    }

    @Test
    void addFidelityPoints_Success() throws SQLException {
        long userId = 1L;
        int totalPrice = 100;
        int currentFidelityPoints = 50;
        int expectedNewFidelityPoints = currentFidelityPoints + (totalPrice * Fidelity.getFidelityValue() / 100);
        when(mockSecurityService.getCurrentUserId()).thenReturn(userId);
        when(mockCustomerRepository.getFidelity(userId)).thenReturn(currentFidelityPoints);

        CartService cartService = new CartService(mockCartRepository, mockSecurityService, mockCustomerRepository);
        cartService.addFidelityPoints(totalPrice);

        verify(mockCustomerRepository, times(1)).updateFidelity(userId, expectedNewFidelityPoints);
    }
    @Test
    void substractFidelityPoints_Success() throws SQLException {

        long userId = 1L;
        int usedFidelityPoints = 20;
        int currentFidelityPoints = 100;
        int expectedRemainingFidelityPoints = currentFidelityPoints - usedFidelityPoints;
        when(mockSecurityService.getCurrentUserId()).thenReturn(userId);
        when(mockCustomerRepository.getFidelity(userId)).thenReturn(currentFidelityPoints);

        CartService cartService = new CartService(mockCartRepository, mockSecurityService, mockCustomerRepository);
        cartService.substractFidelityPoints(usedFidelityPoints);

        verify(mockCustomerRepository, times(1)).updateFidelity(userId, expectedRemainingFidelityPoints);
    }

    @Test
    void deleteFlotingCart_Success() throws SQLException {
        List<Long> guestIds = Arrays.asList(1L, 2L, 3L);
        when(mockSecurityService.getGuestIds()).thenReturn(guestIds);

        CartService cartService = new CartService(mockCartRepository, mockSecurityService, mockCustomerRepository);
        cartService.deleteFlotingCart();

        verify(mockSecurityService, times(1)).getGuestIds();
        verify(mockCartRepository, times(1)).deleteFlotingCart(guestIds);
    }
}