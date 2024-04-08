package org.example.service;

import org.example.model.role.Customer;
import org.example.model.security.User;
import org.example.model.validation.CustomerValidator;
import org.example.model.validation.Notification;
import org.example.model.validation.UserValidator;
import org.example.repository.role.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerService customerService;

    @Mock
    private CustomerRepository mockCustomerRepository;

    @Mock
    private SecurityService mockSecurityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(mockCustomerRepository, mockSecurityService);
    }

    @Test
    void registerCustomer_Success() throws SQLException {
        String username = "testUsername@gmail.com";
        String password = "testPassword31@";
        String name = "JohnDoe";
        String cnp = "1234567890123";
        String card = "1234567890123456";
        String address = "TestAddress";

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setName(name);
        customer.setCNP(cnp);
        customer.setCard(card);
        customer.setAddress(address);

        when(mockSecurityService.encodePassword(password)).thenReturn("encodedPassword");
        when(mockCustomerRepository.addCustomer(any(Customer.class))).thenReturn(customer);

        Notification<Customer> registrationNotification = customerService.registerCustomer(username, password, name, cnp, card, address);

        assertEquals(customer, registrationNotification.getResult());

    }
    @Test
    void testGetAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        when(mockCustomerRepository.getAllCustomers()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(customers, result);
    }

    @Test
    void testUpdateCustomer() throws SQLException {

        Long customerId = 1L;
        String name = "John Doe";
        String cnp = "1234567890123";
        String card = "1234567890123456";
        String address = "Test Address";

        customerService.updateCustomer(customerId, name, cnp, card, address);

        verify(mockCustomerRepository, times(1)).updateCustomerById(eq(customerId), eq(name), eq(cnp), eq(card), eq(address));
    }

    @Test
    void testDeleteCustomer() throws SQLException {
        Long customerId = 1L;

        customerService.deleteCustomer(customerId);

        verify(mockCustomerRepository, times(1)).deleteCutomerById(eq(customerId));
    }
}
