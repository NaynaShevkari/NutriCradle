package org.example;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class StandardUserMenu extends JFrame {
  private Controller controller;
  private LoginView loginView;
  private NutritionalInfoStd caloriePerDishStd;
  private UpdateWeight updateWeight;
  private JLabel welcome;
  private JLabel cal;
  private JLabel protein;
  private String username;
  private String calLimit;
  private String protienIntake;
  private JButton checkDishes;
  private JPanel dynamicPanel;
  private JButton ingrList;
  private JButton updateWeightButton;

  private JButton exit;

  public StandardUserMenu(Controller controller, String username, String calLimit, String proteinIntake){
    super("Standard User Menu");
    this.controller = controller;
    this.username = username;
    this.calLimit = calLimit;
    this.protienIntake = proteinIntake;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    welcome = new JLabel("Welcome " +username +"!");
    cal = new JLabel("Your everyday calorie limit is " +calLimit);
    protein = new JLabel( "Your everyday protein intake should be " +protienIntake +" grams");
    checkDishes = new JButton("Check Dishes");
    checkDishes.setFont(new Font("Arial", Font.BOLD, 18));
    updateWeightButton = new JButton("Update Weight");
    updateWeightButton.setFont(new Font("Arial", Font.BOLD, 18));
    exit = new JButton("Exit");
    exit.setFont(new Font("Arial", Font.BOLD, 18));

    dynamicPanel = new JPanel();
  }

  private void setupLayout() {
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    setSize(1650, 1600);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

    addToPanelWithSpacing(panel, welcome);
    welcome.setFont(new Font("Serif", Font.BOLD, 50));
    addToPanelWithSpacing(panel, cal);
    cal.setFont(new Font("Serif", Font.BOLD, 30));
    addToPanelWithSpacing(panel, protein);
    protein.setFont(new Font("Serif", Font.BOLD, 30));
    addToPanelWithSpacing(panel, checkDishes);
    checkDishes.setMaximumSize(new Dimension(350, 50));
    addToPanelWithSpacing(panel, updateWeightButton);
    updateWeightButton.setMaximumSize(new Dimension(350, 50));
    addToPanelWithSpacing(panel, exit);
    exit.setMaximumSize(new Dimension(350, 50));

    add(panel, BorderLayout.WEST);

    dynamicPanel.setLayout(new BorderLayout());
    add(dynamicPanel, BorderLayout.CENTER);
  }

  private void addToPanelWithSpacing(JPanel panel, JComponent component) {
    component.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(component);
    panel.add(Box.createVerticalStrut(20));
  }

  private void attachHandlers() {
    checkDishes.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
//        caloriePerDishStd = new NutritionalInfoStd(controller);
        NutritionalInfoStd nutrientsPanel = new NutritionalInfoStd(controller);
        dynamicPanel.removeAll();
        dynamicPanel.add(nutrientsPanel);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();

      }
    });
    updateWeightButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateWeight = new UpdateWeight(username, controller);
      }
    });
    exit.addActionListener(e -> System.exit(0));
  }

  private void finalizeSetup() {
    pack();
    setVisible(true);
  }
}
