package org.example.model.validation;

import org.example.model.role.Cashier;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CashierValidator {

  private static final String PHONE_VALIDATION_REGEX = "^\\d{10}$";
  public static final int MIN_NAME_LENGTH = 2;

  private final Cashier cashier;
  private final List<String> errors;


  public CashierValidator(Cashier cashier) {
    errors = new ArrayList<>();
    this.cashier = cashier;
  }

  public boolean validate() {
    validateName(this.cashier.getName());
    validatePhone(this.cashier.getPhone());
    return this.errors.isEmpty();
  }

  private void validateName(String name) {
    if (name.length() < MIN_NAME_LENGTH) {
      errors.add("Name too short!");
    }
    if (containsSpecialCharacter(name)) {
      errors.add("Name can't contain a special character!");
    }
  }

  private void validatePhone(String phone) {
    if (!Pattern.compile(PHONE_VALIDATION_REGEX).matcher(phone).matches()) {
      errors.add("Invalid phone number!");
    }
  }

  private boolean containsSpecialCharacter(String password) {
    return password.matches(".*[^a-zA-Z0-9 ].*");
  }


  public List<String> getErrors() {
    return errors;
  }
}
