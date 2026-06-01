package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String role;
    private int totalExamsTaken;
    private double averageScore;
    private String profilePicture;
    
    public User(int id, String username, String password, String email, String fullName, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.totalExamsTaken = 0;
        this.averageScore = 0.0;
        this.profilePicture = "default.png";
    }
    
    // Enhanced constructor with statistics
    public User(int id, String username, String password, String email, String fullName, String role, 
                int totalExamsTaken, double averageScore, String profilePicture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.totalExamsTaken = totalExamsTaken;
        this.averageScore = averageScore;
        this.profilePicture = profilePicture != null ? profilePicture : "default.png";
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    // New enhanced getters and setters
    public int getTotalExamsTaken() { return totalExamsTaken; }
    public void setTotalExamsTaken(int totalExamsTaken) { this.totalExamsTaken = totalExamsTaken; }
    public double getAverageScore() { return averageScore; }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }
    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
    
    // Utility methods
    public void updateStatistics(int newScore) {
        this.totalExamsTaken++;
        this.averageScore = ((this.averageScore * (this.totalExamsTaken - 1)) + newScore) / this.totalExamsTaken;
    }
    
    public String getPerformanceLevel() {
        if (averageScore >= 90) return "Excellent";
        else if (averageScore >= 80) return "Good";
        else if (averageScore >= 70) return "Average";
        else if (averageScore >= 60) return "Below Average";
        else return "Needs Improvement";
    }
}