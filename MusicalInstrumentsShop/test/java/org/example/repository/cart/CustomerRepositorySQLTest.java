package org.example.repository.cart;

import org.example.model.role.Customer;
import org.example.repository.role.CustomerRepositorySQL;
import org.example.repository.security.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CustomerRepositorySQLTest {

    private CustomerRepositorySQL customerRepository;

    @Mock
    private Connection mockConnection;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CartRepository mockCartRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerRepository = new CustomerRepositorySQL(mockConnection, mockUserRepository, mockCartRepository);
    }


    @Test
    void getAllCustomers_Success() throws SQLException {
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("JohnDoe");

        List<Customer> customers = customerRepository.getAllCustomers();

        verify(mockStatement, times(1)).executeQuery();
        assertEquals(1, customers.size());
        assertEquals(1L, customers.get(0).getId());
        assertEquals("JohnDoe", customers.get(0).getName());
    }
    @Test
    void getFidelity_Success() throws SQLException {
        PreparedStatement mockGetUserIdStatement = mock(PreparedStatement.class);
        ResultSet mockUserIdResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement("SELECT customer_id FROM customer_user WHERE user_id = ?")).thenReturn(mockGetUserIdStatement);
        when(mockGetUserIdStatement.executeQuery()).thenReturn(mockUserIdResultSet);
        when(mockUserIdResultSet.next()).thenReturn(true);
        when(mockUserIdResultSet.getLong("customer_id")).thenReturn(1L);

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement("SELECT points_fidelity FROM customer WHERE id = ?")).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("points_fidelity")).thenReturn(10);

        int fidelity = customerRepository.getFidelity(1L);

        verify(mockGetUserIdStatement, times(1)).setLong(1, 1L);
        verify(mockGetUserIdStatement, times(1)).executeQuery();
        verify(mockStatement, times(1)).setLong(1, 1L);
        verify(mockStatement, times(1)).executeQuery();
        assertEquals(10, fidelity);
    }



    @Test
    void updateFidelity_Success() throws SQLException {
        PreparedStatement mockGetUserIdStatement = mock(PreparedStatement.class);
        ResultSet mockUserIdResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement("SELECT customer_id FROM customer_user WHERE user_id = ?")).thenReturn(mockGetUserIdStatement);
        when(mockGetUserIdStatement.executeQuery()).thenReturn(mockUserIdResultSet);
        when(mockUserIdResultSet.next()).thenReturn(true);
        when(mockUserIdResultSet.getLong("customer_id")).thenReturn(1L);

        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement("UPDATE customer SET points_fidelity = ? WHERE id = ?")).thenReturn(mockStatement);

        customerRepository.updateFidelity(1L, 20);

        verify(mockStatement, times(1)).setInt(1, 20);
        verify(mockStatement, times(1)).setLong(2, 1L);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void updateCustomerById_Success() throws SQLException {

        PreparedStatement mockUpdateStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement("UPDATE customer SET name = ?, cnp = ?, card=?, address=? WHERE id = ?")).thenReturn(mockUpdateStatement);


        customerRepository.updateCustomerById(1L, "John Doe", "1234567890123", "1234 5678 9012 3456", "123 Main Street");


        verify(mockUpdateStatement, times(1)).setString(1, "John Doe");
        verify(mockUpdateStatement, times(1)).setString(2, "1234567890123");
        verify(mockUpdateStatement, times(1)).setString(3, "1234 5678 9012 3456");
        verify(mockUpdateStatement, times(1)).setString(4, "123 Main Street");
        verify(mockUpdateStatement, times(1)).setLong(5, 1L);
        verify(mockUpdateStatement, times(1)).executeUpdate();
    }


}
