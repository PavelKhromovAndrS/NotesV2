package com.example.notesv2.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FireStoreNotesRepository implements NotesRepository {

    private static final String NOTES = "notes";

    private static final String NAME = "name";

    private static final String TEXT = "text";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        db.collection(NOTES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            ArrayList<Note> result = new ArrayList<>();
                            for (DocumentSnapshot document : documents) {
                                String noteId = document.getId();
                                String name = document.getString(NAME);
                                String text = document.getString(TEXT);

                                Note note = new Note(noteId, name, text);

                                result.add(note);
                            }
                            callback.onSuccess(result);

                        } else {
                            callback.onError(task.getException());
                        }

                    }
                });

    }

    @Override
    public void add(String name, String text, Callback<Note> callback) {

        Map<String, Object> data = new HashMap<>();
        data.put(NAME, name);
        data.put(TEXT, text);

        db.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Note note = new Note(Objects.requireNonNull(task.getResult()).getId(), name, text);
                            callback.onSuccess(note);
                        } else {
                            callback.onError(task.getException());
                        }
                    }
                });

    }

    @Override
    public void update(String id, String name, String text, Callback<Note> callback) {
        Map<String, Object> data = new HashMap<>();

        data.put(NAME, name);
        data.put(TEXT, text);

        db.collection(NOTES)
                .document(id)
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(new Note(id, name, text));
                        } else {
                            callback.onError(task.getException());
                        }

                    }
                });
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(null);

                        } else {
                            callback.onError(task.getException());
                        }

                    }
                });

    }
}
