package com.example.notesv2.ui;

import com.example.notesv2.domain.Note;
import java.util.ArrayList;

public interface NotesListView {
    void showNotes(ArrayList<Note> notes);
}
