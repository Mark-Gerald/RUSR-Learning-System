package com.example.rus_r.google;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rus_r.util.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GoogleDriveHelper {
    private static final String TAG = "GoogleDriveHelper";
    private final Context context;

    public GoogleDriveHelper(Context context) {
        this.context = context;
        Log.d(TAG, "GoogleDriveHelper initialized");
    }

    /**
     * Read file content from URI (works with local files and cloud storage)
     */
    public LiveData<String> readFileContent(Uri fileUri) {
        MutableLiveData<String> result = new MutableLiveData<>();

        new Thread(() -> {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream inputStream = context.getContentResolver().openInputStream(fileUri);

                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();

                    String content = stringBuilder.toString();
                    result.postValue(content);
                    Log.d(TAG, "File content read successfully, length: " + content.length());
                } else {
                    result.postValue("");
                    Log.e(TAG, "Could not open input stream");
                }

            } catch (IOException e) {
                Log.e(TAG, "Error reading file: " + e.getMessage());
                result.postValue("");
            }
        }).start();

        return result;
    }

    /**
     * Get file name from URI
     */
    public static String getFileNameFromUri(Context context, Uri fileUri) {
        try {
            DocumentFile documentFile = DocumentFile.fromSingleUri(context, fileUri);
            if (documentFile != null && documentFile.getName() != null) {
                return documentFile.getName();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting file name: " + e.getMessage());
        }
        return "unknown_file";
    }

    /**
     * Get file size from URI
     */
    public static long getFileSizeFromUri(Context context, Uri fileUri) {
        try {
            DocumentFile documentFile = DocumentFile.fromSingleUri(context, fileUri);
            if (documentFile != null) {
                return documentFile.length();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting file size: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Get file MIME type from URI
     */
    public static String getFileMimeTypeFromUri(Context context, Uri fileUri) {
        try {
            DocumentFile documentFile = DocumentFile.fromSingleUri(context, fileUri);
            if (documentFile != null && documentFile.getType() != null) {
                return documentFile.getType();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting MIME type: " + e.getMessage());
        }
        return "application/octet-stream";
    }

    /**
     * Check if file is valid for processing
     */
    public static boolean isValidFileType(String fileName) {
        String extension = FileUtils.getFileExtension(fileName).toLowerCase();

        // Accept text, PDF, Office documents, images
        return extension.matches("txt|pdf|doc|docx|ppt|pptx|jpg|jpeg|png|gif");
    }

    /**
     * Drive File class (simplified)
     */
    public static class DriveFile {
        private final String name;
        private final String mimeType;
        private final long size;
        private final Uri uri;

        public DriveFile(String name, String mimeType, long size, Uri uri) {
            this.name = name;
            this.mimeType = mimeType;
            this.size = size;
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public String getMimeType() {
            return mimeType;
        }

        public long getSize() {
            return size;
        }

        public Uri getUri() {
            return uri;
        }

        public String getFileType() {
            return FileUtils.getFileType(name);
        }

        public String getFormattedSize() {
            return FileUtils.formatFileSize(size);
        }
    }
}