package org.example.repository.cart;

import org.example.model.cart.EProduct;
import org.example.model.cart.Product;
import org.example.repository.security.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartRepositorySQL implements CartRepository {
    private final Connection connection;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartRepositorySQL(Connection connection,ProductRepository productRepository,UserRepository userRepository){
        this.connection = connection;
        this.productRepository=productRepository;
        this.userRepository=userRepository;
    }

    @Override
    public void addProductToCart(Long userId, EProduct product) throws SQLException {
        try {
            Long cartId = getCartIdByUserId(userId);
            Long productId = productRepository.getProductIdByName(product.getName());

            PreparedStatement insertProductCartStatement = connection.prepareStatement(
                    "INSERT INTO product_cart (cart_id, product_id) VALUES (?, ?)");
            insertProductCartStatement.setLong(1, cartId);
            insertProductCartStatement.setLong(2, productId);
            insertProductCartStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to add product to cart.", e);
        }
    }

    @Override
    public List<Product> getCart(Long userId) throws SQLException {
        List<Product> productsInCart = new ArrayList<>();
        try {
            Long cartId = getCartIdByUserId(userId);

            PreparedStatement productStatement = connection.prepareStatement(
                    "SELECT p.name, p.price FROM product p " +
                            "JOIN product_cart pc ON p.id = pc.product_id " +
                            "WHERE pc.cart_id = ?");
            productStatement.setLong(1, cartId);
            ResultSet productResultSet = productStatement.executeQuery();
            while (productResultSet.next()) {
                String name = productResultSet.getString("name");
                int price = productResultSet.getInt("price");
                Product product = new Product(name, price);
                productsInCart.add(product);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to fetch cart products.", e);
        }
        return productsInCart;
    }







    @Override
    public String getCheckout(Long userId) throws SQLException {
        String checkout = null;
        try {
            Long cartId = getCartIdByUserId(userId);

            PreparedStatement checkoutStatement = connection.prepareStatement(
                    "SELECT checkout FROM cart WHERE id = ?");
            checkoutStatement.setLong(1, cartId);
            ResultSet checkoutResultSet = checkoutStatement.executeQuery();
            if (checkoutResultSet.next()) {
                checkout = checkoutResultSet.getString("checkout");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get checkout status.", e);
        }
        return checkout;
    }


    @Override
    public void setCheckoutYes(Long userId) throws SQLException {
        try {
            PreparedStatement checkoutOrderStatement = connection.prepareStatement(
                    "UPDATE cart SET checkout = ? WHERE id = ?"); {
                Long cartId=getCartIdByUserId(userId);
                checkoutOrderStatement.setString(1, "yes");
                checkoutOrderStatement.setLong(2, cartId);
                checkoutOrderStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to set checkout yes.", e);
        }

    }
    @Override
    public void setCheckoutNo(Long idCart) throws SQLException {
        PreparedStatement updateCartStatement = connection.prepareStatement(
                "UPDATE cart SET checkout = ? WHERE id = ?"
        );
        updateCartStatement.setString(1, "no");
        updateCartStatement.setLong(2, idCart);
        updateCartStatement.executeUpdate();
    }
    @Override
    public Long getCartIdByUserId(Long userId) throws SQLException {
        Long idCart=null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT cart_id FROM user_cart WHERE user_id = ?")) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idCart = resultSet.getLong("cart_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }

        return idCart;
    }

    @Override
    public void deleteFromCartId(Long idUser)throws SQLException {
        try (PreparedStatement deleteProductCartStatement = connection.prepareStatement(
                "DELETE FROM product_cart WHERE cart_id  = ?")) {
            Long cartId=getCartIdByUserId(idUser);
            deleteProductCartStatement.setLong(1, cartId);
            deleteProductCartStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }


    @Override
    public Long createCart() throws SQLException {
        PreparedStatement insertCartStatement = connection.prepareStatement(
                "INSERT INTO cart (checkout) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS
        );
        insertCartStatement.setString(1, "no");
        insertCartStatement.executeUpdate();

        ResultSet cartKeys = insertCartStatement.getGeneratedKeys();
        cartKeys.next();
        long cartId = cartKeys.getLong(1);
        return cartId;
    }
@Override
    public void insertUserCart(Long userId,Long cartId)throws SQLException{
    PreparedStatement insertUserCartStatement = connection.prepareStatement(
            "INSERT INTO user_cart (user_id,cart_id) VALUES (?,?)",
            Statement.RETURN_GENERATED_KEYS
    );
    insertUserCartStatement.setLong(1, userId);
    insertUserCartStatement.setLong(2, cartId);
    insertUserCartStatement.executeUpdate();

}
    @Override
    public void removeAll() throws SQLException {
        try (PreparedStatement deleteAllProductsStatement = connection.prepareStatement(
                "DELETE FROM product_cart")) {
            deleteAllProductsStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to remove all products from cart.", e);
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> allProducts = new ArrayList<>();
        try {
            PreparedStatement findAllProductsStatement = connection.prepareStatement(
                    "SELECT p.name, p.price FROM product p " +
                            "JOIN product_cart pc ON p.id = pc.product_id");
            ResultSet productResultSet = findAllProductsStatement.executeQuery();
            while (productResultSet.next()) {
                String name = productResultSet.getString("name");
                int price = productResultSet.getInt("price");
                Product product = new Product(name, price);
                allProducts.add(product);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to fetch all products from cart.", e);
        }
        return allProducts;
    }
    @Override
    public void deleteFromUserCartById(Long userId)throws SQLException {
        try {
            PreparedStatement deleteFromUserCartStatement = connection.prepareStatement(
                    "DELETE FROM user_cart WHERE user_id = ?");
            deleteFromUserCartStatement.setLong(1, userId);
            deleteFromUserCartStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteCart(Long cartId)throws SQLException {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM cart WHERE id = ?");
            deleteStatement.setLong(1, cartId);
            deleteStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteFlotingCart(List<Long> guestsIds) throws SQLException {
        try {
            for (Long userId : guestsIds) {
                if(getCheckout(userId).equals("no")){
                    Long cartId = getCartIdByUserId(userId);
                    deleteFromCartId(cartId);

                    deleteFromUserCartById(userId);

                    deleteCart(cartId);

                    userRepository.deleteUserAndRolesById(userId);
                }

            }
        } catch (SQLException e) {
            throw new SQLException("Failed to delete floating carts.", e);
        }
    }


}

