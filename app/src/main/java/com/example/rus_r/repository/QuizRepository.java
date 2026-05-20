package com.example.rus_r.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rus_r.model.Question;
import com.example.rus_r.model.Quiz;
import com.example.rus_r.model.QuizAttempt;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuizRepository {
    private final FirebaseFirestore db;
    private final String QUIZZES_COLLECTION = "quizzes";
    private final String QUESTIONS_COLLECTION = "questions";
    private final String QUIZ_ATTEMPTS_COLLECTION = "quiz_attempts";

    public QuizRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    // Create a new quiz
    public LiveData<String> createQuiz(Quiz quiz) {
        MutableLiveData<String> result = new MutableLiveData<>();

        if (quiz.getId() == null) {
            quiz.setId(UUID.randomUUID().toString());
        }

        final String quizId = quiz.getId();

        db.collection(QUIZZES_COLLECTION)
                .document(quizId)
                .set(quiz)
                .addOnSuccessListener(aVoid -> result.setValue(quizId))
                .addOnFailureListener(e -> result.setValue(null));

        return result;
    }

    // Get all quizzes for a subject
    public LiveData<List<Quiz>> getQuizzesBySubject(String subjectId) {
        MutableLiveData<List<Quiz>> quizzes = new MutableLiveData<>();

        db.collection(QUIZZES_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .orderBy("createdAt")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        quizzes.setValue(new ArrayList<>());
                        return;
                    }

                    if (value != null) {
                        List<Quiz> quizList = value.toObjects(Quiz.class);
                        quizzes.setValue(quizList);
                    }
                });

        return quizzes;
    }

    // Get quiz by ID
    public LiveData<Quiz> getQuizById(String quizId) {
        MutableLiveData<Quiz> quiz = new MutableLiveData<>();

        db.collection(QUIZZES_COLLECTION)
                .document(quizId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        quiz.setValue(null);
                        return;
                    }

                    if (value != null && value.exists()) {
                        quiz.setValue(value.toObject(Quiz.class));
                    }
                });

        return quiz;
    }

    // Update quiz
    public LiveData<Boolean> updateQuiz(Quiz quiz) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        quiz.setUpdatedAt(System.currentTimeMillis());

        db.collection(QUIZZES_COLLECTION)
                .document(quiz.getId())
                .update(
                        "title", quiz.getTitle(),
                        "description", quiz.getDescription(),
                        "difficulty", quiz.getDifficulty(),
                        "totalQuestions", quiz.getTotalQuestions(),
                        "updatedAt", quiz.getUpdatedAt()
                )
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Delete quiz
    public LiveData<Boolean> deleteQuiz(String quizId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(QUIZZES_COLLECTION)
                .document(quizId)
                .delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Add question to quiz
    public LiveData<Boolean> addQuestion(Question question) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        if (question.getId() == null) {
            question.setId(UUID.randomUUID().toString());
        }

        db.collection(QUESTIONS_COLLECTION)
                .document(question.getId())
                .set(question)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Get all questions for a quiz
    public LiveData<List<Question>> getQuestionsByQuiz(String quizId) {
        MutableLiveData<List<Question>> questions = new MutableLiveData<>();

        db.collection(QUESTIONS_COLLECTION)
                .whereEqualTo("quizId", quizId)
                .orderBy("questionNumber")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        questions.setValue(new ArrayList<>());
                        return;
                    }

                    if (value != null) {
                        List<Question> questionList = value.toObjects(Question.class);
                        questions.setValue(questionList);
                    }
                });

        return questions;
    }

    // Get question by ID
    public LiveData<Question> getQuestionById(String questionId) {
        MutableLiveData<Question> question = new MutableLiveData<>();

        db.collection(QUESTIONS_COLLECTION)
                .document(questionId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        question.setValue(null);
                        return;
                    }

                    if (value != null && value.exists()) {
                        question.setValue(value.toObject(Question.class));
                    }
                });

        return question;
    }

    // Delete question
    public LiveData<Boolean> deleteQuestion(String questionId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(QUESTIONS_COLLECTION)
                .document(questionId)
                .delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Save quiz attempt
    public LiveData<String> saveQuizAttempt(QuizAttempt attempt) {
        MutableLiveData<String> result = new MutableLiveData<>();

        if (attempt.getId() == null) {
            attempt.setId(UUID.randomUUID().toString());
        }

        final String attemptId = attempt.getId();

        db.collection(QUIZ_ATTEMPTS_COLLECTION)
                .document(attemptId)
                .set(attempt)
                .addOnSuccessListener(aVoid -> result.setValue(attemptId))
                .addOnFailureListener(e -> result.setValue(null));

        return result;
    }

    // Get quiz attempt by ID
    public LiveData<QuizAttempt> getQuizAttempt(String attemptId) {
        MutableLiveData<QuizAttempt> attempt = new MutableLiveData<>();

        db.collection(QUIZ_ATTEMPTS_COLLECTION)
                .document(attemptId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        attempt.setValue(null);
                        return;
                    }

                    if (value != null && value.exists()) {
                        attempt.setValue(value.toObject(QuizAttempt.class));
                    }
                });

        return attempt;
    }

    // Get all quiz attempts for a quiz
    public LiveData<List<QuizAttempt>> getQuizAttemptsByQuiz(String quizId) {
        MutableLiveData<List<QuizAttempt>> attempts = new MutableLiveData<>();

        db.collection(QUIZ_ATTEMPTS_COLLECTION)
                .whereEqualTo("quizId", quizId)
                .orderBy("attemptTime")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        attempts.setValue(new ArrayList<>());
                        return;
                    }

                    if (value != null) {
                        List<QuizAttempt> attemptList = value.toObjects(QuizAttempt.class);
                        attempts.setValue(attemptList);
                    }
                });

        return attempts;
    }

    // Update quiz attempt
    public LiveData<Boolean> updateQuizAttempt(QuizAttempt attempt) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(QUIZ_ATTEMPTS_COLLECTION)
                .document(attempt.getId())
                .update(
                        "correctAnswers", attempt.getCorrectAnswers(),
                        "score", attempt.getScore(),
                        "heartsRemaining", attempt.getHeartsRemaining(),
                        "completed", attempt.isCompleted(),
                        "userAnswers", attempt.getUserAnswers()
                )
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }
}