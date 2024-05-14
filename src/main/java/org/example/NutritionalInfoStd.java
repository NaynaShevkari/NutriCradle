package org.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class NutritionalInfoStd extends JPanel {
public NutritionalInfoStd(Controller controller){
//  super("Nutritional Information");
//  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  DefaultTableModel model = null;
  try {
    model = controller.getDish();
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

  this.add(scrollPane);

  JPanel southPanel = new JPanel();
  JLabel label = new JLabel("Dish Name:");
  label.setFont(new Font("Serif", Font.BOLD, 15));
  JTextField dish = new JTextField(20);
  dish.setMinimumSize(new Dimension(350, 110));
  dish.setMaximumSize(new Dimension(350, 110));
  JButton ingrList = new JButton("Check Ingredients List");
  ingrList.setFont(buttonFont);
  JButton home = new JButton("Home");
  home.setFont(buttonFont);
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
  home.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      setVisible(false);
    }
  });
  southPanel.add(label);
  southPanel.add(dish);
  southPanel.add(ingrList);
  southPanel.add(home);

  this.add(southPanel, BorderLayout.SOUTH);

//  this.pack();
//  this.setLocationRelativeTo(null);
  this.setVisible(true);
}
}
