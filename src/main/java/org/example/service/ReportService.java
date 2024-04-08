package org.example.service;

import org.example.model.cart.Order;
import org.example.model.security.Report;
import org.example.model.validation.ReportValidator;
import org.example.repository.cart.ReportRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report getReport(String name, Date dateStart, Date dateEnd) throws SQLException {
        Report report = createReport(name, dateStart, dateEnd);

        if (report != null) {
            List<Order> listReport = fetchOrdersForReport(report);

            if (!listReport.isEmpty()) {
                updateReportWithOrderData(report, listReport);
            }
        }

        return report;
    }

    private Report createReport(String name, Date dateStart, Date dateEnd) {
        Report report = new Report();
        report.setName(name);
        report.setDateStart(dateStart);
        report.setDateEnd(dateEnd);

        ReportValidator validator = new ReportValidator(report);
        boolean isValid = validator.validate();

        return isValid ? report : null;
    }

    private List<Order> fetchOrdersForReport(Report report) throws SQLException {
        try {
            return reportRepository.getReport(report);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void updateReportWithOrderData(Report report, List<Order> listReport) {
        int totalProducts = 0;
        int totalMoney = 0;
        int totalOrders = listReport.size();

        for (Order order : listReport) {
            totalProducts += order.getNrProducts();
            totalMoney += order.getTotalPrice();
        }

        report.setTotalProducts(totalProducts);
        report.setTotalMoney(totalMoney);
        report.setTotalCustomers(totalOrders);
    }
}
