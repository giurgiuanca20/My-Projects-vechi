package org.example.repository.cart;

import org.example.model.cart.EProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductRepositorySQLTest {

    private ProductRepositorySQL productRepository;

    @Mock
    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productRepository = new ProductRepositorySQL(mockConnection);
    }

    @Test
    void createProduct_Success() throws SQLException {
        EProduct product = EProduct.CELLOS;

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        productRepository.create(product);

        verify(mockStatement, times(1)).setString(1, product.getName());
        verify(mockStatement, times(1)).setInt(2, product.getPrice());
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void getProductIdByName_Success() throws SQLException {
        String productName = "Guitars";
        long expectedProductId = 1L;

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("id")).thenReturn(expectedProductId);

        long productId = productRepository.getProductIdByName(productName);

        verify(mockStatement, times(1)).setString(1, productName);
        verify(mockStatement, times(1)).executeQuery();
        assertEquals(expectedProductId, productId);
    }
}