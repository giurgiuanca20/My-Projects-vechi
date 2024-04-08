package org.example.model.validation;

import org.example.model.role.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerValidator {

  private static final String CNP_VALIDATION_REGEX = "^\\d{13}$";
  private static final String CARD_VALIDATION_REGEX = "^\\d{16}$";
  public static final int MIN_NAME_LENGTH = 2;

  private final Customer customer;
  private final List<String> errors;

  public CustomerValidator(Customer customer) {
    errors = new ArrayList<>();
    this.customer = customer;
  }

  public boolean validate() {
    validateName(this.customer.getName());
    validateCNP(this.customer.getCNP());
    validateCard(this.customer.getCard());
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


  private void validateCNP(String cnp) {
    if (!Pattern.compile(CNP_VALIDATION_REGEX).matcher(cnp).matches()) {
      errors.add("Invalid CNP number!");
    }
  }

  private void validateCard(String card) {
    if (!Pattern.compile(CARD_VALIDATION_REGEX).matcher(card).matches()) {
      errors.add("Invalid card number!");
    }
  }

  private boolean containsSpecialCharacter(String password) {
    return password.matches(".*[^a-zA-Z0-9 ].*");
  }

  public List<String> getErrors() {
    return errors;
  }
}
