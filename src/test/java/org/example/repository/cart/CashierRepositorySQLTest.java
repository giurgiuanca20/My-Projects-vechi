package org.example.repository.cart;

import org.example.model.role.Cashier;
import org.example.repository.role.CashierRepositorySQL;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CashierRepositorySQLTest {

    private CashierRepositorySQL cashierRepository;
    @Mock
    private Connection mockConnection;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cashierRepository = new CashierRepositorySQL(mockConnection, mockUserRepository);
    }

    @Test
    void getAllCashiers_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("phone")).thenReturn("123456789");

        List<Cashier> cashiers = cashierRepository.getAllCashiers();

        verify(mockStatement, times(1)).executeQuery();
        assertEquals(1, cashiers.size());
        assertEquals(1L, cashiers.get(0).getId());
        assertEquals("John Doe", cashiers.get(0).getName());
        assertEquals("123456789", cashiers.get(0).getPhone());
    }

    @Test
    void getCashierIdFromUserId_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("cashier_id")).thenReturn(1L);

        Long idCashier = cashierRepository.getCashierIdFromUserId(1L);

        verify(mockStatement, times(1)).setLong(1, 1L);
        verify(mockStatement, times(1)).executeQuery();
        assertEquals(1L, idCashier);
    }

    @Test
    void updateCashierById_Success() throws SQLException {
        PreparedStatement updateStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(updateStatement);

        cashierRepository.updateCashierById(1L, "NewName", "NewPhone");

        verify(updateStatement).setString(1, "NewName");
        verify(updateStatement).setString(2, "NewPhone");
        verify(updateStatement).setLong(3, 1L);
        verify(updateStatement).executeUpdate();
    }

}