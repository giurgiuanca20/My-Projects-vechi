package org.example.controller;

import org.example.service.CartService;

import java.sql.SQLException;

public class ShutdownHook implements Runnable {
    private CartService cartService;

    public ShutdownHook(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void run() {
        try {
            cartService.deleteFlotingCart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
