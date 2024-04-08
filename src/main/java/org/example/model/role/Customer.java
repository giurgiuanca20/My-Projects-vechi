package org.example.model.role;

public class Customer {
  private Long id;
  private String username;
  private String password;
  private String name;
  private String address;
  private String CNP;
  private String card;
  private String fidelity;
  private String points_fidelity;

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setCNP(String CNP) {
    this.CNP = CNP;
  }

  public void setCard(String card) {
    this.card = card;
  }

  public void setFidelity(String fidelity) {
    this.fidelity = fidelity;
  }

  public void setPoints_fidelity(String points_fidelity) {
    this.points_fidelity = points_fidelity;
  }

  public String getAddress() {
    return address;
  }

  public String getCNP() {
    return CNP;
  }

  public String getCard() {
    return card;
  }

  public String getFidelity() {
    return fidelity;
  }

  public String getPoints_fidelity() {
    return points_fidelity;
  }
}
