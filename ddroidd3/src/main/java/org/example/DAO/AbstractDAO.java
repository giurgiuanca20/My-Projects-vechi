package org.example.DAO;


import org.example.database.Connect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }
    // Helper method to create a SELECT query for the given field
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        if (field != null)
            sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }


    private String createInsertQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i].getName());
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(") VALUES (");

        for (int i = 0; i < fields.length; i++) {
            sb.append("?");
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");

        return sb.toString();
    }

    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (!field.getName().equalsIgnoreCase("id")) {
                sb.append(field.getName());
                sb.append(" = ?");
                if (i < fields.length - 1) {
                    sb.append(", ");
                }
            }
        }

        sb.append(" WHERE id = ?");

        return sb.toString();
    }

    private String createDeleteQuery(String coloana) {
        String sb = "DELETE FROM " +
                type.getSimpleName() +
                " WHERE " + coloana + " = ?";

        return sb;
    }

    // Find all records of the specified entity
    public List<T> findAll() {
        List<T> resultList = new ArrayList<>();

        Connection connection = Connect.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(null);
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return resultList = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            Connect.close(resultSet);
            Connect.close(statement);
            Connect.close(connection);
        }
        return null;
    }

    // Find a record of the specified entity by its ID
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            Connect.close(resultSet);
            Connect.close(statement);
            Connect.close(connection);
        }
        return null;
    }

    // Create objects from the ResultSet
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Insert a record of the specified entity
    public T insert(T t) {
        int lastIndex = getLastIndex();
        setId(t, lastIndex + 1);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery(t);

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setInsertParameters(statement, t);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                setId(t, id);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            Connect.close(resultSet);
            Connect.close(statement);
            Connect.close(connection);
        }

        return t;
    }
    // Set parameters for INSERT query
    private void setInsertParameters(PreparedStatement statement, T t) throws SQLException {
        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                statement.setObject(i + 1, value);
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.WARNING, "Error setting insert parameters: " + e.getMessage());
            }
        }
    }
    // Set the ID for an entity
    private void setId(T t, int id) {
        try {
            Field field = type.getDeclaredField("id");
            field.setAccessible(true);
            field.set(t, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Error setting ID: " + e.getMessage());
        }
    }

    // Update a record of the specified entity
    public T update(T t, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            setUpdateParameters(statement, t, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            Connect.close(statement);
            Connect.close(connection);
        }

        return t;
    }

    // Delete a record by specifying a column and its value
    public void delete(String nume, String coloana) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery(coloana);

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, nume);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            Connect.close(statement);
            Connect.close(connection);
        }
    }


    private void setUpdateParameters(PreparedStatement statement, T t, int id) throws SQLException {
        Field[] fields = type.getDeclaredFields();
        int parameterIndex = 1;
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("id")) {
                field.setAccessible(true);
                try {
                    Object value = field.get(t);
                    statement.setObject(parameterIndex, value);
                    parameterIndex++;
                } catch (IllegalAccessException e) {
                    LOGGER.log(Level.WARNING, "Error setting update parameters: " + e.getMessage());
                }
            }
        }
        // Set the ID parameter
        statement.setObject(parameterIndex, id);
    }

    // Get the last index of records
    public int getLastIndex() {
        int lastIndex = 0;
        String query = "SELECT MAX(id) FROM " + type.getSimpleName();

        try (Connection connection = Connect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                lastIndex = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting last index: " + e.getMessage());
        }

        return lastIndex;
    }


    public ArrayList<String> getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>();

        try (ResultSet resultSet = Connect.getConnection().getMetaData().getColumns(null, null, type.getSimpleName(), null)) {
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columnNames.add(columnName);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting column names: " + e.getMessage());
        }

        return columnNames;
    }
    // Find the ID by name for a specific table
    public int findIdByName(String name, String tabel) {
        int i = -1;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id FROM " + tabel + " WHERE name=?";

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                i = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving id findNameByID: " + "DAO:findNameByID " + e.getMessage());
        } finally {
            Connect.close(resultSet);
            Connect.close(statement);
            Connect.close(connection);
        }
        return i;
    }
    // Find a string by ID for a specific tabl
    public String findStringById(String col, String tabel, int id) {
        String i = "null";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT " + col + " FROM " + tabel + " WHERE id=?";

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                i = resultSet.getString(col);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving id findNameByID: " + "DAO:findNameByID " + e.getMessage());
        } finally {
            Connect.close(resultSet);
            Connect.close(statement);
            Connect.close(connection);
        }
        return i;
    }

    public int findIdBytitle(String name, String tabel) {
        int i = -1;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id FROM " + tabel + " WHERE jobtitle=?";

        try {
            connection = Connect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                i = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving id findIdBytitle: " + "DAO:findIdBytitle " + e.getMessage());
        } finally {
            Connect.close(resultSet);
            Connect.close(statement);
            Connect.close(connection);
        }
        return i;
    }

}
