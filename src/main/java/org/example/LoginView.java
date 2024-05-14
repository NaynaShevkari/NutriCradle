package org.example;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {

  DatabaseModel db;
  private Controller controller;
  private PremiumUserMenu premMenu;
  private StandardUserMenu stndMenu;
  private AdminMenu adminMenu;
  private JLabel welcomeLabel;
  private JLabel username;
  private JLabel password;
  private JButton login;
  private JButton exit;
  private JTextField uname;
  private JTextField pwd;
  private String user;
  private String calorieLimit;
  private String proteinIntake;


  public LoginView(Controller controller) {
    super("Login");
    this.controller = controller;
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    welcomeLabel = new JLabel("Welcome to NutriCradle!");
    username = new JLabel("  Username");
    uname = new JTextField(10);
    password = new JLabel("  Password");
    pwd = new JPasswordField(10);
    login = new JButton("Login");
    exit = new JButton("Exit");
  }

  private void setupLayout() {
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    setSize(1650, 1600);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/resources/image.jpg");
    backgroundPanel.setLayout(new BorderLayout());
    add(backgroundPanel, BorderLayout.CENTER);

    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
    controlPanel.setOpaque(false);
    Font textFieldFont = new Font("Serif", Font.PLAIN, 20);
    Font buttonFont = new Font("Serif", Font.BOLD, 20);
    addToPanelWithSpacing(controlPanel, welcomeLabel);
    welcomeLabel.setFont(new Font("Serif", Font.BOLD, 40));
    addToPanelWithSpacing(controlPanel, username);
    username.setFont(new Font("Serif", Font.BOLD, 30));
    addToPanelWithSpacing(controlPanel, uname);
    uname.setFont(textFieldFont);
    addToPanelWithSpacing(controlPanel, password);
    password.setFont(new Font("Serif", Font.BOLD, 30));
    addToPanelWithSpacing(controlPanel, pwd);
    pwd.setFont(textFieldFont);
    addToPanelWithSpacing(controlPanel, login);
    login.setFont(buttonFont);
    addToPanelWithSpacing(controlPanel, exit);
    exit.setFont(buttonFont);

    JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    bottomRightPanel.setOpaque(false);
    bottomRightPanel.add(controlPanel);
    backgroundPanel.add(bottomRightPanel, BorderLayout.SOUTH);
  }

  private void addToPanelWithSpacing(JPanel panel, JComponent component) {
    component.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(component);
    panel.add(Box.createVerticalStrut(15));
  }

  private void attachHandlers() {
    login.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String userType = null;
        try {
          String username = uname.getText();
          String password = pwd.getText();
          userType = controller.login(username, password);
          calorieLimit = controller.getCalorieLimit(username);
          proteinIntake = controller.getProteinIntake(username);
          setUser(username);
          if (userType == null) {
            JOptionPane.showMessageDialog(null, "Invalid username or password", "Error",
                JOptionPane.WARNING_MESSAGE);
          } else {
            JOptionPane.showMessageDialog(null, "Logged in as " + userType, "Success",
                JOptionPane.INFORMATION_MESSAGE);
            if (userType.equals("standard user")) {
              setVisible(false);
              stndMenu = new StandardUserMenu(controller, username, calorieLimit, proteinIntake);
            } else if (userType.equals("premium user")) {
              setVisible(false);
              premMenu = new PremiumUserMenu(controller, username, calorieLimit, proteinIntake);
            } else if (userType.equals("admin")) {
              setVisible(false);
              adminMenu = new AdminMenu(controller);
            }
          }
        } catch (NullPointerException ex) {
          JOptionPane.showMessageDialog(null, "Invalid username or password", "Error",
              JOptionPane.ERROR_MESSAGE);
          ex.printStackTrace();
        }
      }
    });
    exit.addActionListener(e -> System.exit(0));
  }

  private void finalizeSetup() {
    pack();
    revalidate();
    repaint();
    setVisible(true);
  }

  public void setUser(String user) {
    this.user = user;
  }
}