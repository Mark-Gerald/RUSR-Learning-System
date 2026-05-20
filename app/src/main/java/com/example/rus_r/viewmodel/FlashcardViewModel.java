package com.example.rus_r.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rus_r.model.Flashcard;
import com.example.rus_r.repository.FlashcardRepository;
import com.example.rus_r.repository.SubjectRepository;

import java.util.List;

public class FlashcardViewModel extends ViewModel {
    private final FlashcardRepository flashcardRepository;
    private final SubjectRepository subjectRepository;
    private LiveData<List<Flashcard>> flashcards;
    private LiveData<Flashcard> currentFlashcard;

    public FlashcardViewModel() {
        this.flashcardRepository = new FlashcardRepository();
        this.subjectRepository = new SubjectRepository();
    }

    // Get all flashcards for a subject
    public LiveData<List<Flashcard>> getFlashcardsBySubject(String subjectId) {
        this.flashcards = flashcardRepository.getFlashcardsBySubject(subjectId);
        return flashcards;
    }

    // Get flashcard by ID
    public LiveData<Flashcard> getFlashcardById(String flashcardId) {
        this.currentFlashcard = flashcardRepository.getFlashcardById(flashcardId);
        return currentFlashcard;
    }

    // Create flashcard
    public LiveData<Boolean> createFlashcard(Flashcard flashcard, String subjectId) {
        LiveData<Boolean> result = flashcardRepository.createFlashcard(flashcard);

        // Increment flashcard count in subject
        result.observeForever(success -> {
            if (success) {
                subjectRepository.incrementFlashcardCount(subjectId);
            }
        });

        return result;
    }

    // Update flashcard
    public LiveData<Boolean> updateFlashcard(Flashcard flashcard) {
        return flashcardRepository.updateFlashcard(flashcard);
    }

    // Delete flashcard
    public LiveData<Boolean> deleteFlashcard(String flashcardId, String subjectId) {
        LiveData<Boolean> result = flashcardRepository.deleteFlashcard(flashcardId);

        // Decrement flashcard count in subject
        result.observeForever(success -> {
            if (success) {
                subjectRepository.decrementFlashcardCount(subjectId);
            }
        });

        return result;
    }

    // Increment review count
    public void incrementReviewCount(String flashcardId) {
        flashcardRepository.incrementReviewCount(flashcardId);
    }

    // Toggle favorite
    public LiveData<Boolean> toggleFavorite(String flashcardId, boolean isFavorite) {
        return flashcardRepository.toggleFavorite(flashcardId, isFavorite);
    }

    // Get favorite flashcards
    public LiveData<List<Flashcard>> getFavoriteFlashcards(String subjectId) {
        return flashcardRepository.getFavoriteFlashcards(subjectId);
    }

    // Refresh flashcards
    public void refreshFlashcards(String subjectId) {
        this.flashcards = flashcardRepository.getFlashcardsBySubject(subjectId);
    }
}