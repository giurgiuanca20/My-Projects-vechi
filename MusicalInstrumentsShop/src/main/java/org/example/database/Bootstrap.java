package org.example.database;

import org.example.model.cart.EProduct;
import org.example.model.security.ERole;
import org.example.repository.cart.ProductRepository;
import org.example.repository.cart.ProductRepositorySQL;
import org.example.repository.security.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.database.Constants.SCHEMAS.SCHEMAS;

public class Bootstrap {
  private RoleRepository roleRepository;
  private UserRepository userRepository;
  private ProductRepository productRepository;

  public void bootstrap() throws SQLException {
    dropAll();
    createTables();
    createUserData();
  }

  private void dropAll() throws SQLException {
    for (String schema : Constants.SCHEMAS.SCHEMAS) {
      System.out.println("Dropping all tables in schema: " + schema);

      try (Connection connection = new JDBConnectionWrapper(schema).getConnection();
           Statement statement = connection.createStatement()) {

        for (String table : Constants.TABLES.ORDERED_TABLES_FOR_DROP) {
          String dropStatement = "DROP TABLE IF EXISTS `" + table + "`;";
          statement.execute(dropStatement);
        }
      }
    }
  }


  private void createTables() throws SQLException {
    SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

    for (String schema : SCHEMAS) {
      System.out.println("Bootstrapping " + schema + " schema");

      JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
      Connection connection = connectionWrapper.getConnection();

      Statement statement = connection.createStatement();

      for (String table : Constants.TABLES.ORDERED_TABLES_FOR_CREATION) {
        String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
        statement.execute(createTableSQL);
      }
    }
  }

  private void createUserData() {
    for (String schema : SCHEMAS) {
      System.out.println("Bootstrapping user data for " + schema);

      createRoles(schema);
      createUsers(schema);
      createProducts(schema);
    }
  }

  private void createRoles(String schema) {
    JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
    roleRepository = new RoleRepositorySQL(connectionWrapper.getConnection());
    for (ERole role : ERole.values()) {
      roleRepository.create(role);
    }
  }
  private void createProducts(String schema) {
    JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
    productRepository = new ProductRepositorySQL(connectionWrapper.getConnection());
    for (EProduct product : EProduct.values()) {
      productRepository.create(product);
    }
  }

  private void createUsers(String schema) {
    JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
    roleRepository = new RoleRepositorySQL(connectionWrapper.getConnection());
    userRepository = new UserRepositorySQL(connectionWrapper.getConnection(), roleRepository);

  }
}
