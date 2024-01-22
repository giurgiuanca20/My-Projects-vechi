package com.example.proj.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connect {
    private static Connect instance = null;
    public String url;
    public String user;
    public String password;
    private int id=-1;

    public Connect() {
        this.url = "jdbc:postgresql://localhost:5432/postgres";
        this.user = "postgres";
        this.password = "varanudorm";
    }
    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public int generateIndex(String tableName) {
        String query = "SELECT * FROM " + tableName;
        int i = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement()
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    public int getIdAccount(AccountRequest accountRequest) {  //gaseste cine s-a conectat in functie de username si parola si returneaza id-ul contului
        String query = "SELECT id_account FROM account WHERE username = ? AND password = ?";
        int idAccount = -1;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setString(1, accountRequest.getUsername());
            ps.setString(2, accountRequest.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idAccount = rs.getInt("id_account");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        id = idAccount;
        return idAccount;
    }

    public void addInfoAccount(Account cont) {      //adauga informatiile unui cont care a fost creat
        String inserareInfoAccount = "INSERT INTO info_account (id_info_account,firstname, lastname, phone, birthdate, email, id_account) VALUES (?,?, ?, ?, ?, ?,?)";
        String inserareAccount = "INSERT INTO account (username, password, id_account) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement psInfoAccount = connection.prepareStatement(inserareInfoAccount);
             PreparedStatement psAccount = connection.prepareStatement(inserareAccount)
        ) {
            psInfoAccount.setInt(1, generateIndex("info_account"));
            psInfoAccount.setString(2, cont.getFirstName());
            psInfoAccount.setString(3, cont.getLastName());
            psInfoAccount.setString(4, cont.getPhoneNumber());
            psInfoAccount.setString(5, cont.getBirthDate());
            psInfoAccount.setString(6, cont.getEmail());
            psInfoAccount.setInt(7, generateIndex("account"));

            psInfoAccount.executeUpdate();

            psAccount.setString(1, cont.getUsername());
            psAccount.setString(2, cont.getPassword());
            psAccount.setInt(3, generateIndex("account"));

            psAccount.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToday(Today today) {
        String selectQuery = "SELECT COUNT(*) AS count FROM today WHERE image = ? AND text = ? AND hour = ? AND id_account = ?";
        String insertQuery = "INSERT INTO today (id_today,image, text, hour, id_account) VALUES (?,?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)
        ) {
            selectStatement.setString(1, today.getImage());
            selectStatement.setString(2, today.getText());
            selectStatement.setString(3, today.getHour());
            selectStatement.setInt(4, id);

            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt("count") == 0) {
                insertStatement.setInt(1, generateIndex("today"));
                insertStatement.setString(2, today.getImage());
                insertStatement.setString(3, today.getText());
                insertStatement.setString(4, today.getHour());
                insertStatement.setInt(5, id);

                insertStatement.executeUpdate();
            } else {
                System.out.println("Înregistrarea există deja pentru ziua curentă. Nu se poate adăuga.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Today> getTodayData() {
        String query = "SELECT image,text,hour FROM today WHERE id_account = ?";
        List<Today> todayList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Today today = new Today();
                today.setImage(resultSet.getString("image"));
                today.setText(resultSet.getString("text"));
                today.setHour(resultSet.getString("hour"));
                todayList.add(today);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return todayList;
    }

    public void addWeekly(Weekly weekly) {
        String selectQuery = "SELECT COUNT(*) AS count FROM weekly WHERE day = ? AND text = ? AND hour = ? AND id_account = ?";
        String insertQuery = "INSERT INTO weekly (id_weekly, day,text,id_account,image,hour) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)
        ) {
            selectStatement.setString(1, weekly.getDay());
            selectStatement.setString(2, weekly.getText());
            selectStatement.setString(3, weekly.getHour());
            selectStatement.setInt(4, id);

            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt("count") == 0) {
                insertStatement.setInt(1, generateIndex("weekly"));
                insertStatement.setString(2, weekly.getDay());
                insertStatement.setString(3, weekly.getText());
                insertStatement.setInt(4, id);
                insertStatement.setString(5, weekly.getImage());
                insertStatement.setString(6, weekly.getHour());

                insertStatement.executeUpdate();
            } else {
                System.out.println("Înregistrarea există deja pentru săptămâna respectivă. Nu se poate adăuga.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Today> getTodayDataFromWeekly(String dayOfWeek) {
        String query = "SELECT image, text, hour FROM weekly WHERE id_account = ? AND day = ?";
        List<Today> todayList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, dayOfWeek);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Today today = new Today();
                today.setImage(resultSet.getString("image"));
                today.setText(resultSet.getString("text"));
                today.setHour(resultSet.getString("hour"));
                todayList.add(today);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return todayList;
    }

    public void deleteToday(Today today) {
        String deleteToday = "DELETE FROM today WHERE image = ? AND text = ? AND hour = ? AND id_account = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteToday)
        ) {
            preparedStatement.setString(1, today.getImage());
            preparedStatement.setString(2, today.getText());
            preparedStatement.setString(3, today.getHour());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Weekly> getWeeklyData() {
        String query = "SELECT day,image,text,hour FROM weekly WHERE id_account = ?";
        List<Weekly> weeklyList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Weekly weekly = new Weekly();
                weekly.setDay(resultSet.getString("day"));
                weekly.setImage(resultSet.getString("image"));
                weekly.setText(resultSet.getString("text"));
                weekly.setHour(resultSet.getString("hour"));
                weeklyList.add(weekly);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return weeklyList;
    }

    public void deleteWeekly(Weekly weekly) {
            String deleteQuery;

            if ("Altele".equals(weekly.getHour())) {
                deleteQuery = "DELETE FROM weekly WHERE day = ? AND (hour < ? OR hour > ?) AND id_account=?";
            } else {
                deleteQuery = "DELETE FROM weekly WHERE day = ? AND hour BETWEEN ? AND ? AND id_account=?";
            }

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)
            ) {
                if ("Altele".equals(weekly.getHour())) {
                    preparedStatement.setString(1, weekly.getDay());
                    preparedStatement.setString(2, "08:00");
                    preparedStatement.setString(3, "20:00");
                    preparedStatement.setInt(4, id);
                } else {
                    String[] interval = weekly.getHour().split("-");
                    String startHour = interval[0];
                    String endHour = interval[1];

                    preparedStatement.setString(1, weekly.getDay());
                    preparedStatement.setString(2, startHour);
                    preparedStatement.setString(3, endHour);
                    preparedStatement.setInt(4, id);
                }

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    public void addMonthly(Monthly monthly) {
        String selectQuery = "SELECT COUNT(*) AS count FROM monthly WHERE image = ? AND text = ? AND hour = ? AND nr_day = ? AND id_account = ?";
        String insertToday = "INSERT INTO monthly (id_monthly,image, text, hour, nr_day, id_account) VALUES (?,?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertToday)
        ) {
            selectStatement.setString(1, monthly.getImage());
            selectStatement.setString(2, monthly.getText());
            selectStatement.setString(3, monthly.getHour());
            selectStatement.setString(4, monthly.getNrDay());
            selectStatement.setInt(5, id);

            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt("count") == 0) {
                insertStatement.setInt(1, generateIndex("monthly"));
                insertStatement.setString(2, monthly.getImage());
                insertStatement.setString(3, monthly.getText());
                insertStatement.setString(4, monthly.getHour());
                insertStatement.setString(5, monthly.getNrDay());
                insertStatement.setInt(6, id);

                insertStatement.executeUpdate();
            } else {
                // Nu se face inserția, poateți gestiona asta în alt mod sau ignora complet
                System.out.println("Înregistrarea există deja. Nu se poate adăuga.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addColor(Color color) {
        String update = "UPDATE color_monthly SET color = ? WHERE id_account = ? AND day = ?";
        String insert = "INSERT INTO color_monthly (id_color_monthly, color, id_account, day) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement updateStatement = connection.prepareStatement(update);
             PreparedStatement insertStatement = connection.prepareStatement(insert)
        ) {
            // Try to update an existing record
            updateStatement.setString(1, color.getColor());
            updateStatement.setInt(2, id);
            updateStatement.setString(3, color.getNrDay());
            int rowsUpdated = updateStatement.executeUpdate();

            // If no record was updated, then insert a new one
            if (rowsUpdated == 0) {
                insertStatement.setInt(1, generateIndex("color_monthly"));
                insertStatement.setString(2, color.getColor());
                insertStatement.setInt(3, id);
                insertStatement.setString(4, color.getNrDay());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Color> getColorList() {
        String query = "SELECT color,day FROM color_monthly WHERE id_account = ?";
        List<Color> colors = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Color color = new Color();
                color.setColor(resultSet.getString("color"));
                color.setNrDay(resultSet.getString("day"));
                colors.add(color);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return colors;
    }

    public List<Today> getMonthlyData(int nrDay) {
        String query = "SELECT image,text,hour FROM monthly WHERE id_account = ? AND nr_day= ?";
        List<Today> todayList = new ArrayList<>();
        String day= Integer.toString(nrDay);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,day);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Today today = new Today();
                today.setImage(resultSet.getString("image"));
                today.setText(resultSet.getString("text"));
                today.setHour(resultSet.getString("hour"));
                todayList.add(today);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return todayList;
    }

    public void deleteMonthly(Monthly monthly) {
        String deleteToday = "DELETE FROM monthly WHERE image = ? AND text = ? AND hour = ? AND nr_day = ? AND id_account = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteToday)
        ) {
            preparedStatement.setString(1, monthly.getImage());
            preparedStatement.setString(2, monthly.getText());
            preparedStatement.setString(3, monthly.getHour());
            preparedStatement.setString(4, monthly.getNrDay());
            preparedStatement.setInt(5,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Today> getTodayDataFromMonthly(String dayOfMonth) {
        String query = "SELECT image, text, hour FROM monthly WHERE id_account = ? AND nr_day = ?";
        List<Today> todayList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, dayOfMonth);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Today today = new Today();
                today.setImage(resultSet.getString("image"));
                today.setText(resultSet.getString("text"));
                today.setHour(resultSet.getString("hour"));
                todayList.add(today);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return todayList;
    }

}
