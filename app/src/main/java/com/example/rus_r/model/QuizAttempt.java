package com.example.rus_r.model;

import java.util.HashMap;
import java.util.Map;

public class QuizAttempt {
    private String id;
    private String quizId;
    private long attemptTime;
    private int correctAnswers;
    private int totalQuestions;
    private int score; // Percentage
    private int heartsRemaining;
    private boolean completed;
    private Map<String, String> userAnswers; // questionId -> userAnswer

    // Constructor
    public QuizAttempt() {
        this.userAnswers = new HashMap<>();
    }

    public QuizAttempt(String id, String quizId, int totalQuestions, int initialHearts) {
        this.id = id;
        this.quizId = quizId;
        this.attemptTime = System.currentTimeMillis();
        this.totalQuestions = totalQuestions;
        this.correctAnswers = 0;
        this.score = 0;
        this.heartsRemaining = initialHearts;
        this.completed = false;
        this.userAnswers = new HashMap<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public long getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(long attemptTime) {
        this.attemptTime = attemptTime;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHeartsRemaining() {
        return heartsRemaining;
    }

    public void setHeartsRemaining(int heartsRemaining) {
        this.heartsRemaining = heartsRemaining;
    }

    public void removeHeart() {
        if (this.heartsRemaining > 0) {
            this.heartsRemaining--;
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Map<String, String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Map<String, String> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public void addUserAnswer(String questionId, String answer) {
        this.userAnswers.put(questionId, answer);
    }
}