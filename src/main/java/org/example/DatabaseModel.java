package org.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.table.DefaultTableModel;

/**
 * This class handles all database operations for the application. It includes methods to connect to
 * and disconnect from the database, authenticate users, and retrieve or update various types of
 * nutritional and exercise data.
 */
public class DatabaseModel {

  public Connection conn = null;
  private String url;
  //    "jdbc:mysql://localhost:3306/nutritiontrackingdb?characterEncoding=UTF-8&useSSL=false"

  /**
   * Connects to the database using the provided URL, username, and password.
   *
   * @param url      the URL of the database.
   * @param username the username for database access.
   * @param password the password for database access.
   * @throws SQLException if a database access error occurs.
   */
  public void connectToDatabase(String url, String username, String password) throws SQLException {
    this.url = url;
    Properties connectionProps = new Properties();
    connectionProps.put("user", username);
    connectionProps.put("password", password);

    conn = DriverManager.getConnection(url, connectionProps);

  }

  /**
   * Disconnects from the database if the connection is currently open.
   *
   * @throws SQLException if a database access error occurs.
   */
  public void disconnect() throws SQLException {
    if (conn != null && !conn.isClosed()) {
      conn.close();
      System.out.println("Disconnected from the database.");
    }
  }

  /**
   * Authenticates a user attempting to log in.
   *
   * @param username the username of the user attempting to log in.
   * @param password the password of the user attempting to log in.
   * @return the type of user (admin, standard, premium) if login is successful, or null if
   * unsuccessful.
   * @throws SQLException if a database access error occurs.
   */
  public String login(String username, String password) throws SQLException {
    String query = "SELECT usertype FROM userlogin WHERE username = ? AND password = ?";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, username);
      statement.setString(2, password);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString("usertype");
        } else {
          return null;
        }
      }
    }
  }

  /**
   * Retrieves the daily calorie limit for a specified user.
   *
   * @param username the username of the user.
   * @return the daily calorie limit as a string.
   * @throws SQLException if a database access error occurs or the user does not exist.
   */
  public String getCalorieLimit(String username) throws SQLException {
    String query = "SELECT everyday_calories_limit FROM users WHERE username = ?";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString("everyday_calories_limit");
        } else {
          return null;
        }
      }
    }
  }

  /**
   * Retrieves the daily protein intake limit for a specified user.
   *
   * @param username the username of the user.
   * @return the protein intake limit as a string.
   * @throws SQLException if a database access error occurs or the user does not exist.
   */
  public String getProteinIntake(String username) throws SQLException {
    String query = "SELECT protein_intake_limit FROM users WHERE username = ?";
    System.out.println(query);
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString("protein_intake_limit");
        } else {
          System.out.println("Inside else");
          return null;
        }
      }
    }
  }

  /**
   * Retrieves the details of all available dishes in the database.
   *
   * @return a DefaultTableModel that contains the names of dishes and their respective calorie
   * values.
   * @throws SQLException if a database access error occurs.
   */
  public DefaultTableModel getDish() throws SQLException {
    DefaultTableModel model = new DefaultTableModel(new Object[]{"Dish", "Total Calories"}, 0);
    String query = "{CALL getDishCalValue()}";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          String dishName = resultSet.getString("dish_name");
          double totalCalories = resultSet.getDouble("total_calories");
          model.addRow(new Object[]{dishName, totalCalories});
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return model;
  }

  /**
   * * Retrieves a list of all ingredients available in the database. * This method is intended for
   * administrative purposes to view all registered ingredients. * * @return a DefaultTableModel
   * with one column containing the names of all ingredients. * @throws SQLException if a database
   * access error occurs.
   */
  public DefaultTableModel getIngredientAdmin() throws SQLException {
    DefaultTableModel model = new DefaultTableModel(new Object[]{"Ingredients"}, 0);
    String query = "SELECT ingredient_name FROM ingredients";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          String ingrList = resultSet.getString("ingredient_name");
          model.addRow(new Object[]{ingrList});
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return model;
  }

  /**
   * Retrieves the ingredients for a specified dish from the database.
   *
   * @param dish the name of the dish.
   * @return a DefaultTableModel that includes ingredient names, their quantities, and calorie
   * contributions.
   * @throws SQLException if a database access error occurs.
   */
  public DefaultTableModel getIngredientList(String dish) throws SQLException {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Ingredient", "Quantity", "Total Calories"}, 0);
    String query = "{CALL GetDishIngredientDetails(?)}";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, dish);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          String ingredientName = resultSet.getString("ingredient_name");
          double quantity = resultSet.getDouble("quantity");
          double totalCalories = resultSet.getDouble("total_calories");

          model.addRow(new Object[]{ingredientName, quantity, totalCalories});
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return model;
  }

  /**
   * Retrieves the user's progress in terms of calorie and protein consumption over a period.
   *
   * @param username the username of the user.
   * @return a list of array lists containing dates, calorie consumption, and protein consumption.
   * @throws SQLException if a database access error occurs.
   */
  public List<ArrayList<?>> getUserProgress(String username) throws SQLException {
    List<ArrayList<?>> progressLists = new ArrayList<>();
    ArrayList<LocalDate> dates = new ArrayList<>();
    ArrayList<Double> calorie = new ArrayList<>();
    ArrayList<Double> protein = new ArrayList<>();

    String callSQL = "{ CALL GetUserProgress(?) }";
    try (PreparedStatement stmt = conn.prepareCall(callSQL)) {
      stmt.setString(1, username);
      boolean hasResults = stmt.execute();

      if (hasResults) {
        try (ResultSet rs = stmt.getResultSet()) {
          while (rs.next()) {
            dates.add(rs.getDate(1).toLocalDate());
            calorie.add(rs.getDouble(2));
            protein.add(rs.getDouble(3));
          }
        }
      }
    }

    progressLists.add(dates);
    progressLists.add(calorie);
    progressLists.add(protein);
    return progressLists;
  }

  /**
   * Retrieves the macronutrient details for a specific dish, including the quantity consumed and
   * upper limits.
   *
   * @param dish the name of the dish.
   * @return a DefaultTableModel containing the names, quantities, and upper limits of
   * macronutrients.
   * @throws SQLException if a database access error occurs.
   */

  public DefaultTableModel getMacronutrients(String dish) throws SQLException {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Macronutrient", "Quantity in grams", "Upper Limit"}, 0);
    String query = "{CALL GetDishMacroDetails(?)}";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, dish);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          String macronutrient_name = resultSet.getString("macronutrient_name");
          double amount = resultSet.getDouble("total_amount");
          double limit = resultSet.getDouble("limit_in_grams");

          model.addRow(new Object[]{macronutrient_name, amount, limit});
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return model;
  }

  /**
   * Retrieves the micronutrient details for a specific dish, including the quantity consumed.
   *
   * @param dish the name of the dish.
   * @return a DefaultTableModel containing the names and quantities of micronutrients.
   * @throws SQLException if a database access error occurs.
   */
  public DefaultTableModel getMicronutrients(String dish) throws SQLException {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Micronutrient", "Quantity in grams"}, 0);
    String query = "{CALL GetDishMicroDetails1(?)}";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, dish);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          String micronutrient_name = resultSet.getString("micronutrient_name");
          double amount = resultSet.getDouble("total_amount");

          model.addRow(new Object[]{micronutrient_name, amount});
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return model;
  }

  /**
   * Retrieves a list of dishes that include specified ingredients. This method uses a stored
   * procedure to find dishes based on the ingredients provided.
   *
   * @param ingredients A string containing the ingredients used to filter the dishes. This could be
   *                    a single ingredient or a comma-separated list of ingredients.
   * @return a DefaultTableModel with one column containing the names of dishes that contain the
   * specified ingredients.
   * @throws SQLException if there is a problem executing the SQL query or connecting to the
   *                      database.
   */

  public DefaultTableModel getDishes(String ingredients) {
    DefaultTableModel model = new DefaultTableModel(new Object[]{"Dishes"}, 0);
    String query = "{CALL FindDishesByIngredients(?)}";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, ingredients);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          String dish_name = resultSet.getString("dish_name");

          model.addRow(new Object[]{dish_name});
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return model;
  }

  /**
   * Logs a user's dietary intake into the database.
   *
   * @param username the user's username.
   * @param dishName the name of the dish consumed.
   * @param serves   the serving size consumed.
   * @return a status message indicating whether the log was successful or not.
   */

  public String logDiet(String username, String dishName, String serves) {
    String msg = "";
    String query = "{CALL InsertUserDietaryLogWithServings(?,?,?)}";
    try (CallableStatement statement = conn.prepareCall(query)) {
      statement.setString(1, username);
      statement.setString(2, dishName);
      statement.setDouble(3, Double.parseDouble(serves));

      boolean hasResults = statement.execute();
      if (hasResults) {
        try (ResultSet resultSet = statement.getResultSet()) {
          if (resultSet.next()) {
            msg = resultSet.getString("status_message");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      msg = "Error: " + e.getMessage();
    }
    return msg;
  }

  /**
   * Logs a user's exercise activity into the database.
   *
   * @param username     the user's username.
   * @param exerciseName the name of the exercise performed.
   * @param duration     the duration of the exercise in minutes.
   * @return a status message indicating whether the log was successful or not.
   */

  public String logExercise(String username, String exerciseName, String duration) {
    String msg = "";
    String query = "{CALL AddExerciseLog(?,?,?)}";
    try (CallableStatement statement = conn.prepareCall(query)) {
      statement.setString(1, username);
      statement.setString(2, exerciseName);
      statement.setDouble(3, Double.parseDouble(duration));

      boolean hasResults = statement.execute();
      if (hasResults) {
        try (ResultSet resultSet = statement.getResultSet()) {
          if (resultSet.next()) {
            msg = resultSet.getString("status_message");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      msg = "Error: " + e.getMessage();
    }
    return msg;
  }

  /**
   * Updates the weight for a specified user in the database.
   *
   * @param username the user's username.
   * @param weight   the new weight to be recorded.
   * @return a status message indicating whether the update was successful or not.
   */
  public String updateWeight(String username, String weight) {
    String msg = "";
    String query = "{CALL UpdateUserWeight(?,?)}";
    try (CallableStatement statement = conn.prepareCall(query)) {
      statement.setString(1, username);
      statement.setString(2, weight);

      boolean hasResults = statement.execute();
      if (hasResults) {
        try (ResultSet resultSet = statement.getResultSet()) {
          if (resultSet.next()) {
            msg = resultSet.getString("status_message");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      msg = "Error: " + e.getMessage();
    }
    return msg;
  }

  /**
   * Removes a dish from the database.
   *
   * @param dishName the name of the dish to be removed.
   * @return a status message indicating whether the deletion was successful or not.
   */
  public String deleteDish(String dishName) {
    String msg = "";
    String query = "{CALL RemoveDish(?)}";
    try (CallableStatement statement = conn.prepareCall(query)) {
      statement.setString(1, dishName);

      boolean hasResults = statement.execute();
      if (hasResults) {
        try (ResultSet resultSet = statement.getResultSet()) {
          if (resultSet.next()) {
            msg = resultSet.getString("status_message");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      msg = "Error: " + e.getMessage();
    }
    return msg;
  }

  /**
   * Adds a new ingredient along with its associated nutritional data to the database.
   *
   * @param ingredientName       the name of the ingredient.
   * @param calories             the calorie content per 100g of the ingredient.
   * @param macronutrientNames   comma-separated names of macronutrients.
   * @param macronutrientAmounts comma-separated amounts of each macronutrient.
   * @param micronutrientNames   comma-separated names of micronutrients.
   * @param micronutrientAmounts comma-separated amounts of each micronutrient.
   * @return a status message indicating whether the addition was successful or not.
   */
  public String addIngredient(String ingredientName, String calories, String macronutrientNames,
      String macronutrientAmounts, String micronutrientNames, String micronutrientAmounts) {
    String result;
    try {
      double calorieValue = Double.parseDouble(calories);
      CallableStatement statement = conn.prepareCall("{CALL AddIngredient(?,?,?,?,?,?)}");
      statement.setString(1, ingredientName);
      statement.setDouble(2, calorieValue);
      statement.setString(3, macronutrientNames);
      statement.setString(4, macronutrientAmounts);
      statement.setString(5, micronutrientNames);
      statement.setString(6, micronutrientAmounts);

      boolean hasResults = statement.execute();
      if (hasResults) {
        ResultSet rs = statement.getResultSet();
        if (rs.next()) {
          result = rs.getString("status_message");
        } else {
          result = "No status message returned.";
        }
      } else {
        result = "Procedure executed, no result set.";
      }
    } catch (NumberFormatException e) {
      result = "Error: Incorrect number format for calories or amounts.";
    } catch (SQLException e) {
      if (e.getErrorCode() == 1318) {
        result = "Error: Incorrect number of arguments provided to the stored procedure.";
      } else {
        result = "SQL Error: " + e.getMessage();
      }
    }
    return result;
  }

}
