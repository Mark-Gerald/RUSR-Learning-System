package com.example.rus_r.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rus_r.model.Lesson;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LessonRepository {
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;
    private final String LESSONS_COLLECTION = "lessons";

    public LessonRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    // Create a new lesson
    public LiveData<Boolean> createLesson(Lesson lesson) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        if (lesson.getId() == null) {
            lesson.setId(UUID.randomUUID().toString());
        }

        db.collection(LESSONS_COLLECTION)
                .document(lesson.getId())
                .set(lesson)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Get all lessons for a subject
    public LiveData<List<Lesson>> getLessonsBySubject(String subjectId) {
        MutableLiveData<List<Lesson>> lessons = new MutableLiveData<>();

        db.collection(LESSONS_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .orderBy("createdAt")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        lessons.setValue(new ArrayList<>());
                        return;
                    }

                    if (value != null) {
                        List<Lesson> lessonList = value.toObjects(Lesson.class);
                        lessons.setValue(lessonList);
                    }
                });

        return lessons;
    }

    // Get lesson by ID
    public LiveData<Lesson> getLessonById(String lessonId) {
        MutableLiveData<Lesson> lesson = new MutableLiveData<>();

        db.collection(LESSONS_COLLECTION)
                .document(lessonId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        lesson.setValue(null);
                        return;
                    }

                    if (value != null && value.exists()) {
                        lesson.setValue(value.toObject(Lesson.class));
                    }
                });

        return lesson;
    }

    // Update lesson
    public LiveData<Boolean> updateLesson(Lesson lesson) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        lesson.setUpdatedAt(System.currentTimeMillis());

        db.collection(LESSONS_COLLECTION)
                .document(lesson.getId())
                .update(
                        "title", lesson.getTitle(),
                        "description", lesson.getDescription(),
                        "fileUrl", lesson.getFileUrl(),
                        "fileType", lesson.getFileType(),
                        "fileSize", lesson.getFileSize(),
                        "updatedAt", lesson.getUpdatedAt()
                )
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Delete lesson
    public LiveData<Boolean> deleteLesson(String lessonId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        // First delete from Firestore
        db.collection(LESSONS_COLLECTION)
                .document(lessonId)
                .delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Delete file from Firebase Storage
    public LiveData<Boolean> deleteFile(String filePath) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        StorageReference fileRef = storage.getReference().child(filePath);

        fileRef.delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Get storage reference for uploading files
    public StorageReference getStorageReference(String subjectId, String fileName) {
        return storage.getReference()
                .child("lessons")
                .child(subjectId)
                .child(fileName);
    }
}