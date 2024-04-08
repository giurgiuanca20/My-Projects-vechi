package org.example.service;

import org.example.model.role.Customer;
import org.example.model.security.User;
import org.example.model.validation.CustomerValidator;
import org.example.model.validation.Notification;
import org.example.model.validation.UserValidator;
import org.example.repository.role.CustomerRepository;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final SecurityService securityService;

    public CustomerService(CustomerRepository customerRepository, SecurityService securityService) {
        this.customerRepository = customerRepository;
        this.securityService = securityService;
    }
    public void updateCustomer(Long customerId, String name, String cnp,String card, String address){
        try {
            customerRepository.updateCustomerById(customerId,name,cnp,card,address);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(Long customerId){
        try {
            customerRepository.deleteCutomerById(customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Notification<Customer> registerCustomer(String username, String password, String name, String cnp, String card, String address) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setName(name);
        customer.setCNP(cnp);
        customer.setCard(card);
        customer.setAddress(address);


        Notification<Customer> registrationNotification = new Notification<>();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        UserValidator userValidator = new UserValidator(user);
        CustomerValidator customerValidator = new CustomerValidator(customer);

        boolean isValidUser = userValidator.validate();
        boolean isValidCustomer = customerValidator.validate();

        if (!isValidUser) {
            userValidator.getErrors().forEach(registrationNotification::addError);
        } else if (!isValidCustomer) {
            customerValidator.getErrors().forEach(registrationNotification::addError);
        } else {
            customer.setPassword(securityService.encodePassword(password));
            try {
                Customer createdCustomer = customerRepository.addCustomer(customer);
                registrationNotification.setResult(createdCustomer);
            } catch (SQLException e) {
                e.printStackTrace();
                registrationNotification.addError("An error occurred while saving customer data.");
            }
        }
        return registrationNotification;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerRepository.getAllCustomers();
    }

}
