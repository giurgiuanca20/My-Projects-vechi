package org.example.repository.role;

import org.example.model.role.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerRepository {
  Customer addCustomer(Customer customer) throws SQLException;
  List<Customer> getAllCustomers() throws SQLException;
  int getFidelity(Long idUser)throws SQLException;
  void updateCustomerById(Long customerId, String name, String cnp,String cart,String address) throws SQLException;
  void updateFidelity(Long idUser,int remainingFidelityPoints) throws SQLException;
  void deleteCutomerById(Long customerId) throws SQLException;
}
