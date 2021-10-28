package com.example.notesv2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesv2.R;
import com.example.notesv2.domain.Note;
import com.example.notesv2.domain.RepositoryImp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ListNotesFragment extends Fragment implements NotesListView {
    public static String ARG_FULL_NOTE = "ARG_FULL_NOTE";

    private LinearLayout notesListRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_notes, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesListRoot = view.findViewById(R.id.notes_list_root);
        Button plusButton = view.findViewById(R.id.plus_button);

        showNotes(RepositoryImp.getInstance().getNotes());

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, new AddNoteFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public void showNotes(ArrayList<Note> notes) {
        for (Note note : notes) {
            View noteView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesListRoot, false);
            Button removeButton = noteView.findViewById(R.id.remove_button);

            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FullNoteFragment fullNoteFragment = new FullNoteFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ARG_FULL_NOTE, note);
                    fullNoteFragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, fullNoteFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), R.string.Toast_delete_note, Toast.LENGTH_SHORT).show();
                    ((LinearLayout) noteView.getParent()).removeView(noteView);
                    notes.remove(note);
                }
            });

            Date currentDate = new Date();

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);

            TextView noteDate = noteView.findViewById(R.id.note_date);
            noteDate.setText(dateText);

            TextView noteName = noteView.findViewById(R.id.note_name);
            noteName.setText(note.getName());

            TextView noteText = noteView.findViewById(R.id.note_text);
            noteText.setText(note.getText());

            notesListRoot.addView(noteView);
        }
    }
}