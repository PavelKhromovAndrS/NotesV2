package com.example.notesv2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.notesv2.R;
import com.example.notesv2.domain.Note;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class EditNoteBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_RESULT = "KEY_RESULT";
    public static final String ARG_TITLE = "ARG_TITLE";
    public static final String ARG_MESSAGE = "ARG_MESSAGE";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note_bottom_sheet_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note note = requireArguments().getParcelable(ARG_NOTE);

        EditText name = view.findViewById(R.id.title);
        name.setText(note.getName());

        EditText text = view.findViewById(R.id.message);
        text.setText(note.getText());

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString(ARG_TITLE, name.getText().toString());
                bundle.putString(ARG_MESSAGE, text.getText().toString());

                getParentFragmentManager().setFragmentResult(KEY_RESULT, bundle);

                dismiss();
            }
        });
    }
}