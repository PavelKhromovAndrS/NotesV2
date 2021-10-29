package com.example.notesv2.domain;

import android.content.SharedPreferences;

public class NotesStorage {
    private static final String ARG_NOTES = "ARG_NOTES";

    private final SharedPreferences sharedPreferences;


    public NotesStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
