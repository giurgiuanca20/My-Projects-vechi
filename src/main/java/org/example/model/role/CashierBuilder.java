package org.example.model.role;

import java.time.LocalDate;

public class CashierBuilder {

  private final Cashier cashier;

  public CashierBuilder() {
    cashier = new Cashier();
  }

  public CashierBuilder setId(Long id) {
    cashier.setId(id);
    return this;
  }

  public CashierBuilder setUsername(String username) {
    cashier.setUsername(username);
    return this;
  }

  public CashierBuilder setPassword(String password) {
    cashier.setPassword(password);
    return this;
  }

  public CashierBuilder setName(String name) {
    cashier.setName(name);
    return this;
  }

  public CashierBuilder setPhone(String phone) {
    cashier.setPhone(phone);
    return this;
  }

  public Cashier build() {
    return cashier;
  }
}
