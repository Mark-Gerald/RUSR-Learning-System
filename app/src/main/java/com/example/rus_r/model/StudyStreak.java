package com.example.rus_r.model;

public class StudyStreak {
    private String id;
    private String subjectId;
    private int currentStreak; // Days
    private int longestStreak; // Days
    private long lastStudyDate; // Timestamp
    private long createdAt;

    // Constructor
    public StudyStreak() {
    }

    public StudyStreak(String id, String subjectId) {
        this.id = id;
        this.subjectId = subjectId;
        this.currentStreak = 0;
        this.longestStreak = 0;
        this.lastStudyDate = 0;
        this.createdAt = System.currentTimeMillis();
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

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void incrementStreak() {
        this.currentStreak++;
        if (this.currentStreak > this.longestStreak) {
            this.longestStreak = this.currentStreak;
        }
    }

    public void resetStreak() {
        this.currentStreak = 0;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public long getLastStudyDate() {
        return lastStudyDate;
    }

    public void setLastStudyDate(long lastStudyDate) {
        this.lastStudyDate = lastStudyDate;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}