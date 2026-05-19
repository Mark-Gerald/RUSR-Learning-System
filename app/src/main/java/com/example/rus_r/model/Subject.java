package com.example.rus_r.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private String id;
    private String name;
    private String description;
    private String color;
    private long createdAt;
    private long updatedAt;
    private int lessonCount;
    private int quizCount;
    private int flashcardCount;

    // Constructor
    public Subject() {
    }

    public Subject(String id, String name, String description, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.lessonCount = 0;
        this.quizCount = 0;
        this.flashcardCount = 0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public int getQuizCount() {
        return quizCount;
    }

    public void setQuizCount(int quizCount) {
        this.quizCount = quizCount;
    }

    public int getFlashcardCount() {
        return flashcardCount;
    }

    public void setFlashcardCount(int flashcardCount) {
        this.flashcardCount = flashcardCount;
    }
}