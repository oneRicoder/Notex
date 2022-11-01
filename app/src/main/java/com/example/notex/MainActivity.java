package com.example.notex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.notex.Adapters.NotesLIstAdapter;
import com.example.notex.Models.Database.RoomDB;
import com.example.notex.Models.Notes;
import com.example.notex.Models.NotesClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    NotesLIstAdapter notesLIstAdapter;
    List<Notes> notes;
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView searchView_home;
    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        searchView_home = findViewById(R.id.searchView_home);

        database = RoomDB.getInstance(this);
        notes = database.mainDao().getAll();

        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

    }

    private void filter(String newText) {
        List<Notes> filtered_list = new ArrayList<>();
        for (Notes singleNote : notes){
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNote().toLowerCase().contains(newText.toLowerCase())){
                filtered_list.add(singleNote);
            }
        }
        notesLIstAdapter.filterList(filtered_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101){
            if (resultCode == Activity.RESULT_OK){
                assert data != null;
                Notes new_note = (Notes) data.getSerializableExtra("note");
                database.mainDao().insert(new_note);
                notes.clear();
                notes.addAll(database.mainDao().getAll());
                notesLIstAdapter.notifyDataSetChanged();
            }
        }else if (requestCode == 102){
            if (resultCode == Activity.RESULT_OK){
                assert data != null;
                Notes new_note = (Notes) data.getSerializableExtra("note");
                database.mainDao().update(new_note.getID(), new_note.getTitle(), new_note.getNote());
                notes.clear();
                notes.addAll(database.mainDao().getAll());
                notesLIstAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesLIstAdapter = new NotesLIstAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesLIstAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
            intent.putExtra("old_notes", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopUP(cardView);
        }
    };

    private void showPopUP(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.pop_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                database.mainDao().delete(selectedNote);
                notes.clear();
                notes.addAll(database.mainDao().getAll());
                notesLIstAdapter.notifyDataSetChanged();
                return true;
            case R.id.pin:
                if (selectedNote.getPinned()){
                    database.mainDao().pin(selectedNote.getID(), false);
                }else {
                    database.mainDao().pin(selectedNote.getID(), true);
                }
                notes.clear();
                notes.addAll(database.mainDao().getAll());
                notesLIstAdapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }
}