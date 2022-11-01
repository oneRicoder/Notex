package com.example.notex.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notex.Models.Notes;
import com.example.notex.Models.NotesClickListener;
import com.example.notex.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesLIstAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Notes> list;
    NotesClickListener listener;

    public NotesLIstAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textview_notes.setText(list.get(position).getNote());
        holder.textview_date.setText(list.get(position).getDate());
        holder.textview_date.setSelected(true);

        if (list.get(position).getPinned()){
            holder.imageView_pin.setImageResource(R.drawable.ic_pin);
        }else {
            holder.imageView_pin.setImageResource(0);
        }
        Random random = new Random();
        String color = String.format("#%06x", random.nextInt(256*256*256));
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(getColor()));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });
    }
    public int getColor(){
        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.color1);
        colorList.add(R.color.color2);
        colorList.add(R.color.color3);
        colorList.add(R.color.color4);
        colorList.add(R.color.color5);
        colorList.add(R.color.color6);
        colorList.add(R.color.color7);
        colorList.add(R.color.color8);
        Random rand = new Random();
        return colorList.get(rand.nextInt(colorList.size()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> fileteredList){
        list = fileteredList;
        notifyDataSetChanged();
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
