package org.example.service;

import org.example.model.role.Cashier;
import org.example.model.security.User;
import org.example.model.validation.CashierValidator;
import org.example.model.validation.Notification;
import org.example.model.validation.UserValidator;
import org.example.repository.role.CashierRepository;

import java.sql.SQLException;
import java.util.List;

public class CashierService {

    private final CashierRepository cashierRepository;
    private final SecurityService securityService;

    public CashierService(CashierRepository cashierRepository, SecurityService securityService) {
        this.cashierRepository = cashierRepository;
        this.securityService = securityService;
    }

    public Notification<Cashier> registerCashier(String username, String password, String name, String phone) {
        Cashier cashier = new Cashier();
        cashier.setUsername(username);
        cashier.setPassword(password);
        cashier.setName(name);
        cashier.setPhone(phone);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        UserValidator userValidator = new UserValidator(user);
        CashierValidator cashierValidator = new CashierValidator(cashier);

        Notification<Cashier> cashierRegistrationNotification = new Notification<>();

        boolean isValidUser = userValidator.validate();
        boolean isValidCashier = cashierValidator.validate();

        if (!isValidUser) {
            userValidator.getErrors().forEach(cashierRegistrationNotification::addError);
        } else if (!isValidCashier) {
            cashierValidator.getErrors().forEach(cashierRegistrationNotification::addError);
        } else {
            cashier.setPassword(securityService.encodePassword(password));
            try {
                Cashier createdCashier = cashierRepository.createCashier(cashier);
                cashierRegistrationNotification.setResult(createdCashier);
            } catch (SQLException e) {
                e.printStackTrace();
                cashierRegistrationNotification.addError("An error occurred while registering the cashier.");
            }
        }
        return cashierRegistrationNotification;
    }
    public void updateCashier(Long cashierId, String name, String phone){
        try {
            cashierRepository.updateCashierById(cashierId,name,phone);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCashier(Long cashierId){
        try {
            cashierRepository.deleteCashierById(cashierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cashier> getAllCashiers() throws SQLException {
        return cashierRepository.getAllCashiers();
    }

}
