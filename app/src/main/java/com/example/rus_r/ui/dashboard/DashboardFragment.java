package com.example.rus_r.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rus_r.R;
import com.example.rus_r.model.Subject;
import com.example.rus_r.viewmodel.DashboardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    private DashboardViewModel viewModel;
    private RecyclerView recyclerSubjects;
    private FloatingActionButton fabAddSubject;
    private SubjectAdapter subjectAdapter;
    private View emptyState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Setup RecyclerView
        recyclerSubjects = view.findViewById(R.id.recycler_subjects);
        recyclerSubjects.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // Setup Adapter
        subjectAdapter = new SubjectAdapter(subject -> {
            // TODO: Open subject detail
            Toast.makeText(getContext(), "Selected: " + subject.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerSubjects.setAdapter(subjectAdapter);

        // Setup FAB
        fabAddSubject = view.findViewById(R.id.fab_add_subject);
        fabAddSubject.setOnClickListener(v -> showAddSubjectDialog());

        // Setup Empty State
        emptyState = view.findViewById(R.id.empty_state);

        // Observe subjects
        viewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            if (subjects != null && !subjects.isEmpty()) {
                subjectAdapter.updateSubjects(subjects);
                emptyState.setVisibility(View.GONE);
                recyclerSubjects.setVisibility(View.VISIBLE);
            } else {
                recyclerSubjects.setVisibility(View.GONE);
                emptyState.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Show dialog to add new subject
     */
    private void showAddSubjectDialog() {
        try {
            // Create dialog view
            View dialogView = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_create_subject, null);

            TextInputEditText inputName = dialogView.findViewById(R.id.input_subject_name);
            TextInputEditText inputDescription = dialogView.findViewById(R.id.input_subject_description);

            // Create and show dialog
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
            builder.setTitle("Create New Subject")
                    .setView(dialogView)
                    .setPositiveButton("Create", (dialog, which) -> {
                        String name = inputName.getText().toString().trim();
                        String description = inputDescription.getText().toString().trim();

                        if (name.isEmpty()) {
                            Toast.makeText(getContext(), "Please enter subject name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Create new subject
                        Subject subject = new Subject();
                        subject.setName(name);
                        subject.setDescription(description);
                        subject.setColor("#4ECDC4"); // Default color

                        // Save to database
                        viewModel.createSubject(subject).observe(getViewLifecycleOwner(), success -> {
                            if (success) {
                                Toast.makeText(getContext(), "Subject created successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Failed to create subject", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}