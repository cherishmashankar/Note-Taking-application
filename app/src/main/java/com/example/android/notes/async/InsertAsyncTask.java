package com.example.android.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.notes.models.Note;
import com.example.android.notes.persistence.NoteDao;
import com.example.android.notes.persistence.NoteDataBase;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
    private static final String TAG = "InsertAsyncTask";
    private NoteDao mNoteDao;

    public InsertAsyncTask(NoteDao mNoteDao) {
        this.mNoteDao = mNoteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.e(TAG, "doInBackground: Thread Name" + Thread.currentThread().getName() );
        mNoteDao.insertNotes(notes);
        return null;
    }
}
