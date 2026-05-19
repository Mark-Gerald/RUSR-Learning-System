package com.example.rus_r.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String id;
    private String quizId;
    private String questionText;
    private String questionType; // "multiple_choice", "identification", "typing", "voice"
    private List<String> options; // For multiple choice
    private String correctAnswer;
    private int questionNumber;
    private String difficulty; // "easy", "medium", "hard"

    // Constructor
    public Question() {
        this.options = new ArrayList<>();
    }

    public Question(String id, String quizId, String questionText, String questionType,
                    String correctAnswer, int questionNumber) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.correctAnswer = correctAnswer;
        this.questionNumber = questionNumber;
        this.options = new ArrayList<>();
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

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void addOption(String option) {
        this.options.add(option);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}