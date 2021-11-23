package com.example.notesv2.domain;

import java.util.List;

public interface NotesRepository {
    void getNotes(Callback<List<Note>> callback);

    void add(String name, String text, Callback<Note> callback);

    void update(String id, String title, String message, Callback<Note> callback);

    void delete(Note note, Callback<Void> callback);
}
