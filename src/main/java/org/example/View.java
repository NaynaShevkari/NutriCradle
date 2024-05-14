package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {

  private Controller controller;
  private DatabaseModel db;
  private LoginView loginview;
  private JPanel panel;
  private JButton login;
  private JButton exit;
  private JTextField url;
  private JTextField uname;
  private JPasswordField pwd;

  public View() {
    super("Connect to Database");
    db = new DatabaseModel();
    controller = new Controller(db, this);
    initComponents();
    setupLayout();
    attachHandlers();
    finalizeSetup();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


  private void initComponents() {
    setLayout(new BorderLayout());
    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    url = new JTextField("Enter Database URL", 15);
    url.setForeground(Color.GRAY);
    url.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        if (url.getText().equals("Enter Database URL")) {
          url.setText("");
          url.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (url.getText().isEmpty()) {
          url.setText("Enter Database URL");
          url.setForeground(Color.GRAY);
        }
      }
    });
    uname = new JTextField("Enter MySQL Username", 15);
    uname.setForeground(Color.GRAY);
    uname.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        if (uname.getText().equals("Enter MySQL Username")) {
          uname.setText("");
          uname.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (uname.getText().isEmpty()) {
          uname.setText("Enter MySQL Username");
          uname.setForeground(Color.GRAY);
        }
      }
    });
    pwd = new JPasswordField("Enter MySQL password", 15);
    pwd.setEchoChar((char) 0);
    pwd.setForeground(Color.GRAY);
    pwd.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        if (pwd.getText().equals("Enter MySQL password")) {
          pwd.setText("");
          pwd.setEchoChar('•');
          pwd.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (pwd.getText().isEmpty()) {
          pwd.setText("Enter MySQL password");
          pwd.setForeground(Color.GRAY);
        }
      }
    });
    login = new JButton("Connect to Database");
    exit = new JButton("Exit");
  }

  private void setupLayout() {
    setSize(750, 700);
    setLocation(150, 300);
    this.setLayout(new FlowLayout(25, 25, 25));
    Font textFieldFont = new Font("Serif", Font.PLAIN, 20);
    Font buttonFont = new Font("Serif", Font.BOLD, 20);

    addToPanelWithSpacing(panel, url);
//    panel.add(uname);
    url.setFont(textFieldFont);
    addToPanelWithSpacing(panel, uname);
//    panel.add(uname);
    uname.setFont(textFieldFont);
    addToPanelWithSpacing(panel, pwd);
//    panel.add(pwd);
    pwd.setFont(textFieldFont);
//    panel.add(login);
    addToPanelWithSpacing(panel, login);
    login.setFont(buttonFont);
//    panel.add(exit);
    addToPanelWithSpacing(panel, exit);
    exit.setFont(buttonFont);
    this.add(panel);
  }

  private void addToPanelWithSpacing(JPanel panel, JComponent component) {
    component.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(component);
    panel.add(Box.createVerticalStrut(20));
  }


  private void attachHandlers() {
    login.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String urlText = url.getText();
        String username = uname.getText();
        String password = pwd.getText();
        if (username.isEmpty() || password.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Username and password cannot be empty", "Error",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
            if(controller.dbConnect(urlText, username, password)) {
              loginview = new LoginView(controller);
              setVisible(false);
            }
            else {
              JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error",
                JOptionPane.INFORMATION_MESSAGE);
            }
        }
      }
    });
    exit.addActionListener(e -> System.exit(0));
  }

  private void finalizeSetup() {
    pack();
    setVisible(true);
    login.requestFocus();
  }
}
