package com.example.notesv2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.notesv2.R;
import com.example.notesv2.domain.NotesAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ListNotesFragment extends Fragment {

    public static String ARG_FULL_NOTE = "ARG_FULL_NOTE";

    private NotesAdapter notesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesAdapter = new NotesAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notesListRoot = view.findViewById(R.id.notes_list_root);

        notesListRoot.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        notesListRoot.setAdapter(notesAdapter);

        notesAdapter.setNoteClicked(note -> {
            FullNoteFragment fullNoteFragment = new FullNoteFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARG_FULL_NOTE, note);
            fullNoteFragment.setArguments(bundle);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, fullNoteFragment)
                    .addToBackStack(null)
                    .commit();
        });

        Button plusButton = view.findViewById(R.id.plus_button);

        plusButton.setOnClickListener(view1 -> getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, new AddNoteFragment())
                .addToBackStack(null)
                .commit());
    }

}