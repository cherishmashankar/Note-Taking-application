package com.example.android.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.notes.models.Note;
import com.example.android.notes.persistence.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
    private static final String TAG = "DeleteAsyncTask";
    private NoteDao mNoteDao;

    public DeleteAsyncTask(NoteDao mNoteDao) {
        this.mNoteDao = mNoteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.e(TAG, "doInBackground: Thread Name" + Thread.currentThread().getName());
        mNoteDao.delete(notes);
        return null;
    }
}
