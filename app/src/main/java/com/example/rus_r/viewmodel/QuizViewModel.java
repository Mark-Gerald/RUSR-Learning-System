package com.example.rus_r.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rus_r.model.Question;
import com.example.rus_r.model.Quiz;
import com.example.rus_r.model.QuizAttempt;
import com.example.rus_r.repository.QuizRepository;
import com.example.rus_r.repository.SubjectRepository;

import java.util.List;

public class QuizViewModel extends ViewModel {
    private final QuizRepository quizRepository;
    private final SubjectRepository subjectRepository;
    private LiveData<List<Quiz>> quizzes;
    private LiveData<Quiz> currentQuiz;
    private LiveData<List<Question>> currentQuestions;
    private LiveData<QuizAttempt> currentAttempt;

    public QuizViewModel() {
        this.quizRepository = new QuizRepository();
        this.subjectRepository = new SubjectRepository();
    }

    // Get all quizzes for a subject
    public LiveData<List<Quiz>> getQuizzesBySubject(String subjectId) {
        this.quizzes = quizRepository.getQuizzesBySubject(subjectId);
        return quizzes;
    }

    // Get quiz by ID
    public LiveData<Quiz> getQuizById(String quizId) {
        this.currentQuiz = quizRepository.getQuizById(quizId);
        return currentQuiz;
    }

    // Create quiz
    public LiveData<String> createQuiz(Quiz quiz, String subjectId) {
        LiveData<String> result = quizRepository.createQuiz(quiz);

        // Increment quiz count in subject
        result.observeForever(quizId -> {
            if (quizId != null) {
                subjectRepository.incrementQuizCount(subjectId);
            }
        });

        return result;
    }

    // Update quiz
    public LiveData<Boolean> updateQuiz(Quiz quiz) {
        return quizRepository.updateQuiz(quiz);
    }

    // Delete quiz
    public LiveData<Boolean> deleteQuiz(String quizId, String subjectId) {
        LiveData<Boolean> result = quizRepository.deleteQuiz(quizId);

        // Decrement quiz count in subject
        result.observeForever(success -> {
            if (success) {
                subjectRepository.decrementQuizCount(subjectId);
            }
        });

        return result;
    }

    // Add question to quiz
    public LiveData<Boolean> addQuestion(Question question) {
        return quizRepository.addQuestion(question);
    }

    // Get all questions for a quiz
    public LiveData<List<Question>> getQuestionsByQuiz(String quizId) {
        this.currentQuestions = quizRepository.getQuestionsByQuiz(quizId);
        return currentQuestions;
    }

    // Get question by ID
    public LiveData<Question> getQuestionById(String questionId) {
        return quizRepository.getQuestionById(questionId);
    }

    // Delete question
    public LiveData<Boolean> deleteQuestion(String questionId) {
        return quizRepository.deleteQuestion(questionId);
    }

    // Save quiz attempt
    public LiveData<String> saveQuizAttempt(QuizAttempt attempt) {
        return quizRepository.saveQuizAttempt(attempt);
    }

    // Get quiz attempt
    public LiveData<QuizAttempt> getQuizAttempt(String attemptId) {
        this.currentAttempt = quizRepository.getQuizAttempt(attemptId);
        return currentAttempt;
    }

    // Get all attempts for a quiz
    public LiveData<List<QuizAttempt>> getQuizAttemptsByQuiz(String quizId) {
        return quizRepository.getQuizAttemptsByQuiz(quizId);
    }

    // Update quiz attempt
    public LiveData<Boolean> updateQuizAttempt(QuizAttempt attempt) {
        return quizRepository.updateQuizAttempt(attempt);
    }

    // Calculate hearts based on difficulty
    public int getInitialHearts(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return 5;
            case "medium":
                return 3;
            case "hard":
                return 1;
            default:
                return 3;
        }
    }

    // Refresh quizzes
    public void refreshQuizzes(String subjectId) {
        this.quizzes = quizRepository.getQuizzesBySubject(subjectId);
    }
}