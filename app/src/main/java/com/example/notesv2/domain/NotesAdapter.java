package com.example.notesv2.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesv2.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private OnNoteClicked noteClicked;

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    private final ArrayList<Note> noteData = RepositoryImp.getInstance().getNotes();

    public void addNotes(Note note) {
        noteData.add(note);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteData.get(position);

        holder.getName().setText(note.getName());
        holder.getText().setText(note.getText());
        holder.getNoteDate().setText(dateFormat());
        holder.getRemoveButton().setOnClickListener(view -> {
            noteData.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return noteData.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView text;
        private Button removeButton;
        private TextView noteDate;


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
            itemView.setOnClickListener(view -> {
                if (getNoteClicked() != null) {
                    getNoteClicked().onNoteClicked(noteData.get(getAdapterPosition()));
                }
            });

            name = itemView.findViewById(R.id.note_name);
            text = itemView.findViewById(R.id.note_text);
            removeButton = itemView.findViewById(R.id.remove_button);
            noteDate = itemView.findViewById(R.id.note_date);
        }

    }

    public String dateFormat() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(currentDate);
    }
}
