package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
  private JTextField tfUsername;
  private JPasswordField tfPassword;
  private JButton btnLogin;
  private JButton btnRegister;
  private JButton btnGuest;
  private JLabel lblUsername;
  private JLabel lblPassword;

  private final Color navy = new Color(0, 0, 90);
  private final Color purple = new Color(100, 19, 80);
  private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);

  public LoginView() {
    setTitle("Login");
    setSize(400, 300);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    initComponents();
    addComponents();
  }

  private void initComponents() {
    lblUsername = createLabel("Username:", bigFont, navy);
    lblPassword = createLabel("Password:", bigFont, navy);

    tfUsername = new JTextField(15);
    tfPassword = new JPasswordField(15);

    tfUsername.setText("stefi@gmail.com");
    tfPassword.setText("stefi31@");



    btnLogin = createButton(null, "Login", purple);
    btnRegister = createButton(null, "Register", purple);
    btnGuest = createButton(null, "Guest", purple);
  }

  private void addComponents() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    mainPanel.add(lblUsername);
    mainPanel.add(tfUsername);
    mainPanel.add(lblPassword);
    mainPanel.add(tfPassword);
    mainPanel.add(btnLogin);
    mainPanel.add(btnGuest);
    mainPanel.add(btnRegister);

    add(mainPanel, BorderLayout.CENTER);
  }

  public String getUsername() {
    return tfUsername.getText();
  }

  public String getPassword() {
    return new String(tfPassword.getPassword());
  }

  public void setLoginButtonListener(ActionListener loginButtonListener) {
    btnLogin.addActionListener(loginButtonListener);
  }

  public void setRegisterButtonListener(ActionListener registerButtonListener) {
    btnRegister.addActionListener(registerButtonListener);
  }

  public void setGuestButtonListener(ActionListener guestButtonListener) {
    btnGuest.addActionListener(guestButtonListener);
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
}
