package org.example.model.cart;

import org.example.model.cart.EProduct;

import java.sql.Date;
import java.util.List;

public class Order {
    private Long id;
    private Long userId;
    private Date date;
    private int nrProducts;
    private int totalPrice=0;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNrProducts(int nrProducts) {
        this.nrProducts = nrProducts;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public int getNrProducts() {
        return nrProducts;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
