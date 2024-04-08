package org.example.repository.cart;

import org.example.model.cart.Order;
import org.example.repository.role.CashierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderRepositorySQLTest {

    private OrderRepositorySQL orderRepository;

    @Mock
    private Connection mockConnection;
    @Mock
    private CartRepository mockCartRepository;
    @Mock
    private CashierRepository mockCashierRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderRepository = new OrderRepositorySQL(mockConnection, mockCartRepository, mockCashierRepository);

    }


    @Test
    void getAllOrders_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getInt("product_number")).thenReturn(2);
        when(mockResultSet.getInt("money")).thenReturn(50);

        List<Order> orders = orderRepository.getAllOrders();

        verify(mockStatement, times(1)).executeQuery();
        assertEquals(1, orders.size());
        assertEquals(1L, orders.get(0).getId());
        assertEquals(2, orders.get(0).getNrProducts());
        assertEquals(50, orders.get(0).getTotalPrice());
    }


    @Test
    void addOrder_Success() throws SQLException {

        PreparedStatement mockInsertOrderStatement = mock(PreparedStatement.class);
        PreparedStatement mockInsertProductCartStatement = mock(PreparedStatement.class);
        ResultSet mockGeneratedKeys = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockInsertOrderStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockInsertProductCartStatement);
        when(mockInsertOrderStatement.getGeneratedKeys()).thenReturn(mockGeneratedKeys);
        when(mockGeneratedKeys.next()).thenReturn(true);
        when(mockGeneratedKeys.getLong(1)).thenReturn(1L);

        Order order = new Order();
        order.setNrProducts(2);
        order.setTotalPrice(50);
        order.setUserId(123L);

        orderRepository.addOrder(order);

        verify(mockInsertOrderStatement, times(1)).executeUpdate();
        verify(mockInsertProductCartStatement, times(1)).executeUpdate();
        verify(mockCartRepository, times(1)).setCheckoutYes(order.getUserId());
    }


    @Test
    void processOrder_Success() throws SQLException {

        PreparedStatement mockGetCustomerIdStatement = mock(PreparedStatement.class);
        PreparedStatement mockAddCashierOrderStatement = mock(PreparedStatement.class);
        PreparedStatement mockUpdateOrderStatement = mock(PreparedStatement.class);
        PreparedStatement mockDeleteOrderUsrStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockCashierRepository.getCashierIdFromUserId(anyLong())).thenReturn(1L);
        when(mockConnection.prepareStatement(anyString())).thenReturn(
                mockGetCustomerIdStatement,
                mockAddCashierOrderStatement,
                mockUpdateOrderStatement,
                mockDeleteOrderUsrStatement
        );
        when(mockGetCustomerIdStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("user_id")).thenReturn(123L);

        Long orderId = 1L;
        Long userId = 123L;

        orderRepository.processOrder(orderId, userId);

        verify(mockCartRepository, times(1)).getCartIdByUserId(userId);
        verify(mockCartRepository, times(1)).setCheckoutNo(anyLong());
        verify(mockAddCashierOrderStatement, times(1)).executeUpdate();
        verify(mockUpdateOrderStatement, times(1)).executeUpdate();
        verify(mockCartRepository, times(1)).deleteFromCartId(anyLong());
        verify(mockDeleteOrderUsrStatement, times(1)).executeUpdate();
    }
}