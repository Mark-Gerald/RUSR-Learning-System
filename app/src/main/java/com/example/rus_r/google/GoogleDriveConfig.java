package com.example.rus_r.google;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleDriveConfig {
    private static final String TAG = "GoogleDriveConfig";
    private static GoogleSignInClient googleSignInClient;

    /**
     * Initialize Google Sign-In
     */
    public static GoogleSignInClient initializeGoogleSignIn(Context context) {
        if (googleSignInClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(
                            new com.google.android.gms.common.api.Scope(
                                    "https://www.googleapis.com/auth/drive.readonly"
                            )
                    )
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(context, gso);
            Log.d(TAG, "Google Sign-In initialized");
        }
        return googleSignInClient;
    }

    /**
     * Get Google Sign-In client
     */
    public static GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    /**
     * Check if user is signed in
     */
    public static boolean isUserSignedIn(Context context) {
        return GoogleSignIn.getLastSignedInAccount(context) != null;
    }

    /**
     * Sign out from Google
     */
    public static void signOutFromGoogle(Context context) {
        if (googleSignInClient != null) {
            googleSignInClient.signOut()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "User signed out"))
                    .addOnFailureListener(e -> Log.e(TAG, "Sign out failed: " + e.getMessage()));
        }
    }
}