package com.example.rus_r.util;

public class Constants {
    // App Configuration
    public static final String APP_NAME = "RUS-R Learning System";
    public static final String APP_VERSION = "1.0.0";

    // Firebase Collections
    public static final String SUBJECTS_COLLECTION = "subjects";
    public static final String LESSONS_COLLECTION = "lessons";
    public static final String QUIZZES_COLLECTION = "quizzes";
    public static final String QUESTIONS_COLLECTION = "questions";
    public static final String FLASHCARDS_COLLECTION = "flashcards";
    public static final String QUIZ_ATTEMPTS_COLLECTION = "quiz_attempts";
    public static final String USER_PROGRESS_COLLECTION = "user_progress";
    public static final String STUDY_STREAKS_COLLECTION = "study_streaks";

    // Firebase Storage Paths
    public static final String STORAGE_LESSONS_PATH = "lessons";
    public static final String STORAGE_IMAGES_PATH = "images";

    // File Types
    public static final String FILE_TYPE_PDF = "pdf";
    public static final String FILE_TYPE_PPT = "ppt";
    public static final String FILE_TYPE_IMAGE = "image";
    public static final String FILE_TYPE_NOTES = "notes";
    public static final String FILE_TYPE_DOCUMENT = "document";

    // Difficulty Levels
    public static final String DIFFICULTY_EASY = "easy";
    public static final String DIFFICULTY_MEDIUM = "medium";
    public static final String DIFFICULTY_HARD = "hard";

    // Hearts System
    public static final int HEARTS_EASY = 5;
    public static final int HEARTS_MEDIUM = 3;
    public static final int HEARTS_HARD = 1;

    // Question Types
    public static final String QUESTION_TYPE_MULTIPLE_CHOICE = "multiple_choice";
    public static final String QUESTION_TYPE_IDENTIFICATION = "identification";
    public static final String QUESTION_TYPE_TYPING = "typing";
    public static final String QUESTION_TYPE_VOICE = "voice";

    // Time Constants (in milliseconds)
    public static final long ONE_DAY = 86400000L;
    public static final long ONE_HOUR = 3600000L;
    public static final long ONE_MINUTE = 60000L;

    // Default Values
    public static final int DEFAULT_LESSONS_PER_PAGE = 10;
    public static final int DEFAULT_QUIZZES_PER_PAGE = 10;
    public static final int DEFAULT_FLASHCARDS_PER_PAGE = 20;

    // Color Codes for Subject Cards
    public static final String[] SUBJECT_COLORS = {
            "#FF6B6B", // Red
            "#4ECDC4", // Teal
            "#45B7D1", // Blue
            "#FFA07A", // Light Salmon
            "#98D8C8", // Mint
            "#F7DC6F", // Yellow
            "#BB8FCE", // Purple
            "#85C1E2"  // Light Blue
    };

    // Regular Expressions
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String SUBJECT_NAME_REGEX = "^[a-zA-Z0-9\\s]{1,50}$";

    // Shared Preferences Keys
    public static final String PREFS_NAME = "RUS_R_PREFS";
    public static final String PREFS_FIRST_LAUNCH = "first_launch";
    public static final String PREFS_LAST_STUDY_DATE = "last_study_date";
    public static final String PREFS_THEME_MODE = "theme_mode";

    // Request Codes
    public static final int REQUEST_CODE_FILE_PICKER = 101;
    public static final int REQUEST_CODE_CAMERA = 102;
    public static final int REQUEST_CODE_GALLERY = 103;
    public static final int REQUEST_CODE_STORAGE = 104;

    // Intent Extra Keys
    public static final String EXTRA_SUBJECT_ID = "subject_id";
    public static final String EXTRA_LESSON_ID = "lesson_id";
    public static final String EXTRA_QUIZ_ID = "quiz_id";
    public static final String EXTRA_FLASHCARD_ID = "flashcard_id";
    public static final String EXTRA_ATTEMPT_ID = "attempt_id";
    public static final String EXTRA_QUESTION_ID = "question_id";

    // Debug Tags
    public static final String TAG_DASHBOARD = "Dashboard";
    public static final String TAG_SUBJECT = "Subject";
    public static final String TAG_LESSON = "Lesson";
    public static final String TAG_QUIZ = "Quiz";
    public static final String TAG_FLASHCARD = "Flashcard";
    public static final String TAG_FIREBASE = "Firebase";
}