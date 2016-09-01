package com.example.naggeshk.notetaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Calendar;

import static com.example.naggeshk.notetaker.R.string.Save;

public class EditNoteActivity extends AppCompatActivity {
    public static final int RESULT_DELETE = -500;
    private boolean isInEditMode = true;
    private boolean isAddingNote = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Deletemessage);
        builder.setTitle(R.string.ConfirmDelete);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent returnIntent = new Intent();
                setResult(RESULT_DELETE, returnIntent);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        final EditText etNote = (EditText) findViewById(R.id.etNote);
        final EditText etDate = (EditText) findViewById(R.id.etDate);
        final Button bSave = (Button) findViewById(R.id.bSave);
        final Button bCancel = (Button) findViewById(R.id.bCancel);
        Serializable extra = getIntent().getSerializableExtra("Note");
        if (extra != null) {
            Note note = (Note) extra;
            etTitle.setText(note.getTitle());
            etNote.setText(note.getNote());
            Calendar calendar = Calendar.getInstance();
            etDate.setText(calendar.getTime().toString());
            isInEditMode = false;
            etTitle.setEnabled(false);
            etNote.setEnabled(false);
            bSave.setText(R.string.Edit);
            isAddingNote = false;
        }
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });
        //Some Changes
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInEditMode) {
                    Intent returnIntent = new Intent();
                    Note note = new Note(etTitle.getText().toString(), etNote.getText().toString(), Calendar.getInstance().getTime());
                    returnIntent.putExtra("Note", note);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    isInEditMode = true;
                    etTitle.setEnabled(true);
                    etNote.setEnabled(true);
                    bSave.setText(Save);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (isAddingNote) {
            menu.removeItem(R.id.deleteNote);
        }
        return true;
    }
}
