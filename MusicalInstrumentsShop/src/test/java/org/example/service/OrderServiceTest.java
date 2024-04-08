package org.example.service;

import org.example.model.cart.Order;
import org.example.repository.cart.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private OrderRepository mockOrderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(mockOrderRepository);
    }

    @Test
    void addOrder_Success() throws SQLException {
        Order order = new Order();

        doNothing().when(mockOrderRepository).addOrder(order);

        orderService.addOrder(order);

        verify(mockOrderRepository, times(1)).addOrder(order);
    }

    @Test
    void getAllOrders_Success() throws SQLException {
        List<Order> expectedOrders = new ArrayList<>();


        when(mockOrderRepository.getAllOrders()).thenReturn(expectedOrders);

        List<Order> orders = orderService.getAllOrders();

        assertEquals(expectedOrders, orders);
    }

    @Test
    void processOrder_Success() throws SQLException {
        Long idOrder = 1L;
        Long idUser = 1L;

        doNothing().when(mockOrderRepository).processOrder(idOrder, idUser);

        orderService.processOrder(idOrder, idUser);

        verify(mockOrderRepository, times(1)).processOrder(idOrder, idUser);
    }
}