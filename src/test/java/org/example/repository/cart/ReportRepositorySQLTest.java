package org.example.repository.cart;

import org.example.model.cart.Order;
import org.example.model.security.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ReportRepositorySQLTest {

    private ReportRepositorySQL reportRepository;

    @Mock
    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportRepository = new ReportRepositorySQL(mockConnection);
    }

    @Test
    void getReport_Success() throws SQLException {
        Report report = new Report();
        report.setName("CashierName");
        report.setDateStart(java.sql.Date.valueOf("2022-01-01"));
        report.setDateEnd(java.sql.Date.valueOf("2022-01-31"));

        List<Order> expectedOrders = new ArrayList<>();
        Order order = new Order();
        order.setId(1L);
        order.setNrProducts(2);
        order.setTotalPrice(100);
        expectedOrders.add(order);

        PreparedStatement mockOrderIdStatement = mock(PreparedStatement.class);
        PreparedStatement mockOrderStatement = mock(PreparedStatement.class);
        ResultSet mockOrderIdResultSet = mock(ResultSet.class);
        ResultSet mockOrderResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockOrderIdStatement, mockOrderStatement);
        when(mockOrderIdStatement.executeQuery()).thenReturn(mockOrderIdResultSet);
        when(mockOrderIdResultSet.next()).thenReturn(true, false);
        when(mockOrderIdResultSet.getInt("order_id")).thenReturn(1);
        when(mockOrderStatement.executeQuery()).thenReturn(mockOrderResultSet);
        when(mockOrderResultSet.next()).thenReturn(true, false);
        when(mockOrderResultSet.getLong("id")).thenReturn(1L);
        when(mockOrderResultSet.getInt("product_number")).thenReturn(2);
        when(mockOrderResultSet.getInt("money")).thenReturn(100);

        List<Order> actualOrders = reportRepository.getReport(report);

        verify(mockOrderIdStatement, times(1)).setString(1, report.getName());
        verify(mockOrderStatement, times(1)).setInt(1, 1);
        verify(mockOrderStatement, times(1)).setDate(2, report.getDateStart());
        verify(mockOrderStatement, times(1)).setDate(3, report.getDateEnd());
        assertEquals(expectedOrders.size(), actualOrders.size());
        assertEquals(expectedOrders.get(0).getId(), actualOrders.get(0).getId());
        assertEquals(expectedOrders.get(0).getNrProducts(), actualOrders.get(0).getNrProducts());
        assertEquals(expectedOrders.get(0).getTotalPrice(), actualOrders.get(0).getTotalPrice());
    }
}