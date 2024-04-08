package org.example.repository.role;

import org.example.model.role.Cashier;

import java.sql.SQLException;
import java.util.List;

public interface CashierRepository{
  Cashier createCashier(Cashier cashier) throws SQLException;
  List<Cashier> getAllCashiers() throws SQLException;
  Long getCashierIdFromUserId(Long idUser) throws SQLException;
  void updateCashierById(Long cashierId, String name, String phone) throws SQLException;
  void deleteCashierById(Long cashierId) throws SQLException;
}
