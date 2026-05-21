package com.example.rus_r.firebase;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private final FirebaseFirestore db;

    public FirebaseHelper() {
        this.db = FirebaseConfig.getFirestoreInstance();
    }

    /**
     * Perform batch write operations
     */
    public LiveData<Boolean> performBatchWrite(java.util.List<BatchOperation> operations) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        WriteBatch batch = db.batch();

        try {
            for (BatchOperation operation : operations) {
                switch (operation.getOperationType()) {
                    case SET:
                        batch.set(db.collection(operation.getCollection())
                                .document(operation.getDocumentId()), operation.getData());
                        break;
                    case UPDATE:
                        batch.update(db.collection(operation.getCollection())
                                .document(operation.getDocumentId()), operation.getData());
                        break;
                    case DELETE:
                        batch.delete(db.collection(operation.getCollection())
                                .document(operation.getDocumentId()));
                        break;
                }
            }

            batch.commit()
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Batch write successful");
                        result.setValue(true);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Batch write failed: " + e.getMessage());
                        result.setValue(false);
                    });

        } catch (Exception e) {
            Log.e(TAG, "Error performing batch write: " + e.getMessage());
            result.setValue(false);
        }

        return result;
    }

    /**
     * Check if collection exists
     */
    public LiveData<Boolean> collectionExists(String collectionName) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(collectionName)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    result.setValue(queryDocumentSnapshots.size() > 0);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error checking collection: " + e.getMessage());
                    result.setValue(false);
                });

        return result;
    }

    /**
     * Get document count in collection
     */
    public LiveData<Integer> getDocumentCount(String collectionName) {
        MutableLiveData<Integer> result = new MutableLiveData<>();

        db.collection(collectionName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    result.setValue(queryDocumentSnapshots.size());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting document count: " + e.getMessage());
                    result.setValue(0);
                });

        return result;
    }

    /**
     * Delete entire collection (use with caution!)
     */
    public LiveData<Boolean> deleteCollection(String collectionName) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(collectionName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    WriteBatch batch = db.batch();
                    for (com.google.firebase.firestore.DocumentSnapshot document : queryDocumentSnapshots) {
                        batch.delete(document.getReference());
                    }
                    batch.commit()
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "Collection deleted: " + collectionName);
                                result.setValue(true);
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Error deleting collection: " + e.getMessage());
                                result.setValue(false);
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching collection: " + e.getMessage());
                    result.setValue(false);
                });

        return result;
    }

    /**
     * Batch operation class
     */
    public static class BatchOperation {
        public enum OperationType {
            SET, UPDATE, DELETE
        }

        private final String collection;
        private final String documentId;
        private final OperationType operationType;
        private final Map<String, Object> data;

        public BatchOperation(String collection, String documentId, OperationType operationType, Map<String, Object> data) {
            this.collection = collection;
            this.documentId = documentId;
            this.operationType = operationType;
            this.data = data;
        }

        public String getCollection() {
            return collection;
        }

        public String getDocumentId() {
            return documentId;
        }

        public OperationType getOperationType() {
            return operationType;
        }

        public Map<String, Object> getData() {
            return data;
        }
    }
}