package org.example;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents a panel for adding new ingredients into the system. This panel allows users to input
 * data about new ingredients, including their associated macronutrients and micronutrients. The
 * panel interacts with a Controller to handle the insertion of ingredient data into the database.
 */

public class AddIngredient extends JPanel {

  private Controller controller;
  private JLabel ingredientName;
  private JTextField ingredient;
  private JLabel calories;
  private JTextField caloriesAmount;
  private JLabel macroName;
  private JTextField macroNameText;
  private JLabel macroAmount;
  private JTextField macroAmountText;
  private JLabel microName;
  private JTextField microNameText;
  private JLabel microAmount;
  private JTextField microAmountText;
  private JButton insert;

  /**
   * Constructs a new AddIngredient panel that is responsible for ingredient data entry.
   *
   * @param controller the controller responsible for managing the interaction between this panel
   *                   and the model, handling data insertion and retrieval.
   */

  public AddIngredient(Controller controller) {
    this.controller = controller;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
  }

  private void initComponents() {
    setSize(750, 700);
    setLocation(150, 300);
    this.setLayout(new FlowLayout(25, 25, 25));
    Font textFieldFont = new Font("Serif", Font.PLAIN, 20);

    ingredientName = new JLabel("Ingredient name");
    ingredientName.setFont(new Font("Serif", Font.PLAIN, 20));
    ingredient = new JTextField(10);
    ingredient.setFont(textFieldFont);

    calories = new JLabel("Calories");
    calories.setFont(new Font("Serif", Font.PLAIN, 20));
    caloriesAmount = new JTextField(10);
    caloriesAmount.setFont(textFieldFont);

    macroName = new JLabel("Macroingredient name");
    macroName.setFont(new Font("Serif", Font.PLAIN, 20));
    macroNameText = new JTextField(10);
    macroNameText.setFont(textFieldFont);

    macroAmount = new JLabel("Macroingredient Amount");
    macroAmount.setFont(new Font("Serif", Font.PLAIN, 20));
    macroAmountText = new JTextField(10);
    macroAmountText.setFont(textFieldFont);

    microName = new JLabel("Microingredient name");
    microName.setFont(new Font("Serif", Font.PLAIN, 20));
    microNameText = new JTextField(10);
    microNameText.setFont(textFieldFont);

    microAmount = new JLabel("Microingredient Amount");
    microAmount.setFont(new Font("Serif", Font.PLAIN, 20));
    microAmountText = new JTextField(10);
    microAmountText.setFont(textFieldFont);

    insert = new JButton("Insert");
    insert.setFont(new Font("Serif", Font.BOLD, 20));
  }

  private void setupLayout() {
    setSize(650, 600);
    setLocation(200, 200);
    this.setLayout(new FlowLayout());

    this.add(ingredientName);
    this.add(ingredient);
    this.add(calories);
    this.add(caloriesAmount);
    this.add(macroName);
    this.add(macroNameText);
    this.add(macroAmount);
    this.add(macroAmountText);
    this.add(microName);
    this.add(microNameText);
    this.add(microAmount);
    this.add(microAmountText);
    this.add(insert);
  }

  private void attachHandlers() {
    insert.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        String ingredientName = ingredient.getText();
        String calories = caloriesAmount.getText();
        String macroName = macroNameText.getText();
        String macroAmount = macroAmountText.getText();
        String microName = microNameText.getText();
        String microAmount = microAmountText.getText();

        if (ingredientName.isEmpty() || calories.isEmpty() || macroName.isEmpty()
            || macroAmount.isEmpty() || microName.isEmpty() || microAmount.isEmpty()) {
          JOptionPane.showMessageDialog(null, "All fields are mandatory", "Error",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          String msg = controller.addIngredient(ingredientName, calories, macroName, macroAmount,
              microName, microAmount);
          JOptionPane.showMessageDialog(null, msg, "Status", JOptionPane.INFORMATION_MESSAGE);
        }
      }
    });
  }

  private void finalizeSetup() {
    setVisible(true);
  }

}
