package com.example.notesv2.ui;

import com.example.notesv2.domain.Note;

import java.util.List;

public interface NotesListView {

    void displayNotes(List<Note> notes);

    void deleteNote(Note selectedNote);

    void updateNote(Note result);

}
