package org.example.repository.cart;

import org.example.model.cart.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderRepository {

  void addOrder(Order order)throws SQLException;
  void processOrder(Long idOrder,Long idUser) throws SQLException;
  List<Order> getAllOrders() throws SQLException;
}
