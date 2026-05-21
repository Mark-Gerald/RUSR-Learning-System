package com.example.rus_r.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rus_r.model.Lesson;
import com.example.rus_r.repository.LessonRepository;
import com.example.rus_r.repository.SubjectRepository;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class LessonViewModel extends ViewModel {
    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
    private LiveData<List<Lesson>> lessons;
    private LiveData<Lesson> currentLesson;

    public LessonViewModel() {
        this.lessonRepository = new LessonRepository();
        this.subjectRepository = new SubjectRepository();
    }

    // Get all lessons for a subject
    public LiveData<List<Lesson>> getLessonsBySubject(String subjectId) {
        this.lessons = lessonRepository.getLessonsBySubject(subjectId);
        return lessons;
    }

    // Get lesson by ID
    public LiveData<Lesson> getLessonById(String lessonId) {
        this.currentLesson = lessonRepository.getLessonById(lessonId);
        return currentLesson;
    }

    // Create lesson
    public LiveData<Boolean> createLesson(Lesson lesson, String subjectId) {
        LiveData<Boolean> result = lessonRepository.createLesson(lesson);

        // Increment lesson count in subject
        result.observeForever(success -> {
            if (success) {
                subjectRepository.incrementLessonCount(subjectId);
            }
        });

        return result;
    }

    // Update lesson
    public LiveData<Boolean> updateLesson(Lesson lesson) {
        return lessonRepository.updateLesson(lesson);
    }

    // Delete lesson
    public LiveData<Boolean> deleteLesson(String lessonId, String subjectId) {
        LiveData<Boolean> result = lessonRepository.deleteLesson(lessonId);

        // Decrement lesson count in subject
        result.observeForever(success -> {
            if (success) {
                subjectRepository.decrementLessonCount(subjectId);
            }
        });

        return result;
    }

    // Refresh lessons
    public void refreshLessons(String subjectId) {
        this.lessons = lessonRepository.getLessonsBySubject(subjectId);
    }
}