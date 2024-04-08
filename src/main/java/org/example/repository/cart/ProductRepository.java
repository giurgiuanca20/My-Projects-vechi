package org.example.repository.cart;

import org.example.model.cart.EProduct;
import org.example.model.cart.Product;
import org.example.model.security.ERole;
import org.example.model.security.Role;
import org.example.model.security.User;

import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
  void create(EProduct product);
  Long getProductIdByName(String productName) throws SQLException;
}
