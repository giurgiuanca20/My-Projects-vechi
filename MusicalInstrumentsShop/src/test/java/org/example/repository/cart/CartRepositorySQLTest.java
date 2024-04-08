package org.example.repository.cart;

import org.example.model.cart.Product;
import org.example.repository.security.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CartRepositorySQLTest {

    private CartRepositorySQL cartRepository;

    @Mock
    private Connection mockConnection;
    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartRepository = new CartRepositorySQL(mockConnection, mockProductRepository,mockUserRepository);
    }


    @Test
    void createCart_Success() throws SQLException {
        PreparedStatement mockInsertStatement = mock(PreparedStatement.class);
        ResultSet mockGeneratedKeys = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockInsertStatement);
        when(mockInsertStatement.getGeneratedKeys()).thenReturn(mockGeneratedKeys);
        when(mockGeneratedKeys.next()).thenReturn(true);
        when(mockGeneratedKeys.getLong(1)).thenReturn(1L);

        Long cartId = cartRepository.createCart();

        verify(mockInsertStatement, times(1)).setString(1, "no");
        verify(mockInsertStatement, times(1)).executeUpdate();
        assertEquals(1L, cartId);
    }



    @Test
    void removeAll_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        cartRepository.removeAll();

        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void findAll_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("name")).thenReturn("Guitars");
        when(mockResultSet.getInt("price")).thenReturn(500);

        List<Product> allProducts = cartRepository.findAll();

        verify(mockStatement, times(1)).executeQuery();
        assertEquals(1, allProducts.size());
        assertEquals("Guitars", allProducts.get(0).getName());
        assertEquals(500, allProducts.get(0).getPrice());
    }

    @Test
    void deleteFromUserCartById_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        cartRepository.deleteFromUserCartById(123L);

        verify(mockStatement, times(1)).setLong(1, 123L);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void deleteCart_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        cartRepository.deleteCart(456L);

        verify(mockStatement, times(1)).setLong(1, 456L);
        verify(mockStatement, times(1)).executeUpdate();
    }


    @Test
    void setCheckoutNo_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        cartRepository.setCheckoutNo(456L);

        verify(mockStatement, times(1)).setString(1, "no");
        verify(mockStatement, times(1)).setLong(2, 456L);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void getCartIdByUserId_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("cart_id")).thenReturn(789L);

        cartRepository.getCartIdByUserId(123L);

        verify(mockStatement, times(1)).setLong(1, 123L);
        verify(mockStatement, times(1)).executeQuery();
    }

}