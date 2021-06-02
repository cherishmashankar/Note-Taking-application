package com.example.android.notes.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.notes.R;
import com.example.android.notes.models.Note;
import com.example.android.notes.utlis.Utility;

import java.util.ArrayList;
import java.util.TimeZone;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {
    private static final String TAG = "NotesRecyclerAdapter";

    private ArrayList<Note> mNote = new ArrayList<>();
    private OnNoteClickListener mOnNoteClickListener;

    public NotesRecyclerAdapter(ArrayList<Note> note, OnNoteClickListener onNoteClickListener) {
        this.mNote = note;
        this.mOnNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new ViewHolder(view, mOnNoteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{
            String month = mNote.get(position).getTimeStamp().substring(0,2);
            month = Utility.getMonthFromNumber(month);
            String year = mNote.get(position).getTimeStamp().substring(3);
            String timeStamp = month + " " + year;
            holder.title.setText(mNote.get(position).getTitle());
            holder.timeStamp.setText(timeStamp);
        }catch(NullPointerException e){
            Log.d(TAG, "onBindViewHolder: There is a error in Date");
        }


    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView timeStamp;

        OnNoteClickListener onNoteClickListener;


        public ViewHolder(@NonNull View itemView, OnNoteClickListener onNoteClickListener) {
            super(itemView);
            this.onNoteClickListener = onNoteClickListener;
            title = itemView.findViewById(R.id.note_title);
            timeStamp = itemView.findViewById(R.id.note_time_stamp);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteClickListener.onNoteClick(getBindingAdapterPosition());
        }
    }
    public interface OnNoteClickListener{
        void onNoteClick(int position);
    }
}
