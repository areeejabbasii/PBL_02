package model;

public class Question {
    private int id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char correctAnswer;
    private String subject;
    private String category;
    private String difficulty;
    private int marks;
    private int timeLimit; // seconds
    private String explanation;
    private char userAnswer;
    private boolean answered;
    private boolean bookmarked;
    private int timeTaken; // seconds taken by user
    
    public Question(int id, String questionText, String optionA, String optionB,
                    String optionC, String optionD, char correctAnswer, String subject, int marks) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.subject = subject;
        this.marks = marks;
        this.category = "General";
        this.difficulty = "Medium";
        this.timeLimit = 60;
        this.explanation = "";
        this.answered = false;
        this.bookmarked = false;
        this.userAnswer = ' ';
        this.timeTaken = 0;
    }
    
    // Enhanced constructor
    public Question(int id, String questionText, String optionA, String optionB,
                    String optionC, String optionD, char correctAnswer, String subject, 
                    String category, String difficulty, int marks, int timeLimit, String explanation) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.subject = subject;
        this.category = category;
        this.difficulty = difficulty;
        this.marks = marks;
        this.timeLimit = timeLimit;
        this.explanation = explanation;
        this.answered = false;
        this.bookmarked = false;
        this.userAnswer = ' ';
        this.timeTaken = 0;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public String getQuestionText() { return questionText; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public char getCorrectAnswer() { return correctAnswer; }
    public String getSubject() { return subject; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public int getMarks() { return marks; }
    public int getTimeLimit() { return timeLimit; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public char getUserAnswer() { return userAnswer; }
    public void setUserAnswer(char userAnswer) {
        this.userAnswer = userAnswer;
        this.answered = true;
    }
    public boolean isAnswered() { return answered; }
    public boolean isBookmarked() { return bookmarked; }
    public void setBookmarked(boolean bookmarked) { this.bookmarked = bookmarked; }
    public int getTimeTaken() { return timeTaken; }
    public void setTimeTaken(int timeTaken) { this.timeTaken = timeTaken; }
    
    // Utility methods
    public boolean isCorrect() {
        return answered && userAnswer == correctAnswer;
    }
    
    public String getDifficultyColor() {
        switch (difficulty.toLowerCase()) {
            case "easy": return "#4CAF50"; // Green
            case "medium": return "#FF9800"; // Orange
            case "hard": return "#F44336"; // Red
            default: return "#9E9E9E"; // Gray
        }
    }
    
    public String getCategoryIcon() {
        switch (category.toLowerCase()) {
            case "programming": return "💻";
            case "mathematics": return "🔢";
            case "science": return "🔬";
            case "geography": return "🌍";
            case "history": return "📚";
            default: return "📝";
        }
    }
}