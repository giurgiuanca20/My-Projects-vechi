package org.example.repository.cart;

import org.example.model.cart.Order;
import org.example.model.security.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportRepositorySQL implements ReportRepository {
    private final Connection connection;

    public ReportRepositorySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> getReport(Report report) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String orderIdQuery = "SELECT co.order_id " +
                "FROM cashier_order co " +
                "JOIN cashier c ON co.cashier_id = c.id " +
                "WHERE c.name = ?";
        String orderQuery = "SELECT * FROM orders " +
                "WHERE id IN (%s) AND date BETWEEN ? AND ?";
        try (PreparedStatement orderIdStatement = connection.prepareStatement(orderIdQuery);
             PreparedStatement orderStatement = connection.prepareStatement(String.format(orderQuery, "?"))) {

            orderIdStatement.setString(1, report.getName());
            ResultSet orderIdResultSet = orderIdStatement.executeQuery();

            List<Integer> orderIds = new ArrayList<>();
            while (orderIdResultSet.next()) {
                orderIds.add(orderIdResultSet.getInt("order_id"));
            }

            if (orderIds.isEmpty()) {
                return orders;
            }

            for (Integer orderId : orderIds) {
                orderStatement.setInt(1, orderId);
                orderStatement.setDate(2, report.getDateStart());
                orderStatement.setDate(3, report.getDateEnd());

                ResultSet resultSet = orderStatement.executeQuery();

                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getLong("id"));
                    order.setDate(resultSet.getDate("date"));
                    order.setNrProducts(resultSet.getInt("product_number"));
                    order.setTotalPrice(resultSet.getInt("money"));

                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error while fetching report: " + e.getMessage());
        }

        return orders;
    }
}
