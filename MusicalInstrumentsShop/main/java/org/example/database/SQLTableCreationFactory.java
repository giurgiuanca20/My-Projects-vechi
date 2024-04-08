package org.example.database;

import static org.example.database.Constants.TABLES.*;

public class SQLTableCreationFactory {

  public String getCreateSQLForTable(String table) {
    return switch (table) {
      case BOOK -> "CREATE TABLE IF NOT EXISTS book (" +
              "  id int(11) NOT NULL AUTO_INCREMENT," +
              "  author varchar(500) NOT NULL," +
              "  title varchar(500) NOT NULL," +
              "  publishedDate datetime DEFAULT NULL," +
              "  PRIMARY KEY (id)," +
              "  UNIQUE KEY id_UNIQUE (id)" +
              ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
      case USER -> "CREATE TABLE IF NOT EXISTS user (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  username VARCHAR(200) NOT NULL," +
              "  password VARCHAR(64) NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE (id ASC)," +
              "  UNIQUE INDEX username_UNIQUE (username ASC));";
      case ROLE -> "  CREATE TABLE IF NOT EXISTS role (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  role VARCHAR(100) NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE (id ASC)," +
              "  UNIQUE INDEX role_UNIQUE (role ASC));";
      case USER_ROLE -> "\tCREATE TABLE IF NOT EXISTS user_role (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  user_id INT NOT NULL," +
              "  role_id INT NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  UNIQUE INDEX id_UNIQUE (id ASC)," +
              "  INDEX user_id_idx (user_id ASC)," +
              "  INDEX role_id_idx (role_id ASC)," +
              "  CONSTRAINT user_fkid" +
              "    FOREIGN KEY (user_id)" +
              "    REFERENCES user (id)" +
              "    ON DELETE CASCADE" +
              "    ON UPDATE CASCADE," +
              "  CONSTRAINT role_fkid" +
              "    FOREIGN KEY (role_id)" +
              "    REFERENCES role (id)" +
              "    ON DELETE CASCADE" +
              "    ON UPDATE CASCADE);";
      case CASHIER -> "CREATE TABLE IF NOT EXISTS cashier (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  name VARCHAR(200) NOT NULL," +
              "  phone VARCHAR(20) NOT NULL," +
              "  PRIMARY KEY (id)" +
              ")";
      case CASHIER_ORDER -> "CREATE TABLE IF NOT EXISTS cashier_order (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  order_id INT NOT NULL," +
              "  cashier_id INT NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  FOREIGN KEY (order_id) REFERENCES orders (id)," +
              "  FOREIGN KEY (cashier_id) REFERENCES cashier (id)" +
              ")";
      case ORDERS -> "CREATE TABLE IF NOT EXISTS orders (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  `date` DATE ," +
              "  product_number INT NOT NULL," +
              "  money INT NOT NULL," +
              "  PRIMARY KEY (id)" +
              ")";
      case ORDER_USER -> "CREATE TABLE IF NOT EXISTS order_user (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  order_id INT NOT NULL," +
              "  user_id INT NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  FOREIGN KEY (order_id) REFERENCES orders (id)," +
              "  FOREIGN KEY (user_id) REFERENCES user (id)" +
              ")";
      case CUSTOMER -> "CREATE TABLE IF NOT EXISTS customer (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  name VARCHAR(200) NOT NULL," +
              "  cnp VARCHAR(20) NOT NULL," +
              "  card VARCHAR(20) NOT NULL," +
              "  address VARCHAR(500) NOT NULL," +
              "  fidelity VARCHAR(3) NOT NULL DEFAULT 'no'," +
              "  points_fidelity INT NOT NULL DEFAULT 0," +
              "  PRIMARY KEY (id)" +
              ")";
      case CUSTOMER_USER -> "CREATE TABLE IF NOT EXISTS customer_user (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  customer_id INT NOT NULL," +
              "  user_id INT NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  FOREIGN KEY (customer_id) REFERENCES customer (id)," +
              "  FOREIGN KEY (user_id) REFERENCES `user` (id)" +
              ")";
      case USER_CART -> "CREATE TABLE IF NOT EXISTS user_cart (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  user_id INT NOT NULL," +
              "  cart_id INT NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  FOREIGN KEY (user_id) REFERENCES `user` (id)," +
              "  FOREIGN KEY (cart_id) REFERENCES cart (id)" +
              ")";
      case CART -> "CREATE TABLE IF NOT EXISTS cart (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  checkout VARCHAR(3) NOT NULL DEFAULT 'no'," +
              "  PRIMARY KEY (id)" +
              ")";
      case PRODUCT -> "CREATE TABLE IF NOT EXISTS product (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  name VARCHAR(200) NOT NULL," +
              "  price INT NOT NULL," +
              "  PRIMARY KEY (id)" +
              ")";
      case PRODUCT_CART -> "CREATE TABLE IF NOT EXISTS product_cart (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  product_id INT NOT NULL," +
              "  cart_id INT NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  FOREIGN KEY (product_id) REFERENCES product (id)," +
              "  FOREIGN KEY (cart_id) REFERENCES cart (id)" +
              ")";
      case USER_CASHIER -> "CREATE TABLE IF NOT EXISTS user_cashier (" +
              "  id INT NOT NULL AUTO_INCREMENT," +
              "  user_id INT NOT NULL," +
              "  cashier_id INT NOT NULL," +
              "  PRIMARY KEY (id)," +
              "  FOREIGN KEY (user_id) REFERENCES user (id)," +
              "  FOREIGN KEY (cashier_id) REFERENCES cashier (id)" +
              ")";
      default -> "";
    };
  }

}
