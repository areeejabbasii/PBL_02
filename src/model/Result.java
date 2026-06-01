package model;

import java.sql.Timestamp;

public class Result {
    private int id;
    private int userId;
    private int score;
    private int totalQuestions;
    private double percentage;
    private Timestamp examDate;
    
    public Result(int id, int userId, int score, int totalQuestions, double percentage, Timestamp examDate) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.examDate = examDate;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public double getPercentage() { return percentage; }
    public Timestamp getExamDate() { return examDate; }
}