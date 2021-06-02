package com.example.android.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.android.notes.async.DeleteAsyncTask;
import com.example.android.notes.async.InsertAsyncTask;
import com.example.android.notes.async.UpdateAsyncTask;
import com.example.android.notes.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDataBase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDataBase.getInstance(context);
    }

    public void insertNote(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNote(Note note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void deleteNote(Note note){
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> getNote(){
        return mNoteDatabase.getNoteDao().getNotes();

    }
}
