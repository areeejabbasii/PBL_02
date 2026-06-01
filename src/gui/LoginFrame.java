package gui;

import service.LoginService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private LoginService loginService;
    
    public LoginFrame() {
        loginService = new LoginService();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Online Examination System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Clean white background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridBagLayout());
        setContentPane(mainPanel);
        
        // Login Card Panel - Clean white with subtle border
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        cardPanel.setLayout(new GridBagLayout());
        cardPanel.setPreferredSize(new Dimension(380, 400));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 10, 15, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Online Examination System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(37, 99, 235)); // Professional blue
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        cardPanel.add(titleLabel, gbc);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Please sign in to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(107, 114, 128));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 25, 10);
        cardPanel.add(subtitleLabel, gbc);
        
        // Username
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 5, 10);
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(new Color(55, 65, 81));
        cardPanel.add(userLabel, gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBackground(Color.WHITE);
        usernameField.setForeground(new Color(55, 65, 81));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        cardPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(15, 10, 5, 10);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(new Color(55, 65, 81));
        cardPanel.add(passLabel, gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(new Color(55, 65, 81));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        cardPanel.add(passwordField, gbc);
        
        // Login Button - Professional blue
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 10, 10, 10);
        JButton loginButton = new JButton("Sign In");
        loginButton.setBackground(new Color(37, 99, 235)); // Professional blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(0, 40));
        loginButton.addActionListener(e -> login());
        cardPanel.add(loginButton, gbc);
        
        // Register Button - Clean secondary style
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton registerButton = new JButton("Create New Account");
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(37, 99, 235));
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(37, 99, 235), 1));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setPreferredSize(new Dimension(0, 40));
        registerButton.addActionListener(e -> openRegister());
        cardPanel.add(registerButton, gbc);
        
        mainPanel.add(cardPanel);
        
        // Enter key to login
        getRootPane().setDefaultButton(loginButton);
    }
    
    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (loginService.authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome " + username,
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardFrame(loginService.getCurrentUser()).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openRegister() {
        new RegisterFrame().setVisible(true);
        dispose();
    }
}