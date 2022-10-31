package com.example.notex.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notex.Models.Notes;
import com.example.notex.R;

import java.util.List;

public class NotesLIstAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Notes> list;

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder{

    CardView notes_container;
    TextView textView_title, textview_notes, textview_date;
    ImageView imageView_pin;
    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textview_notes = itemView.findViewById(R.id.textview_notes);
        textview_date = itemView.findViewById(R.id.textview_date);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
    }
}
