package org.example.view;

import org.example.model.cart.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CartView extends JFrame {
  private JButton btnCheckout;
  private JLabel lTitle, lTotalPrice, lCheckoutCard, lFidelity,lUseFidelity;
  private JTextField tfCard;
  private JTextField tfUseFidelity;
  private JTable instrumentTable;
  private DefaultTableModel tableModel;

  private final Color navy = new Color(0, 0, 90);
  private final Color purple = new Color(100, 19, 80);
  private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);

  public CartView() {
    setSize(900, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    initializeFields();
    initializeTable();

    setLayout(new BorderLayout());
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    topPanel.add(lTitle);
    topPanel.add(lFidelity);

    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(new JScrollPane(instrumentTable), BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.add(lTotalPrice);
    bottomPanel.add(lCheckoutCard);
    bottomPanel.add(tfCard);
    bottomPanel.add(btnCheckout);
    bottomPanel.add(lUseFidelity);
    bottomPanel.add(tfUseFidelity);


    add(topPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  private void initializeFields() {
    lTitle = createLabel("Cart", bigFont, navy);
    lTotalPrice = createLabel("Total Price: ", bigFont, navy);
    lCheckoutCard = createLabel("Add card: ", bigFont, navy);
    lFidelity = createLabel(" ", bigFont, navy);
    lUseFidelity = createLabel("Use your fidelity points ", bigFont, navy);
    lUseFidelity.setVisible(false);

    btnCheckout = createButton(null, " Checkout ", purple);
    tfCard = new JTextField(15);
    tfUseFidelity = new JTextField(15);
    tfUseFidelity.setVisible(false);
    tfUseFidelity.setText("0");
    lCheckoutCard.setVisible(false);
    tfCard.setVisible(false);
  }

  private void initializeTable() {
    String[] columnNames = {"Instrument", "Price"};
    tableModel = new DefaultTableModel(columnNames, 0);
    instrumentTable = new JTable(tableModel);
  }

  public void updateInstrumentList(List<Product> cartList) {
    tableModel.setRowCount(0);
    for (Product product : cartList) {
      tableModel.addRow(new Object[]{product.getName(), product.getPrice()});
    }
    calculateTotalPrice();
  }
public void useFidelity(){
    lUseFidelity.setVisible(true);
    tfUseFidelity.setVisible(true);
}
  public void setVisibleForGuest(boolean visible) {
    lCheckoutCard.setVisible(visible);
    tfCard.setVisible(visible);
  }

  public void setlTotalPrice(int totalPrice) {
    lTotalPrice.setText("Total Price: " + totalPrice);
  }
  public void setUseFidelityListener(ActionListener listener) {
    tfUseFidelity.addActionListener(listener);
  }
  public int getFidelityPoints() {
    String labelText = lFidelity.getText();
    String pointsText = labelText.replace("        Fidelity points: ", "");
    int fidelityPoints = Integer.parseInt(pointsText);
    return fidelityPoints;
  }


  public int getTfUseFidelity() {
    return Integer.parseInt(tfUseFidelity.getText());
  }

  public void setCheckoutButtonListener(ActionListener checkoutButtonListener) {
    btnCheckout.addActionListener(checkoutButtonListener);
  }
  public void setFidelityPoints(int fidelityPoints) {
    lFidelity.setText("        Fidelity points: "+fidelityPoints);
  }
  public void calculateTotalPrice() {
    int totalPrice = 0;
    for (int row = 0; row < tableModel.getRowCount(); row++) {
      totalPrice += (int) tableModel.getValueAt(row, 1);
    }
    lTotalPrice.setText("Total Price: " + totalPrice);
  }

  public int calculateTotalPriceInt() {
    int totalPrice = 0;
    for (int row = 0; row < tableModel.getRowCount(); row++) {
      totalPrice += (int) tableModel.getValueAt(row, 1);
    }
    lTotalPrice.setText("Total Price: " + totalPrice);
    return totalPrice;
  }












  public int getTotalPrice() {
    int totalPrice = 0;
    for (int row = 0; row < tableModel.getRowCount(); row++) {
      totalPrice += (int) tableModel.getValueAt(row, 1);
    }
    return totalPrice;
  }

  public int getNrProducts() {
    return tableModel.getRowCount();
  }

  private JLabel createLabel(String text, Font font, Color color) {
    JLabel label = new JLabel(text);
    label.setFont(font);
    label.setForeground(color);
    return label;
  }

  private JButton createButton(ImageIcon icon, String text, Color color) {
    JButton button = new JButton(text);
    button.setIcon(icon);
    button.setBackground(color);
    button.setForeground(Color.white);
    button.setFont(bigFont);
    button.setBorder(null);
    return button;
  }
  public void setVisible(boolean aux, List<Product> cartList,int fidelityPoints) {
    if(aux)
      this.setVisible(true);
    updateInstrumentList(cartList);
    setFidelityPoints(fidelityPoints);
  }
}
