package com.example.android.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.notes.Adapters.NotesRecyclerAdapter;
import com.example.android.notes.models.Note;
import com.example.android.notes.utlis.VerticalSpacingDecorator;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteClickListener {

    private static final String TAG = "NotesListActivity";

    //Ui component
    private RecyclerView mRecyclerView;

    //variables
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNotesRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        initRecyclerView();
        insertFakeData();
        setSupportActionBar( (androidx.appcompat.widget.Toolbar) findViewById(R.id.notes_tool_bar));
        setTitle("Notes");
    }

    private void insertFakeData() {
        for(int i = 0; i < 1000; i++){
            Note note = new Note();
            note.setTitle("Title No: " + i);
            note.setContent("Content: ###" + i);
            note.setTimeStamp("Jan 2021");
            mNotes.add(note);
        }
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingDecorator itemSpacing = new VerticalSpacingDecorator(10);
        mRecyclerView.addItemDecoration(itemSpacing);
        mNotesRecyclerAdapter = new NotesRecyclerAdapter(mNotes,this);
        mRecyclerView.setAdapter(mNotesRecyclerAdapter);
    }


    @Override
    public void onNoteClick(int position) {

        Log.e(TAG, "onNoteClick: Position " + position);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_notes", mNotes.get(position));
        startActivity(intent);

    }
}