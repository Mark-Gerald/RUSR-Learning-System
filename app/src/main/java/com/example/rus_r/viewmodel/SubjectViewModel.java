package com.example.rus_r.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rus_r.model.Subject;
import com.example.rus_r.repository.SubjectRepository;

public class SubjectViewModel extends ViewModel {
    private final SubjectRepository subjectRepository;
    private LiveData<Subject> subject;

    public SubjectViewModel() {
        this.subjectRepository = new SubjectRepository();
    }

    // Get subject by ID
    public LiveData<Subject> getSubject(String subjectId) {
        this.subject = subjectRepository.getSubjectById(subjectId);
        return subject;
    }

    // Update subject
    public LiveData<Boolean> updateSubject(Subject subject) {
        return subjectRepository.updateSubject(subject);
    }

    // Delete subject
    public LiveData<Boolean> deleteSubject(String subjectId) {
        return subjectRepository.deleteSubject(subjectId);
    }

    // Increment lesson count
    public void incrementLessonCount(String subjectId) {
        subjectRepository.incrementLessonCount(subjectId);
    }

    // Decrement lesson count
    public void decrementLessonCount(String subjectId) {
        subjectRepository.decrementLessonCount(subjectId);
    }

    // Increment quiz count
    public void incrementQuizCount(String subjectId) {
        subjectRepository.incrementQuizCount(subjectId);
    }

    // Decrement quiz count
    public void decrementQuizCount(String subjectId) {
        subjectRepository.decrementQuizCount(subjectId);
    }

    // Increment flashcard count
    public void incrementFlashcardCount(String subjectId) {
        subjectRepository.incrementFlashcardCount(subjectId);
    }

    // Decrement flashcard count
    public void decrementFlashcardCount(String subjectId) {
        subjectRepository.decrementFlashcardCount(subjectId);
    }
}