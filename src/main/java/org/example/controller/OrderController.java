package org.example.controller;

import org.example.model.security.ERole;
import org.example.model.security.User;
import org.example.model.validation.Notification;
import org.example.service.OrderService;
import org.example.service.SecurityService;
import org.example.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.UUID;

public class OrderController {


  private final OrderView orderView;
  private final CashierView cashierView;
  private final SecurityService securityService;
  private final OrderService orderService;


  public OrderController(OrderView orderView, CashierView cashierView, SecurityService securityService, OrderService orderService) {
    this.orderView = orderView;
    this.cashierView=cashierView;

    this.securityService = securityService;
    this.orderService=orderService;

    this.orderView.setProcessButtonListener(new ProcessButtonListener());
  }

  private class ProcessButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      Long idOrder = orderView.getIdOrder();
      Long idUser = securityService.getCurrentUserId();
      orderService.processOrder(idOrder, idUser);


    }

  }
}
