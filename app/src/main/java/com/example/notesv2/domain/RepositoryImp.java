package com.example.notesv2.domain;

import java.util.ArrayList;

public class RepositoryImp implements Repository {
    private static RepositoryImp instance;
    private final ArrayList<Note> noteData = new ArrayList<>();

    private RepositoryImp() {
    }

    public static RepositoryImp getInstance() {
        if (instance == null) {
            instance = new RepositoryImp();
        }
        return instance;
    }

    public ArrayList<Note> getNotes() {
        return noteData;
    }
}
