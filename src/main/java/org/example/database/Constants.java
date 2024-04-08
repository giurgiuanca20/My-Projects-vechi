package org.example.database;

public class Constants {

  public static class SCHEMAS {
    public static final String TEST = "sd-basics-test";
    public static final String PRODUCTION = "sd-basics";

    public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
  }

  public static class TABLES {
    public static final String BOOK = "book";
    public static final String USER = "user";
    public static final String ROLE = "role";
    public static final String USER_ROLE = "user_role";
    public static final String CASHIER = "cashier";
    public static final String ORDERS = "orders";
    public static final String CUSTOMER = "customer";
    public static final String CART = "cart";
    public static final String PRODUCT = "product";
    public static final String CASHIER_ORDER = "cashier_order";
    public static final String ORDER_USER = "order_user";
    public static final String CUSTOMER_USER = "customer_user";
    public static final String USER_CART = "user_cart";
    public static final String PRODUCT_CART = "product_cart";

    public static final String USER_CASHIER = "user_cashier";


    public static final String[] ORDERED_TABLES_FOR_CREATION = {BOOK, USER, ROLE, USER_ROLE, CART,
            CUSTOMER,PRODUCT, ORDERS,CASHIER,CASHIER_ORDER,ORDER_USER,CUSTOMER_USER,PRODUCT_CART,USER_CASHIER,USER_CART};
    public static final String[] ORDERED_TABLES_FOR_DROP = {CASHIER_ORDER,ORDER_USER,CUSTOMER_USER,PRODUCT_CART,USER_CASHIER,USER_CART,USER_ROLE,BOOK, ROLE, CART,
            CUSTOMER,PRODUCT, ORDERS,CASHIER,USER};
  }

}
