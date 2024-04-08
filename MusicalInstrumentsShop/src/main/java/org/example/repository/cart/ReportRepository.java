package org.example.repository.cart;

import org.example.model.cart.Order;
import org.example.model.security.Report;

import java.sql.SQLException;
import java.util.List;

public interface ReportRepository {

  List<Order> getReport(Report report)throws SQLException;

}
