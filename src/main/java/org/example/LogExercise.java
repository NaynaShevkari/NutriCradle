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

public class LogExercise extends JPanel {

  private Controller controller;
  private String username;
  private JLabel exerciseName;
  private JTextField exerciseNameText;
  private JLabel duration;
  private JTextField durationText;
  private JButton logButton;

  public LogExercise(Controller controller, String username) {
    this.controller = controller;
    this.username = username;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
  }

  private void initComponents() {
    exerciseName = new JLabel("Exercise name");
    exerciseNameText = new JTextField(10);
    duration = new JLabel("Duration");
    durationText = new JTextField(10);
    logButton = new JButton("Log");
  }

  private void setupLayout() {
    setSize(650, 600);
    setLocation(200, 200);
    this.setLayout(new FlowLayout());
    Font labelFont = new Font("Serif", Font.BOLD, 20);
    Font textFieldFont = new Font("Serif", Font.PLAIN, 20);
    Font buttonFont = new Font("Serif", Font.BOLD, 20);

    this.add(exerciseName);
    exerciseName.setFont(labelFont);
    this.add(exerciseNameText);
    exerciseNameText.setFont(textFieldFont);
    this.add(duration);
    duration.setFont(labelFont);
    this.add(durationText);
    durationText.setFont(textFieldFont);
    this.add(logButton);
    logButton.setFont(buttonFont);
  }

  private void attachHandlers() {
    logButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String exName = exerciseNameText.getText();
        String duration = durationText.getText();

        if (exName.isEmpty() || duration.isEmpty()) {
          JOptionPane.showMessageDialog(null, "All fields are mandatory", "Error",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          try{
            double noServes = Double.parseDouble(duration);
            String msg = controller.logExercise(username, exName, duration);
            JOptionPane.showMessageDialog(null, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
          }
          catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Duration should be a valid number", "Error",
                JOptionPane.INFORMATION_MESSAGE);
          }
        }
      }
    });
  }

  private void finalizeSetup() {
    setVisible(true);
  }
}