package com.example.notesv2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesv2.R;
import com.example.notesv2.domain.Note;
import com.example.notesv2.domain.RepositoryImp;


public class AddNoteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addNoteButton = view.findViewById(R.id.btn_add_note);
        EditText noteName = view.findViewById(R.id.button_note_name);
        EditText noteText = view.findViewById(R.id.button_note_text);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), R.string.toast_add_note, Toast.LENGTH_SHORT).show();

                RepositoryImp.getInstance().addNotes(new Note(noteName.getText().toString(), noteText.getText().toString()));

                getParentFragmentManager().popBackStack();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, new ListNotesFragment())
                        .commit();
            }
        });
    }
}