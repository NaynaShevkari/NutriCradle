package org.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class NutritionalInfoPrem extends JPanel {
  private DefaultTableModel initialModel;

  public NutritionalInfoPrem(Controller controller){
    this.setLayout(new GridLayout(2,1));
    DefaultTableModel model = null;
    try {
      model = controller.getDish();
      this.initialModel = model;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    Font buttonFont = new Font("Serif", Font.BOLD, 15);
    JTable table = new JTable(model);
    Font font = new Font("Serif", Font.BOLD,20);
    table.setFont(font);
    table.getTableHeader().setFont(font);
    table.setRowHeight((int) (font.getSize() * 1.5));
    JScrollPane scrollPane = new JScrollPane(table);
    table.setFillsViewportHeight(true);
    this.add(scrollPane, BorderLayout.PAGE_START);

    JPanel southPanel = new JPanel(new FlowLayout());
    JLabel label = new JLabel("Dish Name:");
    label.setFont(new Font("Serif", Font.BOLD, 15));
    JTextField dish = new JTextField(20);
    dish.setMinimumSize(new Dimension(350, 110));
    dish.setMaximumSize(new Dimension(350, 110));
    JButton ingrList = new JButton("Ingredients List");
    ingrList.setMaximumSize(new Dimension(350, 60));
    ingrList.setFont(buttonFont);
    JButton microNutrients = new JButton("Micronutrients");
    microNutrients.setMaximumSize(new Dimension(350, 60));
    microNutrients.setFont(buttonFont);
    JButton macroNutrients = new JButton("Macronutrients");
    macroNutrients.setMaximumSize(new Dimension(350, 60));
    macroNutrients.setFont(buttonFont);
    JButton back = new JButton("Back to Dish");
    back.setMaximumSize(new Dimension(350, 60));
    back.setFont(buttonFont);
    ingrList.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String dishName = dish.getText();
        if(dishName.isEmpty()){
          JOptionPane.showMessageDialog(null, "Dish name cannot be empty", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
          try {
            DefaultTableModel newModel = controller.getIngredientList(dishName);
            table.setModel(newModel);
            table.revalidate();
            table.repaint();
          } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to load data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
          }
        }
      }
    });

    microNutrients.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String dishName = dish.getText();
        if(dishName.isEmpty()){
          JOptionPane.showMessageDialog(null, "Dish name cannot be empty", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
          try {
            DefaultTableModel newModel = controller.getMicronutrients(dishName);
            table.setModel(newModel);
            table.revalidate();
            table.repaint();
          } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to load data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
          }
        }
      }
    });

    macroNutrients.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String dishName = dish.getText();
        if(dishName.isEmpty()){
          JOptionPane.showMessageDialog(null, "Dish name cannot be empty", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
          try {
            DefaultTableModel newModel = controller.getMacronutrients(dishName);
            table.setModel(newModel);
            table.revalidate();
            table.repaint();
          } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to load data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
          }
        }
      }
    });

    back.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dish.setText("");
        table.setModel(initialModel);
        table.revalidate();
        table.repaint();
      }
    });

    southPanel.add(label);
    southPanel.add(dish);
    southPanel.add(ingrList);
    southPanel.add(microNutrients);
    southPanel.add(macroNutrients);
    southPanel.add(back);

    this.add(southPanel, BorderLayout.NORTH);
    this.setVisible(true);
  }
}
