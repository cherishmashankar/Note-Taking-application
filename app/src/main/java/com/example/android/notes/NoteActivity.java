package com.example.android.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.notes.models.Note;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener {
    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //UI components
    private LineEditText mLineEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mBack;

    //vars
    private boolean mIsNoteNew;
    private Note mInitialNote;
    private GestureDetector mGestureDetector;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mLineEditText = findViewById(R.id.note_content_edit_view);
        mEditTitle = findViewById(R.id.note_title_text_edit);
        mViewTitle = findViewById(R.id.note_title_text_view);
        mCheck = findViewById(R.id.tool_bar_check);
        mBack = findViewById(R.id.tool_bar_back_arrow);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);

        if (getIncomingNote()) {
            //this is a new note ----- Edit Mode
            enableEditMode();
            setNewNoteProperty();


        } else {
            disableEditMode();
            //this is not a new note ---- View Mode
            setNoteProperties();
           // disableContentInteraction();

        }
        setOnTouch();
        //set on Touch
    }

    private void setOnTouch(){
        mLineEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }


    private void enableEditMode(){
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mEditTitle.setVisibility(View.VISIBLE);
        mViewTitle.setVisibility(View.GONE);
        mMode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }
    private void disableEditMode(){
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mEditTitle.setVisibility(View.GONE);
        mViewTitle.setVisibility(View.VISIBLE);
        mMode = EDIT_MODE_DISABLED;

        disableContentInteraction();
    }

    private boolean getIncomingNote() {
        if (getIntent().hasExtra("selected_notes")) {
            mInitialNote = getIntent().getParcelableExtra("selected_notes");
            Log.e(TAG, "onCreate: " + mInitialNote.getContent());
            mIsNoteNew = false;
            mMode = EDIT_MODE_DISABLED;
            return mIsNoteNew;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNoteNew = true;
        return mIsNoteNew;

    }

    private void setNoteProperties() {
        mViewTitle.setText(mInitialNote.getTitle());
        mEditTitle.setText(mInitialNote.getTitle());
        mLineEditText.setText(mInitialNote.getContent());
    }
    private void disableContentInteraction(){
        mLineEditText.setFocusable(false);
        mLineEditText.setKeyListener(null);
        mLineEditText.setFocusableInTouchMode(false);
        mLineEditText.setCursorVisible(false);
        mLineEditText.clearFocus();
        hideSoftKeyboard();

    }

    private void enableContentInteraction(){
        mLineEditText.setFocusable(true);
        mLineEditText.setKeyListener(new EditText(this).getKeyListener());
        mLineEditText.setFocusableInTouchMode(true);
        mLineEditText.setCursorVisible(true);
        mLineEditText.requestFocus();


    }

    private void hideSoftKeyboard(){
        InputMethodManager imm =(InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view == null)
            view = new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setNewNoteProperty() {
        mViewTitle.setText("New Note");
        mEditTitle.setText("New Note");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.e(TAG, "onDoubleTap: " );
        enableEditMode();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tool_bar_check:
                disableEditMode();
                hideSoftKeyboard();
                break;
            case R.id.note_title_text_view:
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                break;

            case R.id.tool_bar_back_arrow:
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if(mMode == EDIT_MODE_ENABLED)
            onClick(mCheck);
        else
            super.onBackPressed();
    }
}