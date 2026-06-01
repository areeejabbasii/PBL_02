package service;

import model.Question;
import database.QueryManager;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ExamService {
    private List<Question> questions;
    private int currentIndex;
    private int score;
    private long examStartTime;
    private int totalTimeTaken;
    
    public ExamService() {
        loadQuestions();
        currentIndex = 0;
        score = 0;
        examStartTime = System.currentTimeMillis();
        totalTimeTaken = 0;
    }
    
    private void loadQuestions() {
        questions = QueryManager.getAllQuestions();
        // Shuffle questions for variety
        Collections.shuffle(questions);
    }
    
    
    public List<Question> getQuestions() {
        return questions;
    }
    
    public Question getCurrentQuestion() {
        if (questions != null && currentIndex < questions.size()) {
            return questions.get(currentIndex);
        }
        return null;
    }
    
    public void nextQuestion() {
        if (currentIndex < questions.size() - 1) {
            currentIndex++;
        }
    }
    
    public void previousQuestion() {
        if (currentIndex > 0) {
            currentIndex--;
        }
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }
    
    public void saveAnswer(char answer) {
        if (currentIndex < questions.size()) {
            Question currentQ = questions.get(currentIndex);
            currentQ.setUserAnswer(answer);
            
            // Auto-save progress (in a real app, this would save to database)
            autoSaveProgress();
        }
    }
    
    private void autoSaveProgress() {
        // This would typically save current progress to database
        // For now, we'll just track it in memory
        System.out.println("Progress auto-saved at question " + (currentIndex + 1));
    }
    
    public int calculateScore() {
        score = 0;
        for (Question q : questions) {
            if (q.getUserAnswer() == q.getCorrectAnswer()) {
                score += q.getMarks();
            }
        }
        return score;
    }
    
    public int getTotalMarks() {
        int total = 0;
        for (Question q : questions) {
            total += q.getMarks();
        }
        return total;
    }
    
    public double getPercentage() {
        return (calculateScore() * 100.0) / getTotalMarks();
    }
    
    public boolean isExamCompleted() {
        for (Question q : questions) {
            if (!q.isAnswered()) {
                return false;
            }
        }
        return true;
    }
    
    public int getTotalTimeTaken() {
        return (int) ((System.currentTimeMillis() - examStartTime) / 1000);
    }
    
    public int getQuestionsAttempted() {
        int attempted = 0;
        for (Question q : questions) {
            if (q.isAnswered()) {
                attempted++;
            }
        }
        return attempted;
    }
    
    public int getCorrectAnswers() {
        int correct = 0;
        for (Question q : questions) {
            if (q.isAnswered() && q.getUserAnswer() == q.getCorrectAnswer()) {
                correct++;
            }
        }
        return correct;
    }
    
    public int getWrongAnswers() {
        int wrong = 0;
        for (Question q : questions) {
            if (q.isAnswered() && q.getUserAnswer() != q.getCorrectAnswer()) {
                wrong++;
            }
        }
        return wrong;
    }
    
    public List<Question> getBookmarkedQuestions() {
        List<Question> bookmarked = new ArrayList<>();
        for (Question q : questions) {
            if (q.isBookmarked()) {
                bookmarked.add(q);
            }
        }
        return bookmarked;
    }
    
    public void toggleBookmark(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < questions.size()) {
            Question q = questions.get(questionIndex);
            q.setBookmarked(!q.isBookmarked());
        }
    }
    
    public String getExamSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Exam Summary:\n");
        summary.append("Questions Attempted: ").append(getQuestionsAttempted()).append("/").append(questions.size()).append("\n");
        summary.append("Correct Answers: ").append(getCorrectAnswers()).append("\n");
        summary.append("Wrong Answers: ").append(getWrongAnswers()).append("\n");
        summary.append("Score: ").append(calculateScore()).append("/").append(getTotalMarks()).append("\n");
        summary.append("Percentage: ").append(String.format("%.2f", getPercentage())).append("%\n");
        summary.append("Time Taken: ").append(formatTime(getTotalTimeTaken()));
        return summary.toString();
    }
    
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}