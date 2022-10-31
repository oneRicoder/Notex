package com.example.notex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notex.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {

    EditText editText_title, editText_note;
    ImageView imageView_save;
    Notes notes;
    boolean notecheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        editText_title = findViewById(R.id.editText_title);
        editText_note = findViewById(R.id.editText_notes);
        imageView_save = findViewById(R.id.imageView_save);

        Intent intent_extra = getIntent();
        if (intent_extra.hasExtra("old_notes")){
            notes = new Notes();
            notecheck = true;
            notes = (Notes) intent_extra.getSerializableExtra("old_notes");
            editText_title.setText(notes.getTitle());
            editText_note.setText(notes.getNote());
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText_title.getText().toString();
                String description = editText_note.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(NotesTakerActivity.this, "Please Enter a Note!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if (!notecheck){
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNote(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}