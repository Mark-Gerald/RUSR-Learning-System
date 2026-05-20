package com.example.rus_r.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rus_r.model.Flashcard;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlashcardRepository {
    private final FirebaseFirestore db;
    private final String FLASHCARDS_COLLECTION = "flashcards";

    public FlashcardRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    // Create a new flashcard
    public LiveData<Boolean> createFlashcard(Flashcard flashcard) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        if (flashcard.getId() == null) {
            flashcard.setId(UUID.randomUUID().toString());
        }

        db.collection(FLASHCARDS_COLLECTION)
                .document(flashcard.getId())
                .set(flashcard)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Get all flashcards for a subject
    public LiveData<List<Flashcard>> getFlashcardsBySubject(String subjectId) {
        MutableLiveData<List<Flashcard>> flashcards = new MutableLiveData<>();

        db.collection(FLASHCARDS_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .orderBy("createdAt")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        flashcards.setValue(new ArrayList<>());
                        return;
                    }

                    if (value != null) {
                        List<Flashcard> flashcardList = value.toObjects(Flashcard.class);
                        flashcards.setValue(flashcardList);
                    }
                });

        return flashcards;
    }

    // Get flashcard by ID
    public LiveData<Flashcard> getFlashcardById(String flashcardId) {
        MutableLiveData<Flashcard> flashcard = new MutableLiveData<>();

        db.collection(FLASHCARDS_COLLECTION)
                .document(flashcardId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        flashcard.setValue(null);
                        return;
                    }

                    if (value != null && value.exists()) {
                        flashcard.setValue(value.toObject(Flashcard.class));
                    }
                });

        return flashcard;
    }

    // Update flashcard
    public LiveData<Boolean> updateFlashcard(Flashcard flashcard) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        flashcard.setUpdatedAt(System.currentTimeMillis());

        db.collection(FLASHCARDS_COLLECTION)
                .document(flashcard.getId())
                .update(
                        "front", flashcard.getFront(),
                        "back", flashcard.getBack(),
                        "reviewCount", flashcard.getReviewCount(),
                        "isFavorite", flashcard.isFavorite(),
                        "updatedAt", flashcard.getUpdatedAt()
                )
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Delete flashcard
    public LiveData<Boolean> deleteFlashcard(String flashcardId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(FLASHCARDS_COLLECTION)
                .document(flashcardId)
                .delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Increment review count
    public void incrementReviewCount(String flashcardId) {
        db.collection(FLASHCARDS_COLLECTION)
                .document(flashcardId)
                .update("reviewCount", com.google.firebase.firestore.FieldValue.increment(1));
    }

    // Toggle favorite
    public LiveData<Boolean> toggleFavorite(String flashcardId, boolean isFavorite) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(FLASHCARDS_COLLECTION)
                .document(flashcardId)
                .update("isFavorite", isFavorite)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    // Get favorite flashcards for a subject
    public LiveData<List<Flashcard>> getFavoriteFlashcards(String subjectId) {
        MutableLiveData<List<Flashcard>> flashcards = new MutableLiveData<>();

        db.collection(FLASHCARDS_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .whereEqualTo("isFavorite", true)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        flashcards.setValue(new ArrayList<>());
                        return;
                    }

                    if (value != null) {
                        List<Flashcard> flashcardList = value.toObjects(Flashcard.class);
                        flashcards.setValue(flashcardList);
                    }
                });

        return flashcards;
    }
}