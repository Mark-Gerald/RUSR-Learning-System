package com.example.rus_r.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtils {

    /**
     * Get file size in bytes
     */
    public static long getFileSize(Context context, Uri fileUri) {
        try {
            Cursor cursor = context.getContentResolver().query(fileUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                long fileSize = cursor.getLong(sizeIndex);
                cursor.close();
                return fileSize;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get file name from URI
     */
    public static String getFileName(Context context, Uri fileUri) {
        try {
            Cursor cursor = context.getContentResolver().query(fileUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                String fileName = cursor.getString(nameIndex);
                cursor.close();
                return fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    /**
     * Format file size to readable string
     * Example: "2.5 MB", "1.2 GB"
     */
    public static String formatFileSize(long bytes) {
        if (bytes <= 0) return "0 B";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(bytes / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }

    /**
     * Get file extension
     * Example: "pdf", "ppt", "docx"
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * Determine file type based on extension
     */
    public static String getFileType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();

        if (extension.equals("pdf")) {
            return Constants.FILE_TYPE_PDF;
        } else if (extension.matches("ppt|pptx")) {
            return Constants.FILE_TYPE_PPT;
        } else if (extension.matches("jpg|jpeg|png|gif|bmp")) {
            return Constants.FILE_TYPE_IMAGE;
        } else if (extension.matches("doc|docx|txt")) {
            return Constants.FILE_TYPE_DOCUMENT;
        } else {
            return Constants.FILE_TYPE_NOTES;
        }
    }

    /**
     * Check if file is image
     */
    public static boolean isImage(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        return extension.matches("jpg|jpeg|png|gif|bmp");
    }

    /**
     * Check if file is PDF
     */
    public static boolean isPdf(String fileName) {
        return getFileExtension(fileName).toLowerCase().equals("pdf");
    }

    /**
     * Check if file is PowerPoint
     */
    public static boolean isPowerPoint(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        return extension.matches("ppt|pptx");
    }

    /**
     * Check if file size is within limit
     * limit in bytes
     */
    public static boolean isFileSizeValid(long fileSize, long limitInBytes) {
        return fileSize <= limitInBytes;
    }

    /**
     * Check if file is valid for upload
     * Max size: 100 MB
     */
    public static boolean isValidFile(String fileName, long fileSize) {
        long maxSize = 100 * 1024 * 1024; // 100 MB
        String extension = getFileExtension(fileName).toLowerCase();

        // Check file extension
        String validExtensions = "pdf|ppt|pptx|doc|docx|jpg|jpeg|png|gif|txt";
        if (!extension.matches(validExtensions)) {
            return false;
        }

        // Check file size
        return isFileSizeValid(fileSize, maxSize);
    }

    /**
     * Get MIME type based on file extension
     */
    public static String getMimeType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();

        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "ppt":
            case "pptx":
                return "application/vnd.ms-powerpoint";
            case "doc":
            case "docx":
                return "application/msword";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "txt":
                return "text/plain";
            default:
                return "application/octet-stream";
        }
    }

    /**
     * Delete file
     */
    public static boolean deleteFile(File file) {
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * Get safe file name (remove special characters)
     */
    public static String getSafeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}