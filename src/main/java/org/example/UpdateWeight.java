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

public class UpdateWeight extends JPanel {
  private Controller controller;
  private String username;
  private JLabel currentWeight;
  private JTextField weight;
  private JButton updateButton;
  private JButton home;

  public UpdateWeight(String username, Controller controller) {
//    super("Update Weight");
    this.controller = controller;
    this.username = username;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    currentWeight = new JLabel("Current Weight");
    weight = new JTextField(10);
    updateButton = new JButton("Update");
    home = new JButton("Home");
  }

  private void setupLayout() {
    setSize(650, 600);
    setLocation(200, 200);
    this.setLayout(new FlowLayout());
    Font labelFont = new Font("Serif", Font.BOLD, 20);
    Font textFieldFont = new Font("Serif", Font.PLAIN, 20);
    Font buttonFont = new Font("Serif", Font.BOLD, 20);

    this.add(currentWeight);
    currentWeight.setFont(labelFont);
    this.add(weight);
    weight.setFont(textFieldFont);
    this.add(updateButton);
    updateButton.setFont(buttonFont);
    this.add(home);
    home.setFont(buttonFont);
  }

  private void attachHandlers() {
    updateButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String currentWeight = weight.getText();
        if (currentWeight.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Weight cannot be empty", "Error",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          String msg = controller.updateWeight(username, currentWeight);
          JOptionPane.showMessageDialog(null, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
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
