package com.example.notesv2.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.FractionRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesv2.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final ArrayList<Note> notes = new ArrayList<>();

    private final Fragment fragment;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public interface OnNoteClicked {
        void onNoteClicked(Note note);

        void onNoteLongClicked(View itemView, Note note);
    }

    public interface OnButtonDeleteClicked {
        void onDeleteButtonClicked(Note note);
    }

    private OnButtonDeleteClicked onButtonDeleteClicked;

    public OnButtonDeleteClicked getOnButtonDeleteClicked() {
        return onButtonDeleteClicked;
    }

    public void setOnButtonDeleteClicked(OnButtonDeleteClicked onButtonDeleteClicked) {
        this.onButtonDeleteClicked = onButtonDeleteClicked;
    }

    private OnNoteClicked noteClicked;

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    public void setNotes(Collection<Note> toSet) {
        notes.clear();
        notes.addAll(toSet);
    }

    public void addNotes(Note note) {
        notes.add(note);
    }

    public int deleteNotes(Note note) {
        int index = notes.indexOf(note);
        notes.remove(index);
        return index;
    }

    public int updateNote(Note result) {
        int index = -1;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(result.getId())) {
                index = i;
                break;
            }
        }

        notes.set(index, result);
        return index;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.getName().setText(note.getName());
        holder.getText().setText(note.getText());
        holder.getNoteDate().setText(dateFormat());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView text;
        private final Button removeButton;
        private final TextView noteDate;


        public TextView getName() {
            return name;
        }

        public TextView getText() {
            return text;
        }

        public Button getRemoveButton() {
            return removeButton;
        }

        public TextView getNoteDate() {
            return noteDate;
        }

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            itemView.setOnClickListener(view -> {
                if (getNoteClicked() != null) {
                    getNoteClicked().onNoteClicked(notes.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (getNoteClicked() != null) {
                        getNoteClicked().onNoteLongClicked(v, notes.get(getAdapterPosition()));
                    }
                    return true;
                }
            });

            name = itemView.findViewById(R.id.note_name);
            text = itemView.findViewById(R.id.note_text);
            removeButton = itemView.findViewById(R.id.remove_button);
            noteDate = itemView.findViewById(R.id.note_date);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getOnButtonDeleteClicked() != null) {
                        getOnButtonDeleteClicked().onDeleteButtonClicked(notes.get(getAdapterPosition()));
                    }
                }
            });
        }

    }

    public String dateFormat() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(currentDate);
    }
}
