package org.example.view;

import org.example.model.cart.EProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchView extends JFrame {
  private final Color navy = new Color(0, 0, 90);
  private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);
  private JButton btnCart;
  private JButton btnGuitars, btnDrums, btnPiano, btnViolins, btnCellos, btnHarmonicas, btnSaxophones, btnTrumpets;

  public SearchView() {
    setTitle("Music Store");
    setSize(600, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    initComponents();
    addComponents();
  }

  private void initComponents() {
    btnCart = createButton("Cart", navy);
    btnGuitars = createButton("Add Guitars", navy);
    btnDrums = createButton("Add Drums", navy);
    btnPiano = createButton("Add Piano", navy);
    btnViolins = createButton("Add Violins", navy);
    btnCellos = createButton("Add Cellos", navy);
    btnHarmonicas = createButton("Add Harmonicas", navy);
    btnSaxophones = createButton("Add Saxophones", navy);
    btnTrumpets = createButton("Add Trumpets", navy);
  }

  private void addComponents() {
    setLayout(new GridLayout(9, 2, 10, 10));
    addProductComponent(EProduct.GUITARS, btnGuitars);
    addProductComponent(EProduct.DRUMS, btnDrums);
    addProductComponent(EProduct.PIANO, btnPiano);
    addProductComponent(EProduct.VIOLINS, btnViolins);
    addProductComponent(EProduct.CELLOS, btnCellos);
    addProductComponent(EProduct.HARMONICAS, btnHarmonicas);
    addProductComponent(EProduct.SAXOPHONES, btnSaxophones);
    addProductComponent(EProduct.TRUMPETS, btnTrumpets);
    add(btnCart);
  }

  private JButton createButton(String text, Color color) {
    JButton button = new JButton(text);
    button.setBackground(color);
    button.setForeground(Color.WHITE);
    button.setFont(bigFont);
    button.setBorderPainted(false);
    return button;
  }

  private void addProductComponent(EProduct product, JButton button) {
    JLabel label = new JLabel(product.getName() + "     " + product.getPrice() + " Ron", JLabel.CENTER);
    label.setFont(bigFont);
    label.setForeground(navy);
    add(label);
    add(button);
  }

  public void setCartButtonListener(ActionListener cartButtonListener) {
    btnCart.addActionListener(cartButtonListener);
  }

  public void setGuitarButtonListener(ActionListener guitarButtonListener) {
    btnGuitars.addActionListener(guitarButtonListener);
  }

  public void setDrumsButtonListener(ActionListener drumsButtonListener) {
    btnDrums.addActionListener(drumsButtonListener);
  }

  public void setPianoButtonListener(ActionListener pianoButtonListener) {
    btnPiano.addActionListener(pianoButtonListener);
  }

  public void setViolinsButtonListener(ActionListener violinsButtonListener) {
    btnViolins.addActionListener(violinsButtonListener);
  }

  public void setCellosButtonListener(ActionListener cellosButtonListener) {
    btnCellos.addActionListener(cellosButtonListener);
  }

  public void setHarmonicasButtonListener(ActionListener harmonicasButtonListener) {
    btnHarmonicas.addActionListener(harmonicasButtonListener);
  }

  public void setSaxophonesButtonListener(ActionListener saxophonesButtonListener) {
    btnSaxophones.addActionListener(saxophonesButtonListener);
  }

  public void setTrumpetsButtonListener(ActionListener trumpetsButtonListener) {
    btnTrumpets.addActionListener(trumpetsButtonListener);
  }

}
