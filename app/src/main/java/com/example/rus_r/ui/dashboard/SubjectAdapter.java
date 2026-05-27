package com.example.rus_r.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rus_r.R;
import com.example.rus_r.model.Subject;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private List<Subject> subjects = new ArrayList<>();
    private final OnSubjectClickListener listener;

    public interface OnSubjectClickListener {
        void onSubjectClick(Subject subject);
    }

    public SubjectAdapter(OnSubjectClickListener listener) {
        this.listener = listener;
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
        holder.bind(subject, listener);
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

        void bind(Subject subject, OnSubjectClickListener listener) {
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

            // Click listener
            card.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSubjectClick(subject);
                }
            });
        }
    }
}