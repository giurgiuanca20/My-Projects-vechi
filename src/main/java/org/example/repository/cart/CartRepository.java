package org.example.repository.cart;

import org.example.model.cart.EProduct;
import org.example.model.cart.Product;

import java.sql.SQLException;
import java.util.List;

public interface CartRepository {

  void deleteCart(Long cartId)throws SQLException;
  void deleteFromUserCartById(Long userId)throws SQLException;
  void deleteFlotingCart(List<Long> guestsIds) throws SQLException;
  void addProductToCart(Long userId, EProduct product)throws SQLException;
  List<Product> getCart(Long userId)throws SQLException;
  String getCheckout(Long userId)throws SQLException;
  void setCheckoutYes(Long userId) throws SQLException;
  void setCheckoutNo(Long idCart) throws SQLException;
  Long getCartIdByUserId(Long userId) throws SQLException;
  void deleteFromCartId(Long idCustomer) throws SQLException ;
  Long createCart() throws SQLException;
  void insertUserCart(Long userId,Long cartId)throws SQLException;
  void removeAll() throws SQLException;
  List<Product> findAll() throws SQLException;
}
