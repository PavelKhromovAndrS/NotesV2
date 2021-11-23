package com.example.notesv2.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;


public class Note implements Parcelable {

    @SerializedName("id")
    private String id;

    private String name;

    private String text;

    public Note(String id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public Note(String name, String text) {
        this.name = name;
        this.text = text;
    }

    protected Note(Parcel in) {
        name = in.readString();
        text = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(text);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) &&
                Objects.equals(name, note.name) &&
                Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text);
    }
}
