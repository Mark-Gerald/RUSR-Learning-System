package com.example.rus_r.ai;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rus_r.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

public class AIService {
    private static final String TAG = "AIService";
    private static final String API_KEY = "YOUR_API_KEY_HERE"; // Add your API key

    /**
     * Check if AI service is configured
     */
    public static boolean isConfigured() {
        return API_KEY != null && !API_KEY.isEmpty() && !API_KEY.equals("YOUR_API_KEY_HERE");
    }

    /**
     * Set API key
     */
    public static void setApiKey(String apiKey) {
        // In production, store this securely
        Log.d(TAG, "API Key set");
    }

    /**
     * Extract keywords from text
     */
    public static LiveData<List<String>> extractKeywords(String text) {
        MutableLiveData<List<String>> result = new MutableLiveData<>();

        if (ValidationUtils.isEmpty(text)) {
            result.setValue(new ArrayList<>());
            return result;
        }

        try {
            // Simple keyword extraction (without API call)
            List<String> keywords = extractKeywordsLocally(text);
            result.setValue(keywords);
        } catch (Exception e) {
            Log.e(TAG, "Error extracting keywords: " + e.getMessage());
            result.setValue(new ArrayList<>());
        }

        return result;
    }

    /**
     * Local keyword extraction (no API call needed)
     */
    private static List<String> extractKeywordsLocally(String text) {
        List<String> keywords = new ArrayList<>();

        // Split text into words
        String[] words = text.toLowerCase().split("\\s+");

        // Filter out common words and short words
        String[] commonWords = {"the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for", "of", "is", "are", "was", "were", "be", "have", "has", "do", "does", "did", "will", "would", "could", "should", "may", "might", "must", "can"};

        for (String word : words) {
            if (word.length() > 3 && !isCommonWord(word, commonWords)) {
                if (!keywords.contains(word)) {
                    keywords.add(word);
                }
            }
        }

        // Return top 10 keywords
        return keywords.subList(0, Math.min(10, keywords.size()));
    }

    /**
     * Check if word is common
     */
    private static boolean isCommonWord(String word, String[] commonWords) {
        for (String common : commonWords) {
            if (word.equals(common)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get API ready status
     */
    public static LiveData<Boolean> checkApiStatus() {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        result.setValue(isConfigured());
        return result;
    }
}