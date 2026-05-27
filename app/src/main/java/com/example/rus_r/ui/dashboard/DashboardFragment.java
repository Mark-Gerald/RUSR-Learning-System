package com.example.rus_r.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rus_r.R;
import com.example.rus_r.viewmodel.DashboardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    private DashboardViewModel viewModel;
    private RecyclerView recyclerSubjects;
    private FloatingActionButton fabAddSubject;
    private SubjectAdapter subjectAdapter;

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
        });
        recyclerSubjects.setAdapter(subjectAdapter);

        // Setup FAB
        fabAddSubject = view.findViewById(R.id.fab_add_subject);
        fabAddSubject.setOnClickListener(v -> showAddSubjectDialog());

        // Observe subjects
        viewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            if (subjects != null && !subjects.isEmpty()) {
                subjectAdapter.updateSubjects(subjects);
                view.findViewById(R.id.empty_state).setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.empty_state).setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Show dialog to add new subject
     */
    private void showAddSubjectDialog() {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_create_subject, null);

        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Create New Subject")
                .setView(dialogView)
                .setPositiveButton("Create", (dialog, which) -> {
                    // TODO: Get input and create subject
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}