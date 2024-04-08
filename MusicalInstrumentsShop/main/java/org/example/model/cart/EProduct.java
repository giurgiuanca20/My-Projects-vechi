package org.example.model.cart;

public enum EProduct {
  GUITARS("Guitars", 500),
  DRUMS("Drums", 800),
  PIANO("Piano", 2100),
  VIOLINS("Violins", 1000),
  CELLOS("Cellos", 1500),
  HARMONICAS("Harmonicas", 20),
  SAXOPHONES("Saxophones", 700),
  TRUMPETS("Trumpets", 600);

  private final String name;
  private final int price;

  EProduct(String name, int price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }
}
