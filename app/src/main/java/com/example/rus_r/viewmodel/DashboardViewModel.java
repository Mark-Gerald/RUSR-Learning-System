package com.example.rus_r.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rus_r.model.Subject;
import com.example.rus_r.repository.SubjectRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final SubjectRepository subjectRepository;
    private LiveData<List<Subject>> subjects;

    public DashboardViewModel() {
        this.subjectRepository = new SubjectRepository();
        this.subjects = subjectRepository.getAllSubjects();
    }

    // Get all subjects
    public LiveData<List<Subject>> getAllSubjects() {
        return subjects;
    }

    // Create a new subject
    public LiveData<Boolean> createSubject(Subject subject) {
        return subjectRepository.createSubject(subject);
    }

    // Update subject ✅ FIXED
    public LiveData<Boolean> updateSubject(Subject subject) {
        return subjectRepository.updateSubject(subject);
    }

    // Delete a subject
    public LiveData<Boolean> deleteSubject(String subjectId) {
        return subjectRepository.deleteSubject(subjectId);
    }

    // Refresh subjects
    public void refreshSubjects() {
        this.subjects = subjectRepository.getAllSubjects();
    }
}