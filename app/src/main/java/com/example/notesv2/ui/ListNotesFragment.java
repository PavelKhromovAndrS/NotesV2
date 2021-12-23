package com.example.notesv2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.notesv2.R;
import com.example.notesv2.domain.FireStoreNotesRepository;
import com.example.notesv2.domain.Note;
import com.example.notesv2.domain.NotesAdapter;

import java.util.List;

public class ListNotesFragment extends Fragment implements NotesListView {

    public static final String ARG_FULL_NOTE = "ARG_FULL_NOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private NotesAdapter notesAdapter;
    private NotesPresenter presenter;
    private Note selectedNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesAdapter = new NotesAdapter(ListNotesFragment.this);
        presenter = new NotesPresenter(this, new FireStoreNotesRepository());
        presenter.requestNotes();
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

        notesAdapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_FULL_NOTE, note);
                NavHostFragment.findNavController(ListNotesFragment.this)
                        .navigate(R.id.action_listNotesFragment_to_fullNoteFragment, bundle);
            }

            @Override
            public void onNoteLongClicked(View itemView, Note note) {
                itemView.showContextMenu();
                selectedNote = note;
            }
        });

        notesAdapter.setOnButtonDeleteClicked(new NotesAdapter.OnButtonDeleteClicked() {
            @Override
            public void onDeleteButtonClicked(Note note) {
                presenter.delete(note);
            }
        });

        Button plusButton = view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_listNotesFragment_to_addNoteFragment);
        });

        getParentFragmentManager().setFragmentResultListener(EditNoteBottomSheetDialogFragment.KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String title = result.getString(EditNoteBottomSheetDialogFragment.ARG_TITLE);
                String message = result.getString(EditNoteBottomSheetDialogFragment.ARG_MESSAGE);

                presenter.updateNote(title, message, selectedNote);
            }
        });

        getParentFragmentManager().setFragmentResultListener(AddNoteFragment.KEY_RESULT1, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(AddNoteFragment.KEY_RESULT1);

                presenter.add(note.getName(), note.getText());
            }
        });
    }

    @Override
    public void displayNotes(List<Note> notes) {
        notesAdapter.setNotes(notes);
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteNote(Note selectedNote) {
        int index = notesAdapter.deleteNotes(selectedNote);
        notesAdapter.notifyItemRemoved(index);
    }

    @Override
    public void updateNote(Note result) {
        int position = notesAdapter.updateNote(result);
        notesAdapter.notifyItemChanged(position);

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.contex_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            Toast.makeText(requireContext(), R.string.toast_delete_note, Toast.LENGTH_SHORT).show();

            presenter.delete(selectedNote);
            return true;
        }

        if (item.getItemId() == R.id.action_edit) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(ARG_NOTE, selectedNote);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_listNotesFragment_to_editNoteBottomSheetDialogFragment, arguments);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}