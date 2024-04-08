package org.example.service;

import org.example.model.cart.Order;
import org.example.repository.cart.OrderRepository;

import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order) {
        try {
            orderRepository.addOrder(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderRepository.getAllOrders();
    }

    public void processOrder(Long idOrder, Long idUser) {
        try {
            orderRepository.processOrder(idOrder, idUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
