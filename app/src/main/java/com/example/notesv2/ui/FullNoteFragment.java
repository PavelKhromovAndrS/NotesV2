package com.example.notesv2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notesv2.R;
import com.example.notesv2.domain.Note;

public class FullNoteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView fullNoteName = view.findViewById(R.id.note_full_name);
        TextView fullNoteText = view.findViewById(R.id.note_full_text);

        if (getArguments() != null) {
            Note note = FullNoteFragment.this.getArguments().getParcelable(ListNotesFragment.ARG_FULL_NOTE);
            fullNoteName.setText(note.getName());
            fullNoteText.setText(note.getText());
        }
    }
}