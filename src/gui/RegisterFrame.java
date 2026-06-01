package gui;

import service.LoginService;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField, emailField, fullNameField;
    private JPasswordField passwordField, confirmPasswordField;
    private LoginService loginService;
    
    public RegisterFrame() {
        loginService = new LoginService();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Online Examination System - Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Clean white background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);
        
        // Card Panel - Clean white with subtle border
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        cardPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 10, 12, 10);
        
        // Title - Professional blue
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(37, 99, 235));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 30, 10);
        cardPanel.add(titleLabel, gbc);
        
        // Form fields with clean design
        gbc.insets = new Insets(8, 10, 8, 10);
        addField(cardPanel, gbc, "Full Name:", fullNameField = new JTextField(15), 1);
        addField(cardPanel, gbc, "Username:", usernameField = new JTextField(15), 2);
        addField(cardPanel, gbc, "Email:", emailField = new JTextField(15), 3);
        addField(cardPanel, gbc, "Password:", passwordField = new JPasswordField(15), 4);
        addField(cardPanel, gbc, "Confirm Password:", confirmPasswordField = new JPasswordField(15), 5);
        
        // Register Button - Professional blue
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 10, 10, 10);
        JButton registerButton = new JButton("Create Account");
        registerButton.setBackground(new Color(37, 99, 235));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setPreferredSize(new Dimension(0, 40));
        registerButton.addActionListener(e -> register());
        cardPanel.add(registerButton, gbc);
        
        // Back to Login - Clean secondary style
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton backButton = new JButton("Back to Sign In");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(37, 99, 235));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(37, 99, 235), 1));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(0, 40));
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        cardPanel.add(backButton, gbc);
        
        mainPanel.add(cardPanel);
    }
    
    private void addField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jLabel.setForeground(new Color(55, 65, 81));
        panel.add(jLabel, gbc);
        
        gbc.gridx = 1;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(55, 65, 81));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        panel.add(field, gbc);
    }
    
    private void register() {
        String fullName = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        
        // Check empty fields
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate username length
        if (username.length() < 3 || username.length() > 20) {
            JOptionPane.showMessageDialog(this, "Username must be 3-20 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate password length
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate email format
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check password match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Try to register
        if (loginService.register(username, password, email, fullName)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed! Username may already exist or database error occurred.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}