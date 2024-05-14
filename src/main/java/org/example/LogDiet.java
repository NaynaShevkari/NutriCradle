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

public class LogDiet extends JPanel {

  private Controller controller;
  private String username;
  private JLabel dishName;
  private JTextField dishNameText;
  private JLabel serves;
  private JTextField noOfServes;
  private JButton logButton;
  private JButton home;

  public LogDiet(Controller controller, String username) {
//    super("Log diet");
    this.controller = controller;
    this.username = username;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    dishName = new JLabel("Dish name");
    dishNameText = new JTextField(10);
    serves = new JLabel("Number of serves");
    noOfServes = new JTextField(10);
    logButton = new JButton("Log");
    home = new JButton("Home");
  }

  private void setupLayout() {
    setSize(650, 600);
    setLocation(200, 200);
    this.setLayout(new FlowLayout());
    Font labelFont = new Font("Serif", Font.BOLD, 20);
    Font textFieldFont = new Font("Serif", Font.PLAIN, 20);
    Font buttonFont = new Font("Serif", Font.BOLD, 20);

    this.add(dishName);
    dishName.setFont(labelFont);
    this.add(dishNameText);
    dishNameText.setFont(textFieldFont);
    this.add(serves);
    serves.setFont(labelFont);
    this.add(noOfServes);
    noOfServes.setFont(textFieldFont);
    this.add(logButton);
    logButton.setFont(buttonFont);
    this.add(home);
    home.setFont(buttonFont);
  }

  private void attachHandlers() {
    logButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String dishName = dishNameText.getText();
        String serves = noOfServes.getText();

        if (dishName.isEmpty() || serves.isEmpty()) {
          JOptionPane.showMessageDialog(null, "All fields are mandatory", "Error",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          try{
            double noServes = Double.parseDouble(serves);
            String msg = controller.logDiet(username, dishName, serves);
            JOptionPane.showMessageDialog(null, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
          }
          catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Serves should be a valid number", "Error",
                JOptionPane.INFORMATION_MESSAGE);
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
  }

  private void finalizeSetup() {
//    pack();
    setVisible(true);
  }
}
