package org.example;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PremiumUserMenu extends JFrame {
  private Controller controller;
  private NutritionalInfoPrem nutrients;
  private DishSuggestion suggestDish;
  private LogDiet logDiet;
  private LogExercise logEx;
  private UpdateWeight updateWeight;
  private JLabel welcome;
  private JLabel cal;
  private JLabel protein;
  private String username;
  private String calLimit;
  private JButton checkCalorie;
  private String proteinIntake;
  private JButton dishSuggestButton;
  private JButton updateWeightButton;
  private JButton dietLogButton;
  private JButton viewProgressButton;
  private JButton exerciseLog;
  private JButton exerciseProgress;
  private JButton exit;
  private JPanel dynamicPanel;


  public PremiumUserMenu(Controller controller, String username, String calLimit, String proteinIntake){
    super("Premium User Menu");
    this.controller = controller;
    this.username = username;
    this.calLimit = calLimit;
    this.proteinIntake = proteinIntake;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    welcome = new JLabel("Welcome " +username +"!");
    cal = new JLabel("Your everyday calorie limit is " +calLimit);
    protein = new JLabel( "Your everyday protein intake should be " +proteinIntake +" grams");
    checkCalorie = new JButton("View Dishes");
    checkCalorie.setFont(new Font("Arial", Font.BOLD, 18));
    dishSuggestButton = new JButton("Dishes for available ingredients");
    dishSuggestButton.setFont(new Font("Arial", Font.BOLD, 18));
    dietLogButton = new JButton("Insert Diet log");
    dietLogButton.setFont(new Font("Arial", Font.BOLD, 18));
    updateWeightButton = new JButton("Update Weight");
    updateWeightButton.setFont(new Font("Arial", Font.BOLD, 18));
    viewProgressButton = new JButton("Diet Progress");
    viewProgressButton.setFont(new Font("Arial", Font.BOLD, 18));
    exerciseLog = new JButton("Log Exercise");
    exerciseLog.setFont(new Font("Arial", Font.BOLD, 18));
    exerciseProgress = new JButton("Exercise Progress");
    exerciseProgress.setFont(new Font("Arial", Font.BOLD, 18));
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
    cal.setFont(new Font("Serif", Font.BOLD, 25));
    addToPanelWithSpacing(panel, protein);
    protein.setFont(new Font("Serif", Font.BOLD, 25));
    addToPanelWithSpacing(panel, checkCalorie);
    checkCalorie.setMaximumSize(new Dimension(350, 50));
    addToPanelWithSpacing(panel, dishSuggestButton);
    dishSuggestButton.setMaximumSize(new Dimension(350, 50));
    addToPanelWithSpacing(panel, dietLogButton);
    dietLogButton.setMaximumSize(new Dimension(350, 50));
    addToPanelWithSpacing(panel, updateWeightButton);
    updateWeightButton.setMaximumSize(new Dimension(350, 50));
    addToPanelWithSpacing(panel, viewProgressButton);
    viewProgressButton.setMaximumSize(new Dimension(350, 50));
    addToPanelWithSpacing(panel, exerciseLog);
    exerciseLog.setMaximumSize(new Dimension(350, 50));
    addToPanelWithSpacing(panel, exerciseProgress);
    exerciseProgress.setMaximumSize(new Dimension(350, 50));
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
    checkCalorie.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        NutritionalInfoPrem nutrientsPanel = new NutritionalInfoPrem(controller);
        dynamicPanel.removeAll();
        dynamicPanel.add(nutrientsPanel);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();
      }
    });
    dishSuggestButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        DishSuggestion suggestDish = new DishSuggestion(controller);
        dynamicPanel.removeAll();
        dynamicPanel.add(suggestDish);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();
      }
    });
    dietLogButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

//        logDiet = new LogDiet(controller, username);
        LogDiet logDiet = new LogDiet(controller, username);
        dynamicPanel.removeAll();
        dynamicPanel.add(logDiet);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();
      }
    });
    updateWeightButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

//        updateWeight = new UpdateWeight(controller);
        UpdateWeight updateWeight = new UpdateWeight(username, controller);
        dynamicPanel.removeAll();
        dynamicPanel.add(updateWeight);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();
      }
    });
    viewProgressButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ArrayList<?>> data;
        try {
          data = controller.getUserProgress(username);
        } catch (SQLException ex) {
          throw new RuntimeException(ex);
        }
        ViewProgress viewProgress = new ViewProgress(controller, data);
        dynamicPanel.removeAll();
        dynamicPanel.add(viewProgress);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();
      }
    });
    exerciseLog.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        LogExercise logEx = new LogExercise(controller, username);
        dynamicPanel.removeAll();
        dynamicPanel.add(logEx);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
        revalidate();
        repaint();
      }
    });
    exit.addActionListener(e -> System.exit(0));
  }

  private void finalizeSetup() {
    pack();
    setVisible(true);
  }
}
