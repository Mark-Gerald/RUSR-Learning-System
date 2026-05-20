package com.example.rus_r.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rus_r.model.Subject;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubjectRepository {
    private final FirebaseFirestore db;
    private final String SUBJECTS_COLLECTION = "subjects";

    public SubjectRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    // Create a new subject
    public LiveData<Boolean> createSubject(Subject subject) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        if (subject.getId() == null) {
            subject.setId(UUID.randomUUID().toString());
        }

        db.collection(SUBJECTS_COLLECTION)
                .document(subject.getId())
                .set(subject)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Get all subjects
    public LiveData<List<Subject>> getAllSubjects() {
        MutableLiveData<List<Subject>> subjects = new MutableLiveData<>();

        db.collection(SUBJECTS_COLLECTION)
                .orderBy("createdAt")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        subjects.setValue(new ArrayList<>());
                        return;
                    }

                    if (value != null) {
                        List<Subject> subjectList = value.toObjects(Subject.class);
                        subjects.setValue(subjectList);
                    }
                });

        return subjects;
    }

    // Get subject by ID
    public LiveData<Subject> getSubjectById(String subjectId) {
        MutableLiveData<Subject> subject = new MutableLiveData<>();

        db.collection(SUBJECTS_COLLECTION)
                .document(subjectId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        subject.setValue(null);
                        return;
                    }

                    if (value != null && value.exists()) {
                        subject.setValue(value.toObject(Subject.class));
                    }
                });

        return subject;
    }

    // Update subject
    public LiveData<Boolean> updateSubject(Subject subject) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        subject.setUpdatedAt(System.currentTimeMillis());

        db.collection(SUBJECTS_COLLECTION)
                .document(subject.getId())
                .update(
                        "name", subject.getName(),
                        "description", subject.getDescription(),
                        "color", subject.getColor(),
                        "updatedAt", subject.getUpdatedAt(),
                        "lessonCount", subject.getLessonCount(),
                        "quizCount", subject.getQuizCount(),
                        "flashcardCount", subject.getFlashcardCount()
                )
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Delete subject
    public LiveData<Boolean> deleteSubject(String subjectId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(SUBJECTS_COLLECTION)
                .document(subjectId)
                .delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Increment lesson count
    public void incrementLessonCount(String subjectId) {
        db.collection(SUBJECTS_COLLECTION)
                .document(subjectId)
                .update("lessonCount", com.google.firebase.firestore.FieldValue.increment(1));
    }

    // Increment quiz count
    public void incrementQuizCount(String subjectId) {
        db.collection(SUBJECTS_COLLECTION)
                .document(subjectId)
                .update("quizCount", com.google.firebase.firestore.FieldValue.increment(1));
    }

    // Increment flashcard count
    public void incrementFlashcardCount(String subjectId) {
        db.collection(SUBJECTS_COLLECTION)
                .document(subjectId)
                .update("flashcardCount", com.google.firebase.firestore.FieldValue.increment(1));
    }

    // Decrement lesson count
    public void decrementLessonCount(String subjectId) {
        db.collection(SUBJECTS_COLLECTION)
                .document(subjectId)
                .update("lessonCount", com.google.firebase.firestore.FieldValue.increment(-1));
    }

    // Decrement quiz count
    public void decrementQuizCount(String subjectId) {
        db.collection(SUBJECTS_COLLECTION)
                .document(subjectId)
                .update("quizCount", com.google.firebase.firestore.FieldValue.increment(-1));
    }

    // Decrement flashcard count
    public void decrementFlashcardCount(String subjectId) {
        db.collection(SUBJECTS_COLLECTION)
                .document(subjectId)
                .update("flashcardCount", com.google.firebase.firestore.FieldValue.increment(-1));
    }
}