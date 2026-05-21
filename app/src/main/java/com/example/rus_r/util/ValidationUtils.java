package com.example.rus_r.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class ValidationUtils {

    /**
     * Validate subject name
     * - Not empty
     * - Between 1-50 characters
     * - Only letters, numbers, and spaces
     */
    public static boolean isValidSubjectName(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        return name.matches(Constants.SUBJECT_NAME_REGEX);
    }

    /**
     * Validate email
     */
    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return Pattern.matches(Constants.EMAIL_REGEX, email);
    }

    /**
     * Validate lesson title
     * - Not empty
     * - Between 1-100 characters
     */
    public static boolean isValidLessonTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return false;
        }
        return title.length() > 0 && title.length() <= 100;
    }

    /**
     * Validate quiz title
     */
    public static boolean isValidQuizTitle(String title) {
        return isValidLessonTitle(title);
    }

    /**
     * Validate flashcard front/back text
     * - Not empty
     * - Between 1-500 characters
     */
    public static boolean isValidFlashcardText(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.length() > 0 && text.length() <= 500;
    }

    /**
     * Validate question text
     * - Not empty
     * - Between 1-1000 characters
     */
    public static boolean isValidQuestionText(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.length() > 0 && text.length() <= 1000;
    }

    /**
     * Validate answer text
     */
    public static boolean isValidAnswer(String answer) {
        return !TextUtils.isEmpty(answer);
    }

    /**
     * Validate multiple choice options
     * - At least 2 options
     * - Each option not empty
     */
    public static boolean isValidMultipleChoiceOptions(java.util.List<String> options) {
        if (options == null || options.size() < 2) {
            return false;
        }

        for (String option : options) {
            if (TextUtils.isEmpty(option)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate difficulty level
     */
    public static boolean isValidDifficulty(String difficulty) {
        if (TextUtils.isEmpty(difficulty)) {
            return false;
        }

        return difficulty.equalsIgnoreCase(Constants.DIFFICULTY_EASY) ||
                difficulty.equalsIgnoreCase(Constants.DIFFICULTY_MEDIUM) ||
                difficulty.equalsIgnoreCase(Constants.DIFFICULTY_HARD);
    }

    /**
     * Validate question type
     */
    public static boolean isValidQuestionType(String questionType) {
        if (TextUtils.isEmpty(questionType)) {
            return false;
        }

        return questionType.equals(Constants.QUESTION_TYPE_MULTIPLE_CHOICE) ||
                questionType.equals(Constants.QUESTION_TYPE_IDENTIFICATION) ||
                questionType.equals(Constants.QUESTION_TYPE_TYPING) ||
                questionType.equals(Constants.QUESTION_TYPE_VOICE);
    }

    /**
     * Check if string is not empty
     */
    public static boolean isNotEmpty(String text) {
        return !TextUtils.isEmpty(text);
    }

    /**
     * Check if string is empty
     */
    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    /**
     * Validate number of questions for quiz
     * - Between 1-100 questions
     */
    public static boolean isValidQuestionCount(int count) {
        return count > 0 && count <= 100;
    }

    /**
     * Validate quiz score (percentage)
     * - Between 0-100
     */
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }

    /**
     * Validate hearts count
     */
    public static boolean isValidHearts(int hearts) {
        return hearts >= 0;
    }

    /**
     * Validate streak count
     */
    public static boolean isValidStreakCount(int count) {
        return count >= 0;
    }

    /**
     * Check if password is strong
     * - At least 8 characters
     * - Contains uppercase, lowercase, number, and special character
     */
    public static boolean isStrongPassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            return false;
        }

        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    /**
     * Get validation error message
     */
    public static String getValidationError(String fieldName, String value) {
        if (TextUtils.isEmpty(value)) {
            return fieldName + " cannot be empty";
        }

        if (fieldName.equalsIgnoreCase("Subject Name")) {
            if (!isValidSubjectName(value)) {
                return "Subject name must contain only letters, numbers, and spaces (1-50 characters)";
            }
        } else if (fieldName.equalsIgnoreCase("Email")) {
            if (!isValidEmail(value)) {
                return "Please enter a valid email address";
            }
        } else if (fieldName.equalsIgnoreCase("Password")) {
            if (!isStrongPassword(value)) {
                return "Password must be at least 8 characters with uppercase, lowercase, number, and special character";
            }
        }

        return null;
    }
}