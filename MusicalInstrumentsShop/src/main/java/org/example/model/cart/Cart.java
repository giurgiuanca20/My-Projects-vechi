package org.example.model.cart;

import java.util.List;

public class Cart {
    private Long id;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private List<EProduct> products;
    private int totalPrice=0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProducts(List<EProduct> products) {
        this.products = products;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    public List<EProduct> getProducts() {
        return products;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
