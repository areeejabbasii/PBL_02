package gui;

import model.User;
import service.ResultService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultFrame extends JFrame {
    private int userId;
    private ResultService resultService;
    private User currentUser;
    
    public ResultFrame(int userId) {
        this.userId = userId;
        this.resultService = new ResultService();
        initComponents();
        loadResults();
    }
    
    public ResultFrame(int userId, int score, int totalQuestions, double percentage) {
        this.userId = userId;
        this.resultService = new ResultService();
        initComponents();
        showSingleResult(score, totalQuestions, percentage);
    }
    
    private void initComponents() {
        setTitle("Online Examination System - Results");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        
        // Main Panel - Clean white background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header Panel - Professional blue header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(37, 99, 235));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        JLabel titleLabel = new JLabel("Exam Results");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(37, 99, 235));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Results Table - Clean professional design
        String[] columns = {"Exam ID", "Score", "Total Questions", "Percentage", "Date"};
        JTable resultTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultTable.setRowHeight(35);
        resultTable.setBackground(Color.WHITE);
        resultTable.setForeground(new Color(55, 65, 81));
        resultTable.setSelectionBackground(new Color(219, 234, 254));
        resultTable.setSelectionForeground(new Color(37, 99, 235));
        resultTable.setGridColor(new Color(229, 231, 235));
        resultTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultTable.getTableHeader().setBackground(new Color(249, 250, 251));
        resultTable.getTableHeader().setForeground(new Color(55, 65, 81));
        resultTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void loadResults() {
        List<model.Result> results = resultService.getUserResults(userId);
        
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No results found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] columns = {"Exam ID", "Score", "Total Questions", "Percentage", "Date"};
        Object[][] data = new Object[results.size()][5];
        
        for (int i = 0; i < results.size(); i++) {
            model.Result result = results.get(i);
            data[i][0] = result.getId();
            data[i][1] = result.getScore();
            data[i][2] = result.getTotalQuestions();
            data[i][3] = String.format("%.1f%%", result.getPercentage());
            data[i][4] = result.getExamDate();
        }
        
        JTable table = new JTable(data, columns);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(55, 65, 81));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(new Color(37, 99, 235));
        table.setGridColor(new Color(229, 231, 235));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(249, 250, 251));
        table.getTableHeader().setForeground(new Color(55, 65, 81));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Replace the panel's content
        getContentPane().removeAll();
        
        // Recreate header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(37, 99, 235));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        JLabel titleLabel = new JLabel("Exam Results");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(37, 99, 235));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });
        headerPanel.add(backButton, BorderLayout.EAST);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void showSingleResult(int score, int totalQuestions, double percentage) {
        // Create a clean result display
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        
        // Main result card
        JPanel resultCard = new JPanel(new GridBagLayout());
        resultCard.setBackground(Color.WHITE);
        resultCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
            BorderFactory.createEmptyBorder(50, 50, 50, 50)
        ));
        resultCard.setMaximumSize(new Dimension(500, 400));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        
        // Success icon
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel iconLabel = new JLabel("🎉");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(iconLabel, gbc);
        
        // Title
        gbc.gridy = 1;
        JLabel titleLabel = new JLabel("Exam Completed!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(37, 99, 235));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(titleLabel, gbc);
        
        // Score section
        gbc.gridy = 2; gbc.gridwidth = 1;
        JLabel scoreLabel = new JLabel("Your Score");
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        scoreLabel.setForeground(new Color(107, 114, 128));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(scoreLabel, gbc);
        
        gbc.gridx = 1;
        JLabel scoreValue = new JLabel(score + "/" + totalQuestions);
        scoreValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        scoreValue.setForeground(new Color(34, 197, 94));
        scoreValue.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(scoreValue, gbc);
        
        // Percentage section
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel percentageLabel = new JLabel("Percentage");
        percentageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        percentageLabel.setForeground(new Color(107, 114, 128));
        percentageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(percentageLabel, gbc);
        
        gbc.gridx = 1;
        JLabel percentageValue = new JLabel(String.format("%.1f%%", percentage));
        percentageValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        Color percentageColor = percentage >= 80 ? new Color(34, 197, 94) : 
                               percentage >= 60 ? new Color(249, 115, 22) : 
                               new Color(239, 68, 68);
        percentageValue.setForeground(percentageColor);
        percentageValue.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(percentageValue, gbc);
        
        resultPanel.add(Box.createVerticalGlue());
        resultPanel.add(resultCard);
        resultPanel.add(Box.createVerticalStrut(30));
        
        // Back Button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(37, 99, 235));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(200, 45));
        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });
        resultPanel.add(backButton);
        resultPanel.add(Box.createVerticalGlue());
        
        // Replace the frame's content
        getContentPane().removeAll();
        getContentPane().add(resultPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}