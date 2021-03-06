package com.example.notesv2.domain;

public interface Callback<T> {
    void onSuccess(T result);

    void onError(Throwable error);
}
