package gui;

import model.User;
import service.ExamService;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private User user;
    
    public DashboardFrame(User user) {
        this.user = user;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Online Examination System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Main Panel - Clean white background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header Panel - Professional blue header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(37, 99, 235));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFullName());
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JButton logoutButton = new JButton("Sign Out");
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(new Color(37, 99, 235));
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> logout());
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        // Center Panel with Cards - Clean white background
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // First Row - Main Actions
        gbc.gridy = 0;
        
        // Take Exam Card
        gbc.gridx = 0;
        JPanel examCard = createCard("Take Examination",
                                    "Start your online exam",
                                    new Color(34, 197, 94), // Professional green
                                    "📝");
        examCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startExam();
            }
        });
        centerPanel.add(examCard, gbc);
        
        // View Results Card
        gbc.gridx = 1;
        JPanel resultsCard = createCard("View Results",
                                       "Check your exam history",
                                       new Color(249, 115, 22), // Professional orange
                                       "📊");
        resultsCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewResults();
            }
        });
        centerPanel.add(resultsCard, gbc);
        

        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createCard(String title, String description, Color accentColor, String icon) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        card.setPreferredSize(new Dimension(280, 180));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(249, 250, 251));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accentColor, 2),
                    BorderFactory.createEmptyBorder(29, 29, 29, 29)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                    BorderFactory.createEmptyBorder(30, 30, 30, 30)
                ));
            }
        });
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(iconLabel, BorderLayout.NORTH);
        
        // Text content
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(17, 24, 39));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(107, 114, 128));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        textPanel.add(titleLabel);
        textPanel.add(descLabel);
        
        contentPanel.add(textPanel, BorderLayout.CENTER);
        card.add(contentPanel, BorderLayout.CENTER);
        
        return card;
    }
    

    
    private void startExam() {
        int confirm = JOptionPane.showConfirmDialog(this,
             "Are you ready to start the exam?\nYou will have 30 minutes to complete.",
             "Start Exam", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            ExamService examService = new ExamService();
            if (examService.getQuestions().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Database mein koi sawal nahi mila.\n\n"
                                + "SSMS mein database.sql chalain (sample questions INSERT wala hissa).",
                        "No Questions", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new ExamFrame(user, examService).setVisible(true);
            dispose();
        }
    }
    
    private void viewResults() {
        ResultFrame resultFrame = new ResultFrame(user.getId());
        resultFrame.setCurrentUser(user);
        resultFrame.setVisible(true);
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to sign out?",
                                                     "Sign Out", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}