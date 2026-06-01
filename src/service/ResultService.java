package service;

import database.QueryManager;
import model.Result;
import java.util.List;

public class ResultService {
    
    public boolean saveResult(int userId, int score, int totalQuestions, double percentage) {
        return QueryManager.saveResult(userId, score, totalQuestions, percentage);
    }
    
    public List<Result> getUserResults(int userId) {
        return QueryManager.getUserResults(userId);
    }
}