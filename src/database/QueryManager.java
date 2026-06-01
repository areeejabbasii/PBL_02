package database;

import model.*;
import java.sql.*;
import java.util.*;

public class QueryManager {
    
    // User operations
    public static boolean validateUser(String username, String password) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.err.println("Database connection failed!");
            return false;
        }
        
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public static User getUser(String username) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.err.println("Database connection failed!");
            return null;
        }
        
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String fullName = rs.getString("full_name");
                String role = rs.getString("role");
                int totalExamsTaken = rs.getInt("total_exams_taken");
                double averageScore = rs.getDouble("average_score");
                
                User user = new User(id, username, rs.getString("password"), email, fullName, role);
                user.setTotalExamsTaken(totalExamsTaken);
                user.setAverageScore(averageScore);
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean registerUser(String username, String password, String email, String fullName) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.err.println("Database connection failed!");
            return false;
        }
        
        // Validate inputs
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
            System.err.println("All fields are required!");
            return false;
        }
        
        String query = "INSERT INTO users (username, password, email, full_name, role) VALUES (?, ?, ?, ?, 'student')";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username.trim());
            pstmt.setString(2, password.trim());
            pstmt.setString(3, email.trim());
            pstmt.setString(4, fullName.trim());
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ User registered successfully: " + username);
                return true;
            } else {
                System.err.println("✗ No rows affected during registration");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error registering user: " + e.getMessage());
            System.err.println("✗ SQL State: " + e.getSQLState());
            System.err.println("✗ Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
    }
    
    // Question operations
    public static List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.err.println("Database connection failed!");
            return questions;
        }
        
        String query = "SELECT * FROM questions";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("question_text");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");
                char correctAnswer = rs.getString("correct_answer").charAt(0);
                String subject = rs.getString("subject");
                String category = rs.getString("category");
                String difficulty = rs.getString("difficulty");
                int marks = rs.getInt("marks");
                if (category == null) category = "General";
                if (difficulty == null) difficulty = "Medium";

                Question question = new Question(id, questionText, optionA, optionB, optionC, optionD,
                        correctAnswer, subject, category, difficulty, marks, 60, "");
                questions.add(question);
            }
        } catch (SQLException e) {
            System.err.println("Error getting questions: " + e.getMessage());
            e.printStackTrace();
        }
        return questions;
    }
    
    // Result operations
    public static boolean saveResult(int userId, int score, int totalQuestions, double percentage) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.err.println("Database connection failed!");
            return false;
        }
        
        String query = "INSERT INTO results (user_id, score, total_questions, percentage, time_taken, questions_attempted, correct_answers, wrong_answers) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, score);
            pstmt.setInt(3, totalQuestions);
            pstmt.setDouble(4, percentage);
            pstmt.setInt(5, 0);
            pstmt.setInt(6, totalQuestions);
            pstmt.setInt(7, score);
            pstmt.setInt(8, totalQuestions - score);
            pstmt.executeUpdate();
            
            updateUserStats(userId);
            return true;
        } catch (SQLException e) {
            System.err.println("Error saving result: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<Result> getUserResults(int userId) {
        List<Result> results = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.err.println("Database connection failed!");
            return results;
        }
        
        String query = "SELECT * FROM results WHERE user_id = ? ORDER BY exam_date DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int score = rs.getInt("score");
                int totalQuestions = rs.getInt("total_questions");
                double percentage = rs.getDouble("percentage");
                Timestamp examDate = rs.getTimestamp("exam_date");
                
                Result result = new Result(id, userId, score, totalQuestions, percentage, examDate);
                results.add(result);
            }
        } catch (SQLException e) {
            System.err.println("Error getting results: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }
    
    private static void updateUserStats(int userId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.err.println("Database connection failed!");
            return;
        }
        
        String query = "UPDATE users SET total_exams_taken = total_exams_taken + 1, " +
                      "average_score = (SELECT AVG(percentage) FROM results WHERE user_id = ?) " +
                      "WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating user stats: " + e.getMessage());
            e.printStackTrace();
        }
    }
}