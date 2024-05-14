package org.example;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewIngredients extends JPanel {
  private Controller controller;
  private JTable table;
  private JScrollPane scrollPane;

  public ViewIngredients(Controller controller) {
    this.controller = controller;
    initComponents();
    setupLayout();
    getIngredients();
    finalizeSetup();
  }

  private void initComponents() {
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
    add(topPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
  }

  private void getIngredients(){
    DefaultTableModel ingredients = null;
    try {
      ingredients = controller.getIngredientAdmin();
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
    table.setModel(ingredients);
  }


//  private void attachHandlers() {
//    suggestionButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        String ingredients = ingredientsField.getText();
//        if (ingredients.isEmpty()) {
//          JOptionPane.showMessageDialog(null, "Please enter at least 1 ingredient", "Error",
//              JOptionPane.INFORMATION_MESSAGE);
//        } else {
//          DefaultTableModel dishes = null;
//          try {
//            dishes = controller.suggestDish(ingredients);
//          } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//          }
//          table.setModel(dishes);
//        }
//      }
//    });
//    homeButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        setVisible(false);
//      }
//    });
//  }

  private void finalizeSetup() {
    setVisible(true);
  }
}

