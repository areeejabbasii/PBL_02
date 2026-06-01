package gui;

import model.Question;
import model.User;
import service.ExamService;
import database.QueryManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class ExamFrame extends JFrame {
    private User user;
    private ExamService examService;
    private JLabel questionLabel, timerLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JButton nextButton, prevButton, submitButton;
    private JPanel questionPanel, navigationPanel;
    private Timer timer;
    private int timeRemaining = 1800; // 30 minutes in seconds
    private JLabel questionNumberLabel;
    
    public ExamFrame(User user, ExamService examService) {
        this.user = user;
        this.examService = examService;
        initComponents();
        loadQuestion();
        startTimer();
    }
    
    private void initComponents() {
        setTitle("Online Examination System - Exam");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        // Header Panel - Professional blue header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(37, 99, 235));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("Online Examination");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        timerLabel = new JLabel("Time Remaining: 30:00");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(timerLabel, BorderLayout.EAST);
        
        // Question Panel - Clean white background
        questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 30, 50));
        questionPanel.setBackground(Color.WHITE);
        
        questionNumberLabel = new JLabel();
        questionNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        questionNumberLabel.setForeground(new Color(37, 99, 235));
        
        // Question info panel
        JPanel questionInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        questionInfoPanel.setBackground(Color.WHITE);
        
        JLabel difficultyLabel = new JLabel();
        difficultyLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JLabel categoryLabel = new JLabel();
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categoryLabel.setForeground(new Color(107, 114, 128));
        
        questionInfoPanel.add(difficultyLabel);
        questionInfoPanel.add(Box.createHorizontalStrut(15));
        questionInfoPanel.add(categoryLabel);
        
        JPanel headerInfoPanel = new JPanel(new BorderLayout());
        headerInfoPanel.setBackground(Color.WHITE);
        headerInfoPanel.add(questionNumberLabel, BorderLayout.WEST);
        headerInfoPanel.add(questionInfoPanel, BorderLayout.EAST);
        headerInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        questionLabel.setForeground(new Color(17, 24, 39));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 15));
            optionButtons[i].setBackground(Color.WHITE);
            optionButtons[i].setForeground(new Color(55, 65, 81));
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            
            // Add hover effect
            final int index = i;
            optionButtons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!optionButtons[index].isSelected()) {
                        optionButtons[index].setBackground(new Color(249, 250, 251));
                    }
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!optionButtons[index].isSelected()) {
                        optionButtons[index].setBackground(Color.WHITE);
                    }
                }
            });
            
            buttonGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        
        questionPanel.add(headerInfoPanel, BorderLayout.NORTH);
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        questionPanel.add(optionsPanel, BorderLayout.SOUTH);
        
        // Navigation Panel - Clean design with professional colors
        navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        navigationPanel.setBackground(new Color(249, 250, 251));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        prevButton = new JButton("← Previous");
        prevButton.setBackground(new Color(107, 114, 128));
        prevButton.setForeground(Color.WHITE);
        prevButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        prevButton.setBorderPainted(false);
        prevButton.setFocusPainted(false);
        prevButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prevButton.setPreferredSize(new Dimension(120, 40));
        prevButton.addActionListener(e -> previousQuestion());
        
        // Bookmark button
        JButton bookmarkButton = new JButton("🔖 Bookmark");
        bookmarkButton.setBackground(new Color(168, 85, 247));
        bookmarkButton.setForeground(Color.WHITE);
        bookmarkButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookmarkButton.setBorderPainted(false);
        bookmarkButton.setFocusPainted(false);
        bookmarkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bookmarkButton.setPreferredSize(new Dimension(130, 40));
        bookmarkButton.addActionListener(e -> toggleBookmark());
        
        nextButton = new JButton("Next →");
        nextButton.setBackground(new Color(37, 99, 235));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.setPreferredSize(new Dimension(120, 40));
        nextButton.addActionListener(e -> nextQuestion());
        
        submitButton = new JButton("Submit Exam");
        submitButton.setBackground(new Color(220, 38, 127));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setPreferredSize(new Dimension(140, 40));
        submitButton.addActionListener(e -> submitExam());
        
        navigationPanel.add(prevButton);
        navigationPanel.add(bookmarkButton);
        navigationPanel.add(nextButton);
        navigationPanel.add(submitButton);
        
        add(headerPanel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
    }
    
    private void loadQuestion() {
        Question currentQuestion = examService.getCurrentQuestion();
        if (currentQuestion == null) {
            questionNumberLabel.setText("No questions");
            questionLabel.setText("<html><body style='width: 600px'>No questions loaded from database.</body></html>");
            for (JRadioButton button : optionButtons) {
                button.setText("");
                button.setEnabled(false);
            }
            return;
        }
        for (JRadioButton button : optionButtons) {
            button.setEnabled(true);
        }
        questionNumberLabel.setText("Question " + (examService.getCurrentIndex() + 1) + " of " + examService.getQuestions().size());
            questionLabel.setText("<html><body style='width: 600px; line-height: 1.5;'>" + currentQuestion.getQuestionText() + "</body></html>");
            
            optionButtons[0].setText("A) " + currentQuestion.getOptionA());
            optionButtons[1].setText("B) " + currentQuestion.getOptionB());
            optionButtons[2].setText("C) " + currentQuestion.getOptionC());
            optionButtons[3].setText("D) " + currentQuestion.getOptionD());
            
            // Update question info (difficulty and category)
            updateQuestionInfo(currentQuestion);
            
            // Set selected button based on user's previous answer
            char userAnswer = currentQuestion.getUserAnswer();
            if (userAnswer != ' ') {
                switch (userAnswer) {
                    case 'A': 
                        optionButtons[0].setSelected(true);
                        optionButtons[0].setBackground(new Color(219, 234, 254));
                        break;
                    case 'B': 
                        optionButtons[1].setSelected(true);
                        optionButtons[1].setBackground(new Color(219, 234, 254));
                        break;
                    case 'C': 
                        optionButtons[2].setSelected(true);
                        optionButtons[2].setBackground(new Color(219, 234, 254));
                        break;
                    case 'D': 
                        optionButtons[3].setSelected(true);
                        optionButtons[3].setBackground(new Color(219, 234, 254));
                        break;
                }
            } else {
                buttonGroup.clearSelection();
                for (JRadioButton button : optionButtons) {
                    button.setBackground(Color.WHITE);
                }
            }
    }
    
    private void updateQuestionInfo(Question question) {
        // Find the difficulty and category labels in the question info panel
        JPanel questionInfoPanel = (JPanel) ((JPanel) questionPanel.getComponent(0)).getComponent(1);
        JLabel difficultyLabel = (JLabel) questionInfoPanel.getComponent(0);
        JLabel categoryLabel = (JLabel) questionInfoPanel.getComponent(2);
        
        // Set difficulty with color coding
        difficultyLabel.setText(question.getDifficulty().toUpperCase());
        switch (question.getDifficulty().toLowerCase()) {
            case "easy":
                difficultyLabel.setForeground(new Color(34, 197, 94)); // Green
                break;
            case "medium":
                difficultyLabel.setForeground(new Color(249, 115, 22)); // Orange
                break;
            case "hard":
                difficultyLabel.setForeground(new Color(239, 68, 68)); // Red
                break;
        }
        
        // Set category
        categoryLabel.setText(question.getCategory());
    }
    
    private void toggleBookmark() {
        examService.toggleBookmark(examService.getCurrentIndex());
        Question currentQuestion = examService.getCurrentQuestion();
        
        // Update bookmark button appearance
        JButton bookmarkButton = (JButton) navigationPanel.getComponent(1);
        if (currentQuestion.isBookmarked()) {
            bookmarkButton.setText("⭐ Bookmarked");
            bookmarkButton.setBackground(new Color(251, 191, 36)); // Yellow
            bookmarkButton.setForeground(new Color(17, 24, 39));
        } else {
            bookmarkButton.setText("🔖 Bookmark");
            bookmarkButton.setBackground(new Color(168, 85, 247)); // Purple
            bookmarkButton.setForeground(Color.WHITE);
        }
        
        JOptionPane.showMessageDialog(this, 
            currentQuestion.isBookmarked() ? "Question bookmarked!" : "Bookmark removed!",
            "Bookmark", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void nextQuestion() {
        if (examService.getCurrentIndex() < examService.getQuestions().size() - 1) {
            // Save current answer before moving
            saveCurrentAnswer();
            examService.nextQuestion();
            loadQuestion();
        } else {
            JOptionPane.showMessageDialog(this, "This is the last question!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void previousQuestion() {
        if (examService.getCurrentIndex() > 0) {
            // Save current answer before moving
            saveCurrentAnswer();
            examService.previousQuestion();
            loadQuestion();
        } else {
            JOptionPane.showMessageDialog(this, "This is the first question!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void saveCurrentAnswer() {
        char selectedOption = ' ';
        if (optionButtons[0].isSelected()) selectedOption = 'A';
        else if (optionButtons[1].isSelected()) selectedOption = 'B';
        else if (optionButtons[2].isSelected()) selectedOption = 'C';
        else if (optionButtons[3].isSelected()) selectedOption = 'D';
        
        examService.saveAnswer(selectedOption);
    }
    
    private void submitExam() {
        int confirm = JOptionPane.showConfirmDialog(this,
             "Are you sure you want to submit the exam?",
             "Submit Exam", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Save current answer
            saveCurrentAnswer();
            
            // Stop timer
            if (timer != null) {
                timer.cancel();
            }
            
            // Calculate score
            int score = examService.calculateScore();
            int totalMarks = examService.getTotalMarks();
            double percentage = examService.getPercentage();
            
            // Save result
            QueryManager.saveResult(user.getId(), score, examService.getQuestions().size(), percentage);
            
            // Show result
            ResultFrame resultFrame = new ResultFrame(user.getId(), score, examService.getQuestions().size(), percentage);
            resultFrame.setCurrentUser(user);
            resultFrame.setVisible(true);
            dispose();
        }
    }
    
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining <= 0) {
                    timer.cancel();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(ExamFrame.this, "Time is up! Submitting exam...", "Time Expired", JOptionPane.WARNING_MESSAGE);
                        submitExam();
                    });
                    return;
                }
                
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                SwingUtilities.invokeLater(() -> {
                    timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
                });
                timeRemaining--;
            }
        }, 0, 1000);
    }
}