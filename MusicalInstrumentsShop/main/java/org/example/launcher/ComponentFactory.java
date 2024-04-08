package org.example.launcher;

import org.example.controller.*;
import org.example.database.DatabaseConnectionFactory;
import org.example.database.SupportedDatabase;
import org.example.repository.role.CashierRepository;
import org.example.repository.role.CashierRepositorySQL;
import org.example.repository.role.CustomerRepository;
import org.example.repository.role.CustomerRepositorySQL;
import org.example.repository.security.*;
import org.example.repository.cart.*;
import org.example.service.*;
import org.example.view.*;

import java.sql.Connection;

public class ComponentFactory {

  private final LoginView loginView;
  private final SearchView searchView;
  private final CartView cartView;
  private final CashierView cashierView;
  private final AdministratorView administratorView;

  private final OrderView orderView;
  private final ReportView reportView;

  private final LoginController loginController;
  private final SearchController searchController;
  private final CashierController cashierController;
  private final AdministratorController administratorController;
  private final CartController cartController;
  private final OrderController orderController;
  private final ReportController reportController;

  private final SecurityService securityService;
  private final OrderService orderService;
  private final CartService cartService;
  private final CustomerService customerService;
  private final CashierService cashierService;
  private final ReportService reportService;

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final CashierRepository cashierRepository;
  private final OrderRepository orderRepository;

  private final CustomerRepository customerRepository;
  private final CartRepository cartRepository;
  private final ReportRepository reportRepository;
  private final ProductRepository productRepository;

  public ComponentFactory(SupportedDatabase supportedDatabase, Boolean componentsForTest) {
    final Connection connection = DatabaseConnectionFactory.getConnectionWrapper(supportedDatabase, componentsForTest).getConnection();
    this.roleRepository = new RoleRepositorySQL(connection);
    this.userRepository = new UserRepositorySQL(connection, roleRepository);
    this.productRepository=new ProductRepositorySQL(connection);
    this.cartRepository = new CartRepositorySQL(connection,productRepository,userRepository);
    this.cashierRepository = new CashierRepositorySQL(connection,userRepository);
    this.customerRepository = new CustomerRepositorySQL(connection,userRepository,cartRepository);

    this.orderRepository=new OrderRepositorySQL(connection,cartRepository,cashierRepository);
    this.reportRepository=new ReportRepositorySQL(connection);

    this.securityService = new SecurityService(userRepository, roleRepository);
    this.cartService = new CartService(cartRepository, securityService,customerRepository);
    this.cashierService = new CashierService(cashierRepository, securityService);
    this.customerService = new CustomerService(customerRepository, securityService);
    this.orderService=new OrderService(orderRepository);
    this.reportService=new ReportService( reportRepository);


    this.loginView = new LoginView();
    this.searchView = new SearchView();
    this.cartView = new CartView();
    this.cashierView=new CashierView();
    this.administratorView=new AdministratorView();
    this.orderView=new OrderView();
    this.reportView=new ReportView();

    this.loginController = new LoginController(loginView, searchView,cashierView,administratorView,securityService);
    this.searchController = new SearchController(searchView, cartView,cartService,securityService);
    this.cashierController = new CashierController(loginView, cashierView,securityService, customerService,orderService, orderView);
    this.administratorController = new AdministratorController(loginView, administratorView,cashierService,reportView);
    this.cartController=new CartController(cartView,securityService,orderService,cartService,customerService);
    this.orderController=new OrderController(orderView,cashierView,securityService,orderService);
    this.reportController=new ReportController(reportView,reportService);

    ShutdownHook shutdownHook = new ShutdownHook(cartService);
    Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
  }

  public LoginView getLoginView() {
    return loginView;
  }
  public SearchView getSearchView() {
    return searchView;
  }

  public LoginController getLoginController() {
    return loginController;
  }

  public SecurityService getSecurityService() {
    return securityService;
  }

  public RoleRepository getRoleRepository() {
    return roleRepository;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

}
