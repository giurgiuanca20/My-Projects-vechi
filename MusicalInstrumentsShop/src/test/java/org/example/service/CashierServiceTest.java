package org.example.service;

import org.example.model.role.Cashier;
import org.example.repository.role.CashierRepository;
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

class CashierServiceTest {

    private CashierService cashierService;

    @Mock
    private CashierRepository mockCashierRepository;

    @Mock
    private SecurityService mockSecurityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cashierService = new CashierService(mockCashierRepository, mockSecurityService);
    }

    @Test
    void registerCashier_Success() throws SQLException {
        String username = "testUsername@gmail.com";
        String password = "testPassword31@";
        String name = "JohnDoe";
        String phone = "1234567890";

        Cashier cashier = new Cashier();
        cashier.setUsername(username);
        cashier.setPassword(password);
        cashier.setName(name);
        cashier.setPhone(phone);

        when(mockSecurityService.encodePassword(password)).thenReturn("encodedPassword");
        when(mockCashierRepository.createCashier(any(Cashier.class))).thenReturn(cashier);

        cashierService.registerCashier(username, password, name, phone);

        verify(mockSecurityService, times(1)).encodePassword(password);
        verify(mockCashierRepository, times(1)).createCashier(any(Cashier.class));
    }

    @Test
    void updateCashier_Success() throws SQLException {
        Long cashierId = 1L;
        String name = "UpdatedName";
        String phone = "9876543210";

        cashierService.updateCashier(cashierId, name, phone);

        verify(mockCashierRepository, times(1)).updateCashierById(cashierId, name, phone);
    }

    @Test
    void deleteCashier_Success() throws SQLException {
        Long cashierId = 1L;

        cashierService.deleteCashier(cashierId);

        verify(mockCashierRepository, times(1)).deleteCashierById(cashierId);
    }

    @Test
    void getAllCashiers_Success() throws SQLException {
        List<Cashier> cashiers = new ArrayList<>();
        Cashier cashier1 = new Cashier();
        cashier1.setId(1L);
        cashier1.setName("Cashier 1");
        cashiers.add(cashier1);

        Cashier cashier2 = new Cashier();
        cashier2.setId(2L);
        cashier2.setName("Cashier 2");
        cashiers.add(cashier2);

        when(mockCashierRepository.getAllCashiers()).thenReturn(cashiers);

        List<Cashier> result = cashierService.getAllCashiers();

        assertEquals(cashiers.size(), result.size());
        assertEquals(cashiers.get(0).getName(), result.get(0).getName());
        assertEquals(cashiers.get(1).getName(), result.get(1).getName());
    }
}