package com.example.notesv2.ui;

import com.example.notesv2.domain.Callback;
import com.example.notesv2.domain.FireStoreNotesRepository;
import com.example.notesv2.domain.Note;

import java.util.List;

public class NotesPresenter {

    private NotesListView view;
    private FireStoreNotesRepository repository;


    public NotesPresenter(NotesListView view, FireStoreNotesRepository repository) {
        this.view = view;
        this.repository = repository;

    }

    public void requestNotes() {

        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                view.displayNotes(result);
            }

            @Override
            public void onError(Throwable error) {

            }
        });

    }

    public void add(String nameNew, String textNew) {

        repository.add(nameNew, textNew, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
            }

            @Override
            public void onError(Throwable error) {

            }
        });

    }

    public void delete(Note note) {
        repository.delete(note, new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.deleteNote(note);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void updateNote(String title, String message, Note selectedNote) {

        repository.update(selectedNote.getId(), title, message, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {

                view.updateNote(result);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
