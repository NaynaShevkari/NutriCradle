package org.example;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteDish extends JPanel {
  private Controller controller;
  private JLabel dishName;
  private JTextField dish;
  private JButton delete;
//  private JButton home;

  public DeleteDish(Controller controller){
//    super("Delete Dish");
    this.controller = controller;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    dishName = new JLabel("Dish Name:   ");
    dish = new JTextField(10);
    delete = new JButton("Delete");
//    home = new JButton("Home");
  }

  private void setupLayout() {
    setSize(650, 600);
    setLocation(200, 200);
    this.setLayout(new FlowLayout());
    Font textFieldFont = new Font("Serif", Font.PLAIN, 20);
    Font buttonFont = new Font("Serif", Font.BOLD, 20);
    Font labelFont = new Font("Serif", Font.BOLD, 20);

    this.add(dishName);
    dishName.setFont(labelFont);
    this.add(dish);
    dish.setFont(textFieldFont);
    this.add(delete);
    delete.setFont(buttonFont);
//    this.add(home);
//    home.setFont(buttonFont);
  }

  private void attachHandlers() {
    delete.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String dishName = dish.getText();
        if (dishName.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Dish name cannot be empty", "Error",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          String msg = controller.deleteDish(dishName);
          JOptionPane.showMessageDialog(null, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
      }
    });
  }

  private void finalizeSetup() {
    setVisible(true);
  }
}
