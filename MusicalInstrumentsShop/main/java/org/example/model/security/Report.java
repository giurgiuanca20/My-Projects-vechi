package org.example.model.security;

import java.sql.Date;

public class Report {
  private Long id;
private String name;
private Date dateStart;
private Date dateEnd;

private int totalProducts;
private int totalMoney;
private int totalCustomers;

  public void setTotalProducts(int totalProducts) {
    this.totalProducts = totalProducts;
  }

  public int getTotalProducts() {
    return totalProducts;
  }

  public int getTotalMoney() {
    return totalMoney;
  }

  public int getTotalCustomers() {
    return totalCustomers;
  }

  public void setTotalMoney(int totalMoney) {
    this.totalMoney = totalMoney;
  }

  public void setTotalCustomers(int totalCustomers) {
    this.totalCustomers = totalCustomers;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDateStart(Date dateStart) {
    this.dateStart = dateStart;
  }

  public void setDateEnd(Date dateEnd) {
    this.dateEnd = dateEnd;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Date getDateStart() {
    return dateStart;
  }

  public Date getDateEnd() {
    return dateEnd;
  }

  public void setId(Long id) {
    this.id = id;
  }


}
