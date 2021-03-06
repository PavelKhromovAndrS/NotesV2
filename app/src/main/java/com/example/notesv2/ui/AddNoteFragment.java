package com.example.notesv2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesv2.R;
import com.example.notesv2.domain.Note;

public class AddNoteFragment extends Fragment {

    public static final String KEY_RESULT1 = "KEY_RESULT1";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addNoteButton = view.findViewById(R.id.btn_add_note);
        EditText noteName = view.findViewById(R.id.button_note_name);
        EditText noteText = view.findViewById(R.id.button_note_text);

        addNoteButton.setOnClickListener(view1 -> {
            Toast.makeText(getActivity(), R.string.toast_add_note, Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putParcelable(AddNoteFragment.KEY_RESULT1, new Note(noteName.getText().toString(), noteText.getText().toString()));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_addNoteFragment_to_listNotesFragment, bundle);
            getParentFragmentManager().setFragmentResult(KEY_RESULT1, bundle);

        });
    }
}