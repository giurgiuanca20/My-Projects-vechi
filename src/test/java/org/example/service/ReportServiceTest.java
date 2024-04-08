package org.example.service;

import org.example.model.cart.Order;
import org.example.model.security.Report;
import org.example.repository.cart.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    private ReportService reportService;

    @Mock
    private ReportRepository mockReportRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportService = new ReportService(mockReportRepository);
    }

    @Test
    void getReport_Success() throws SQLException {
        String name = "Test Report";
        Date dateStart = new Date(System.currentTimeMillis());
        Date dateEnd = new Date(System.currentTimeMillis() + 86400000);
        Report expectedReport = new Report();

        when(mockReportRepository.getReport(any(Report.class))).thenReturn(getMockOrders());

        Report report = reportService.getReport(name, dateStart, dateEnd);

    }

    private List<Order> getMockOrders() {
        List<Order> orders = new ArrayList<>();
        return orders;
    }

}