package com.example.rus_r.model;

public class UserProgress {
    private String id;
    private String subjectId;
    private int totalLessonsViewed;
    private int totalQuizzesTaken;
    private int totalFlashcardsReviewed;
    private double averageQuizScore;
    private long lastStudyTime;
    private int currentStreak; // Days of consecutive study
    private int longestStreak; // Longest streak ever
    private long totalStudyTime; // In milliseconds

    // Constructor
    public UserProgress() {
    }

    public UserProgress(String id, String subjectId) {
        this.id = id;
        this.subjectId = subjectId;
        this.totalLessonsViewed = 0;
        this.totalQuizzesTaken = 0;
        this.totalFlashcardsReviewed = 0;
        this.averageQuizScore = 0;
        this.lastStudyTime = System.currentTimeMillis();
        this.currentStreak = 0;
        this.longestStreak = 0;
        this.totalStudyTime = 0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getTotalLessonsViewed() {
        return totalLessonsViewed;
    }

    public void setTotalLessonsViewed(int totalLessonsViewed) {
        this.totalLessonsViewed = totalLessonsViewed;
    }

    public void incrementLessonsViewed() {
        this.totalLessonsViewed++;
    }

    public int getTotalQuizzesTaken() {
        return totalQuizzesTaken;
    }

    public void setTotalQuizzesTaken(int totalQuizzesTaken) {
        this.totalQuizzesTaken = totalQuizzesTaken;
    }

    public void incrementQuizzesTaken() {
        this.totalQuizzesTaken++;
    }

    public int getTotalFlashcardsReviewed() {
        return totalFlashcardsReviewed;
    }

    public void setTotalFlashcardsReviewed(int totalFlashcardsReviewed) {
        this.totalFlashcardsReviewed = totalFlashcardsReviewed;
    }

    public void incrementFlashcardsReviewed() {
        this.totalFlashcardsReviewed++;
    }

    public double getAverageQuizScore() {
        return averageQuizScore;
    }

    public void setAverageQuizScore(double averageQuizScore) {
        this.averageQuizScore = averageQuizScore;
    }

    public long getLastStudyTime() {
        return lastStudyTime;
    }

    public void setLastStudyTime(long lastStudyTime) {
        this.lastStudyTime = lastStudyTime;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public long getTotalStudyTime() {
        return totalStudyTime;
    }

    public void setTotalStudyTime(long totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }

    public void addStudyTime(long milliseconds) {
        this.totalStudyTime += milliseconds;
    }
}