package org.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents the main user interface for administrative actions within the application. This
 * interface allows administrators to perform various tasks such as inserting new dishes, viewing
 * ingredients, adding new ingredients, and deleting dishes.
 */
public class AdminMenu extends JFrame {

  private Controller controller;
  private DeleteDish deleteDish;
  private JButton insertButton;
  private JButton viewIngrButton;
  private JButton addIngredient;
  private JButton deleteButton;
  private JButton checkDishes;
  private JPanel dynamicPanel;
  private JPanel panel;
  private JButton exit;

  /**
   * Constructs an AdminMenu with the specified controller to manage interactions.
   *
   * @param controller the controller that handles business logic and database interactions
   */


  public AdminMenu(Controller controller) {
    super("Admin Menu");
    this.controller = controller;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    checkDishes = new JButton("Check Dishes");
    viewIngrButton = new JButton("View Ingredients");
    addIngredient = new JButton("Add Ingredient");
    insertButton = new JButton("Insert new Dish");
    deleteButton = new JButton("Delete Dish");
    exit = new JButton("Exit");

    panel = new JPanel();

    dynamicPanel = new JPanel();
  }

  private void setupLayout() {
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
    panel.setPreferredSize(new Dimension(200, getHeight()));

    dynamicPanel.setLayout(new BorderLayout());

    Font buttonFont = new Font("Serif", Font.BOLD, 20);

    checkDishes.setFont(buttonFont);
    panel.add(checkDishes);
    viewIngrButton.setFont(buttonFont);
    panel.add(viewIngrButton);
    addIngredient.setFont(buttonFont);
    panel.add(addIngredient);
    insertButton.setFont(buttonFont);
    panel.add(insertButton);
    deleteButton.setFont(buttonFont);
    panel.add(deleteButton);
    exit.setFont(buttonFont);
    panel.add(exit);

    add(panel, BorderLayout.WEST);
    add(dynamicPanel, BorderLayout.CENTER);
  }

  private void attachHandlers() {
    addIngredient.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        AddIngredient addIngredient = new AddIngredient(controller);
        dynamicPanel.removeAll();
        dynamicPanel.add(addIngredient);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();

      }
    });
    viewIngrButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ViewIngredients viewIngredients = new ViewIngredients(controller);
        dynamicPanel.removeAll();
        dynamicPanel.add(viewIngredients);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();

      }
    });
    checkDishes.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        NutritionalInfoStd nutrientsPanel = new NutritionalInfoStd(controller);
        dynamicPanel.removeAll();
        dynamicPanel.add(nutrientsPanel);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();

      }
    });
    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        DeleteDish deleteDish = new DeleteDish(controller);
        dynamicPanel.removeAll();
        dynamicPanel.add(deleteDish);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();
      }
    });
    exit.addActionListener(e -> System.exit(0));
  }

  private void finalizeSetup() {
    pack();
    setVisible(true);
  }
}
