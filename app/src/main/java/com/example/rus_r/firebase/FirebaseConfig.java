package com.example.rus_r.firebase;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseConfig {
    private static final String TAG = "FirebaseConfig";
    private static FirebaseFirestore db;

    /**
     * Initialize Firebase Firestore
     */
    public static FirebaseFirestore getFirestoreInstance() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();

            // Configure Firestore settings
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true) // Enable offline persistence
                    .build();

            db.setFirestoreSettings(settings);
            Log.d(TAG, "Firestore initialized successfully");
        }
        return db;
    }

    /**
     * Check if Firebase is initialized
     */
    public static boolean isFirebaseInitialized() {
        return db != null;
    }

    /**
     * Reset Firestore instance (useful for testing)
     */
    public static void resetFirestore() {
        db = null;
        Log.d(TAG, "Firestore instance reset");
    }
}