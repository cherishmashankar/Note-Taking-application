package com.example.android.notes.persistence;

import android.content.ContentValues;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.notes.models.Note;

@Database( entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes_db";
    private static NoteDataBase instance;

    public static NoteDataBase getInstance(final Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDataBase.class,
                    DATABASE_NAME
            ).build();
        }

            return instance;

        }

        public abstract NoteDao getNoteDao();
    }

