package org.example.repository.cart;

import org.example.model.cart.Order;
import org.example.repository.role.CashierRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositorySQL implements OrderRepository {
    private final Connection connection;
    private final CartRepository cartRepository;
    private final CashierRepository cashierRepository;

    public OrderRepositorySQL(Connection connection,CartRepository cartRepository,CashierRepository cashierRepository) {

        this.connection = connection;
        this.cartRepository=cartRepository;
        this.cashierRepository=cashierRepository;
    }

    @Override
    public void addOrder(Order order) throws SQLException {
        try (
                PreparedStatement insertOrderStatement = connection.prepareStatement(
                "INSERT INTO orders (product_number, money) VALUES (?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement insertProductCartStatement = connection.prepareStatement(
                     "INSERT INTO order_user (order_id, user_id) VALUES (?, ?)")) {

            insertOrderStatement.setInt(1, order.getNrProducts());
            insertOrderStatement.setInt(2, order.getTotalPrice());
            insertOrderStatement.executeUpdate();

            ResultSet generatedKeys = insertOrderStatement.getGeneratedKeys();
            long orderId;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getLong(1);
            } else {
                throw new SQLException("Failed to create order, no ID obtained.");
            }

            insertProductCartStatement.setLong(1, orderId);
            insertProductCartStatement.setLong(2, order.getUserId());
            insertProductCartStatement.executeUpdate();

            cartRepository.setCheckoutYes(order.getUserId());
        }
    }

    @Override
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM orders WHERE id NOT IN (SELECT order_id FROM cashier_order)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setNrProducts(resultSet.getInt("product_number"));
                order.setTotalPrice(resultSet.getInt("money"));

                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public void processOrder(Long idOrder, Long idUser) throws SQLException {
        Long idCashier = null;
        Long idCustomer = null;
        Long idCart = null;

        try {
            idCashier=cashierRepository.getCashierIdFromUserId(idUser);

            try (PreparedStatement getCustomerIdStatement = connection.prepareStatement(
                    "SELECT user_id FROM order_user WHERE order_id = ?")) {
                getCustomerIdStatement.setLong(1, idOrder);
                ResultSet resultSet = getCustomerIdStatement.executeQuery();
                if (resultSet.next()) {
                    idCustomer = resultSet.getLong("user_id");
                }
            }

            idCart=cartRepository.getCartIdByUserId(idCustomer);

            cartRepository.setCheckoutNo(idCart);

            try (PreparedStatement addCashierOrderStatement = connection.prepareStatement(
                    "INSERT INTO cashier_order (order_id, cashier_id) VALUES (?, ?)")) {
                addCashierOrderStatement.setLong(1, idOrder);
                addCashierOrderStatement.setLong(2, idCashier);
                addCashierOrderStatement.executeUpdate();
            }

            try (PreparedStatement updateOrderStatement = connection.prepareStatement(
                    "UPDATE orders SET date = ? WHERE id = ?")) {
                updateOrderStatement.setDate(1, new Date(System.currentTimeMillis()));
                updateOrderStatement.setLong(2, idOrder);
                updateOrderStatement.executeUpdate();
            }

            cartRepository.deleteFromCartId(idCustomer);


            try (PreparedStatement deleteOrderUsrStatement = connection.prepareStatement(
                    "DELETE FROM order_user WHERE order_id  = ?")) {
                deleteOrderUsrStatement.setLong(1, idOrder);
                deleteOrderUsrStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
