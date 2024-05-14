package org.example;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DishSuggestion extends JPanel {
  private Controller controller;
  private JLabel ingrLabel;
  private JTextField ingredientsField;
  private JButton suggestionButton;
  private JButton homeButton;
  private JTable table;
  private JScrollPane scrollPane;

  public DishSuggestion(Controller controller) {
    this.controller = controller;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
  }

  private void initComponents() {
    ingrLabel = new JLabel("  Ingredients");
    ingrLabel.setFont(new Font("Serif", Font.BOLD, 20));
    ingredientsField = new JTextField(15);
    ingredientsField.setFont(new Font("Serif", Font.BOLD, 20));
    suggestionButton = new JButton("Suggest Dishes");
    suggestionButton.setFont(new Font("Serif", Font.BOLD, 15));
    homeButton = new JButton("Home");
    homeButton.setFont(new Font("Serif", Font.BOLD, 15));
    table = new JTable();
    scrollPane = new JScrollPane(table);
    table.setFillsViewportHeight(true);
    Font font = new Font("Serif", Font.BOLD,20);
    table.setFont(font);
    table.getTableHeader().setFont(font);
    table.setRowHeight((int) (font.getSize() * 1.5));
  }

  private void setupLayout() {
    setSize(650, 600);
    setLocation(200, 200);
    setLayout(new BorderLayout());
    JPanel topPanel = new JPanel(new FlowLayout());
    topPanel.add(ingrLabel);
    topPanel.add(ingredientsField);
    topPanel.add(suggestionButton);
    topPanel.add(homeButton);
    add(topPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
  }

  private void attachHandlers() {
    suggestionButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String ingredients = ingredientsField.getText();
        if (ingredients.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Please enter at least 1 ingredient", "Error",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          DefaultTableModel dishes = null;
          try {
            dishes = controller.suggestDish(ingredients);
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }
          table.setModel(dishes);
        }
      }
    });
    homeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });
  }

  private void finalizeSetup() {
    setVisible(true);
  }
}

