package com.example.rus_r.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rus_r.model.StudyStreak;
import com.example.rus_r.model.UserProgress;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class ProgressRepository {
    private final FirebaseFirestore db;
    private final String USER_PROGRESS_COLLECTION = "user_progress";
    private final String STUDY_STREAKS_COLLECTION = "study_streaks";

    public ProgressRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    // Create user progress
    public LiveData<Boolean> createUserProgress(UserProgress progress) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        if (progress.getId() == null) {
            progress.setId(UUID.randomUUID().toString());
        }

        db.collection(USER_PROGRESS_COLLECTION)
                .document(progress.getId())
                .set(progress)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Get user progress for a subject
    public LiveData<UserProgress> getUserProgress(String subjectId) {
        MutableLiveData<UserProgress> progress = new MutableLiveData<>();

        db.collection(USER_PROGRESS_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .limit(1)
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null || value.isEmpty()) {
                        progress.setValue(null);
                        return;
                    }

                    UserProgress userProgress = value.toObjects(UserProgress.class).get(0);
                    progress.setValue(userProgress);
                });

        return progress;
    }

    // Update user progress
    public LiveData<Boolean> updateUserProgress(UserProgress progress) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(USER_PROGRESS_COLLECTION)
                .document(progress.getId())
                .update(
                        "totalLessonsViewed", progress.getTotalLessonsViewed(),
                        "totalQuizzesTaken", progress.getTotalQuizzesTaken(),
                        "totalFlashcardsReviewed", progress.getTotalFlashcardsReviewed(),
                        "averageQuizScore", progress.getAverageQuizScore(),
                        "lastStudyTime", progress.getLastStudyTime(),
                        "currentStreak", progress.getCurrentStreak(),
                        "longestStreak", progress.getLongestStreak(),
                        "totalStudyTime", progress.getTotalStudyTime()
                )
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Increment lessons viewed
    public void incrementLessonsViewed(String progressId) {
        db.collection(USER_PROGRESS_COLLECTION)
                .document(progressId)
                .update("totalLessonsViewed", com.google.firebase.firestore.FieldValue.increment(1));
    }

    // Increment quizzes taken
    public void incrementQuizzesTaken(String progressId) {
        db.collection(USER_PROGRESS_COLLECTION)
                .document(progressId)
                .update("totalQuizzesTaken", com.google.firebase.firestore.FieldValue.increment(1));
    }

    // Increment flashcards reviewed
    public void incrementFlashcardsReviewed(String progressId) {
        db.collection(USER_PROGRESS_COLLECTION)
                .document(progressId)
                .update("totalFlashcardsReviewed", com.google.firebase.firestore.FieldValue.increment(1));
    }

    // Create study streak
    public LiveData<Boolean> createStudyStreak(StudyStreak streak) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        if (streak.getId() == null) {
            streak.setId(UUID.randomUUID().toString());
        }

        db.collection(STUDY_STREAKS_COLLECTION)
                .document(streak.getId())
                .set(streak)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Get study streak for a subject
    public LiveData<StudyStreak> getStudyStreak(String subjectId) {
        MutableLiveData<StudyStreak> streak = new MutableLiveData<>();

        db.collection(STUDY_STREAKS_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .limit(1)
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null || value.isEmpty()) {
                        streak.setValue(null);
                        return;
                    }

                    StudyStreak studyStreak = value.toObjects(StudyStreak.class).get(0);
                    streak.setValue(studyStreak);
                });

        return streak;
    }

    // Update study streak
    public LiveData<Boolean> updateStudyStreak(StudyStreak streak) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(STUDY_STREAKS_COLLECTION)
                .document(streak.getId())
                .update(
                        "currentStreak", streak.getCurrentStreak(),
                        "longestStreak", streak.getLongestStreak(),
                        "lastStudyDate", streak.getLastStudyDate()
                )
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Increment study streak
    public void incrementStudyStreak(String streakId) {
        db.collection(STUDY_STREAKS_COLLECTION)
                .document(streakId)
                .update("currentStreak", com.google.firebase.firestore.FieldValue.increment(1));
    }

    // Reset study streak
    public LiveData<Boolean> resetStudyStreak(String streakId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(STUDY_STREAKS_COLLECTION)
                .document(streakId)
                .update("currentStreak", 0)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }
}