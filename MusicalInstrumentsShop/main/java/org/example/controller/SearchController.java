package org.example.controller;

import org.example.model.cart.EProduct;
import org.example.model.cart.Product;
import org.example.model.security.ERole;
import org.example.service.CartService;
import org.example.service.SecurityService;
import org.example.view.CartView;
import org.example.view.SearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchController {
    private final SearchView searchView;
    private final CartView cartView;
    private final CartService cartService;

    private List<EProduct> cartList=new ArrayList<>();
    private List<Product> productList=new ArrayList<>();
    private final SecurityService securityService;
    public SearchController(SearchView searchView, CartView cartView, CartService cartService,SecurityService securityService) {
        this.cartView = cartView;
        this.searchView=searchView;
        this.cartService=cartService;
        this.securityService=securityService;
        this.searchView.setCartButtonListener(new SearchController.CartButtonListener());
        this.searchView.setGuitarButtonListener(new SearchController.GuitarButtonListener());
        this.searchView.setDrumsButtonListener(new SearchController.DrumsButtonListener());
        this.searchView.setPianoButtonListener(new SearchController.PianoButtonListener());
        this.searchView.setViolinsButtonListener(new SearchController.ViolinsButtonListener());
        this.searchView.setCellosButtonListener(new SearchController.CellosButtonListener());
        this.searchView.setHarmonicasButtonListener(new SearchController.HarmonicasButtonListener());
        this.searchView.setSaxophonesButtonListener(new SearchController.SaxophonesButtonListener());
        this.searchView.setTrumpetsButtonListener(new SearchController.TrumpetsButtonListener());
    }

    private class CartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           searchView.setVisible(false);
           int fidelityPoints=0;
           boolean guest=true;
            try {
                productList=cartService.populateCart();
                fidelityPoints=cartService.getFidelityPoints();
                if(securityService.getCurrentUserRole().equals(ERole.CUSTOMER)){
                    guest=false;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

           cartView.setVisible(true,productList,fidelityPoints);
            if(!guest)
            {
                cartView.useFidelity();
            }
        }
    }
    private class GuitarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(Objects.equals(cartService.getCheckout(), "no")) {
                    EProduct product = EProduct.GUITARS;
                    cartList.add(product);
                    try {
                        cartService.addProductToCart(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    private class DrumsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (Objects.equals(cartService.getCheckout(), "no")) {
                    EProduct product = EProduct.DRUMS;
                    cartList.add(product);
                    try {
                        cartService.addProductToCart(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class PianoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (Objects.equals(cartService.getCheckout(), "no")) {
                    EProduct product = EProduct.PIANO;
                    cartList.add(product);
                    try {
                        cartService.addProductToCart(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class ViolinsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (Objects.equals(cartService.getCheckout(), "no")) {
                    EProduct product = EProduct.VIOLINS;
                    cartList.add(product);
                    try {
                        cartService.addProductToCart(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class CellosButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (Objects.equals(cartService.getCheckout(), "no")) {
                    EProduct product = EProduct.CELLOS;
                    cartList.add(product);
                    try {
                        cartService.addProductToCart(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class HarmonicasButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (Objects.equals(cartService.getCheckout(), "no")) {
                    EProduct product = EProduct.HARMONICAS;
                    cartList.add(product);
                    try {
                        cartService.addProductToCart(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private class SaxophonesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (Objects.equals(cartService.getCheckout(), "no")) {
                    EProduct product = EProduct.SAXOPHONES;
                    cartList.add(product);
                    try {
                        cartService.addProductToCart(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class TrumpetsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (Objects.equals(cartService.getCheckout(), "no")) {
                    EProduct product = EProduct.TRUMPETS;
                    cartList.add(product);
                    try {
                        cartService.addProductToCart(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
