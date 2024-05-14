package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * The Controller class is responsible for handling the business logic of the application.
 * It communicates between the DatabaseModel and the View, facilitating data exchange
 * and coordinating responses to user interactions.
 */

public class Controller {
  private DatabaseModel db;
  private View view;

  /**
   * Constructs a Controller object with a specified database model and view.
   * @param db the database model used for data operations
   * @param view the view used for interacting with the user interface
   */

  public Controller(DatabaseModel db, View view) {
    this.view = view;
    this.db = db;
  }

  /**
   * Attempts to connect to the database using the provided credentials and URL.
   * @param url the database URL
   * @param username the username for database access
   * @param password the password for database access
   * @return true if connection is successful, false otherwise
   */

  public boolean dbConnect(String url, String username, String password) {
    try {
      db.connectToDatabase(url, username, password);
      return true;
    } catch (SQLException ex) {
      return false;
    }
  }

  /**
   * Attempts to log in a user with the provided username and password.
   * @param username the username of the user
   * @param password the password of the user
   * @return the user type if login is successful, otherwise returns "Invalid username or password"
   */
  public String login(String username, String password) {
    try {
      String userType = db.login(username, password);
      return userType;
    } catch (SQLException e) {
      return "Invalid username or password";
    }
  }

  /**
   * Retrieves the calorie limit for a specified user.
   * @param username the username of the user
   * @return the calorie limit if the user exists, otherwise returns "Invalid username"
   */
  public String getCalorieLimit(String username) {
    try {
      String user = db.getCalorieLimit(username);
      return user;
    } catch (SQLException e) {
      return "Invalid username";
    }
  }

  /**
   * Retrieves the protein intake requirement for a specified user.
   * @param username the username of the user
   * @return the protein intake limit if the user exists, otherwise returns "Invalid username"
   */

  public String getProteinIntake(String username) {
    try {
      String user = db.getProteinIntake(username);
      return user;
    } catch (SQLException e) {
      return "Invalid username";
    }
  }

  /**
   * Retrieves the list of dishes from the database.
   * @return a DefaultTableModel containing the dishes.
   * @throws SQLException if a database access error occurs.
   */
  public DefaultTableModel getDish() throws SQLException {
    return db.getDish();
  }

  /**
   * Retrieves a list of ingredients for a specific dish from the database.
   * @param dish the name of the dish for which ingredients are to be retrieved.
   * @return a DefaultTableModel containing ingredients of the specified dish.
   * @throws SQLException if a database access error occurs.
   */

  public DefaultTableModel getIngredientList(String dish) throws SQLException {
    return db.getIngredientList(dish);
  }

  /**
   * Retrieves macronutrients for a specified dish.
   * @param dish the name of the dish.
   * @return a DefaultTableModel containing the macronutrients of the specified dish.
   * @throws SQLException if a database access error occurs.
   */

  public DefaultTableModel getMacronutrients(String dish) throws SQLException {
    return db.getMacronutrients(dish);
  }

  /**
   * Retrieves micronutrients for a specified dish.
   * @param dish the name of the dish.
   * @return a DefaultTableModel containing the micronutrients of the specified dish.
   * @throws SQLException if a database access error occurs.
   */
  public DefaultTableModel getMicronutrients(String dish) throws SQLException {
    return db.getMicronutrients(dish);
  }

  /**
   * Suggests dishes based on the provided ingredients.
   * @param ingredients a comma-separated string of ingredients.
   * @return a DefaultTableModel containing suggested dishes.
   * @throws SQLException if a database access error occurs.
   */

  public DefaultTableModel suggestDish(String ingredients) throws SQLException {
    return db.getDishes(ingredients);
  }

  /**
   * Retrieves progress of a user based on their username.
   * @param username the username of the user.
   * @return a List of ArrayList containing user progress details.
   * @throws SQLException if a database access error occurs.
   */

  public List<ArrayList<?>> getUserProgress(String username) throws SQLException {
    return db.getUserProgress(username);
  }

  /**
   * Logs a diet record for a user.
   * @param username the username of the user.
   * @param dishName the name of the dish consumed.
   * @param serves the serving size consumed.
   * @return a status message indicating the success or failure of the operation.
   */

  public String logDiet(String username, String dishName, String serves) {
    return db.logDiet(username, dishName, serves);
  }

  /**
   * Logs an exercise record for a user.
   * @param username the username of the user.
   * @param exerciseName the name of the exercise performed.
   * @param duration the duration of the exercise in minutes.
   * @return a status message indicating the success or failure of the operation.
   */

  public String logExercise(String username, String exerciseName, String duration) {
    return db.logExercise(username, exerciseName, duration);
  }

  /**
   * Updates the weight for a user.
   * @param username the username of the user.
   * @param weight the new weight of the user.
   * @return a status message indicating the success or failure of the operation.
   */
  public String updateWeight(String username, String weight) {
    return db.updateWeight(username, weight);
  }

  /**
   * Deletes a dish from the database.
   * @param dishName the name of the dish to be deleted.
   * @return a status message indicating the success or failure of the operation.
   */
  public String deleteDish(String dishName) {
    return db.deleteDish(dishName);
  }

  /**
   * Retrieves a list of ingredients for admin management.
   * @return a DefaultTableModel containing all ingredients.
   * @throws SQLException if a database access error occurs.
   */
  public DefaultTableModel getIngredientAdmin() throws SQLException {
    return db.getIngredientAdmin();
  }

  /**
   * Adds a new ingredient to the database.
   * @param ingredientName the name of the ingredient.
   * @param calories the calorie content per 100g of the ingredient.
   * @param macronutrientNames comma-separated names of macronutrients.
   * @param macronutrientAmounts comma-separated amounts of each macronutrient.
   * @param micronutrientNames comma-separated names of micronutrients.
   * @param micronutrientAmounts comma-separated amounts of each micronutrient.
   * @return a status message indicating the success or failure of the operation.
   */
  public String addIngredient(String ingredientName, String calories, String macronutrientNames,
      String macronutrientAmounts, String micronutrientNames, String micronutrientAmounts) {
    return db.addIngredient(ingredientName, calories, macronutrientNames, macronutrientAmounts,
        micronutrientNames, micronutrientAmounts);
  }
}