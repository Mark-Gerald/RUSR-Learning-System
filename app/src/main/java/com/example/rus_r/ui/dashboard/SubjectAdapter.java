package com.example.rus_r.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rus_r.R;
import com.example.rus_r.model.Subject;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private List<Subject> subjects = new ArrayList<>();
    private final OnSubjectClickListener listener;
    private final OnSubjectLongClickListener longClickListener;

    public interface OnSubjectClickListener {
        void onSubjectClick(Subject subject);
    }

    public interface OnSubjectLongClickListener {
        void onSubjectEdit(Subject subject);
        void onSubjectDelete(Subject subject);
    }

    public SubjectAdapter(OnSubjectClickListener listener, OnSubjectLongClickListener longClickListener) {
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject_card, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.bind(subject, listener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void updateSubjects(List<Subject> newSubjects) {
        this.subjects = newSubjects;
        notifyDataSetChanged();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        private final TextView subjectName;
        private final TextView subjectDescription;
        private final MaterialCardView card;
        private final View colorIndicator;

        SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);
            subjectDescription = itemView.findViewById(R.id.subject_description);
            card = (MaterialCardView) itemView;
            colorIndicator = itemView.findViewById(R.id.color_indicator);
        }

        void bind(Subject subject, OnSubjectClickListener listener, OnSubjectLongClickListener longClickListener) {
            subjectName.setText(subject.getName());
            subjectDescription.setText(subject.getDescription() != null ?
                    subject.getDescription() : "No description");

            // Set color based on subject color
            if (subject.getColor() != null) {
                try {
                    colorIndicator.setBackgroundColor(android.graphics.Color.parseColor(subject.getColor()));
                } catch (IllegalArgumentException e) {
                    colorIndicator.setBackgroundColor(itemView.getContext()
                            .getColor(R.color.primary));
                }
            }

            // Normal click - navigate to subject detail
            card.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSubjectClick(subject);
                }
            });

            // Long press - show options menu
            card.setOnLongClickListener(v -> {
                showSubjectMenu(subject, longClickListener);
                return true;
            });
        }

        private void showSubjectMenu(Subject subject, OnSubjectLongClickListener longClickListener) {
            String[] options = {"Edit", "Delete"};

            new MaterialAlertDialogBuilder(itemView.getContext())
                    .setTitle(subject.getName())
                    .setItems(options, (dialog, which) -> {
                        if (which == 0) {
                            // Edit
                            if (longClickListener != null) {
                                longClickListener.onSubjectEdit(subject);
                            }
                        } else if (which == 1) {
                            // Delete
                            showDeleteConfirmation(subject, longClickListener);
                        }
                    })
                    .show();
        }

        private void showDeleteConfirmation(Subject subject, OnSubjectLongClickListener longClickListener) {
            new MaterialAlertDialogBuilder(itemView.getContext())
                    .setTitle("Delete Subject")
                    .setMessage("Are you sure you want to delete \"" + subject.getName() + "\"?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        if (longClickListener != null) {
                            longClickListener.onSubjectDelete(subject);
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}