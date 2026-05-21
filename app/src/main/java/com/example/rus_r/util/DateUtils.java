package com.example.rus_r.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    /**
     * Format timestamp to readable date string
     * Example: "Jan 15, 2024"
     */
    public static String formatDate(long timestamp) {
        return formatDate(timestamp, "MMM dd, yyyy");
    }

    /**
     * Format timestamp with custom pattern
     */
    public static String formatDate(long timestamp, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            return sdf.format(new Date(timestamp));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Format timestamp to time string
     * Example: "2:30 PM"
     */
    public static String formatTime(long timestamp) {
        return formatDate(timestamp, "hh:mm a");
    }

    /**
     * Format timestamp to date and time
     * Example: "Jan 15, 2024 2:30 PM"
     */
    public static String formatDateTime(long timestamp) {
        return formatDate(timestamp, "MMM dd, yyyy hh:mm a");
    }

    /**
     * Get relative time string
     * Example: "2 hours ago", "Just now", "Tomorrow"
     */
    public static String getRelativeTime(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;

        if (diff < Constants.ONE_MINUTE) {
            return "Just now";
        } else if (diff < Constants.ONE_HOUR) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        } else if (diff < Constants.ONE_DAY) {
            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            if (days == 1) {
                return "Yesterday";
            } else if (days < 7) {
                return days + " days ago";
            } else if (days < 30) {
                long weeks = days / 7;
                return weeks + " week" + (weeks > 1 ? "s" : "") + " ago";
            } else if (days < 365) {
                long months = days / 30;
                return months + " month" + (months > 1 ? "s" : "") + " ago";
            } else {
                long years = days / 365;
                return years + " year" + (years > 1 ? "s" : "") + " ago";
            }
        }
    }

    /**
     * Check if two timestamps are on the same day
     */
    public static boolean isSameDay(long timestamp1, long timestamp2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timestamp1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(timestamp2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Get start of day timestamp
     */
    public static long getStartOfDay(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Get end of day timestamp
     */
    public static long getEndOfDay(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    /**
     * Convert milliseconds to readable time format
     * Example: "1h 30m 45s"
     */
    public static String formatDuration(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }

    /**
     * Check if timestamp is today
     */
    public static boolean isToday(long timestamp) {
        return isSameDay(timestamp, System.currentTimeMillis());
    }

    /**
     * Check if timestamp is yesterday
     */
    public static boolean isYesterday(long timestamp) {
        return isSameDay(timestamp, System.currentTimeMillis() - Constants.ONE_DAY);
    }

    /**
     * Get current timestamp
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Add days to timestamp
     */
    public static long addDays(long timestamp, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTimeInMillis();
    }

    /**
     * Get day of week name
     * Example: "Monday", "Tuesday"
     */
    public static String getDayOfWeekName(long timestamp) {
        return formatDate(timestamp, "EEEE");
    }
}