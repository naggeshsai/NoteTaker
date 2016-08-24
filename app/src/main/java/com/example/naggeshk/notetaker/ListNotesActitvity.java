package com.example.naggeshk.notetaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListNotesActitvity extends AppCompatActivity {
    private List<Note> notes = new ArrayList<Note>();
    private ListView lvNotes;
    private int editingNoteId = -1;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        notes.remove(info.position);
        populateList();
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (resultCode == EditNoteActivity.RESULT_DELETE) {
            notes.remove(editingNoteId);
            editingNoteId = -1;
            populateList();
        }
        Serializable extra = data.getSerializableExtra("Note");
        if (extra != null) {
            Note newNote = (Note) extra;
            if (editingNoteId > -1) {
                notes.set(editingNoteId, newNote);
                editingNoteId = -1;
            } else {
                notes.add(newNote);
            }
            populateList();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
        startActivityForResult(editNoteIntent, 1);
        return true;
    }

    private void populateList() {
        List<String> values = new ArrayList<String>();
        for (Note note : notes) {
            values.add(note.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lvNotes.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_list_notes);
        lvNotes = (ListView) findViewById(R.id.lvNotes);
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent editNoteIntent = new Intent(view.getContext(), EditNoteActivity.class);
                editNoteIntent.putExtra("Note", notes.get(i));
                editingNoteId = i;
                startActivityForResult(editNoteIntent, 1);
            }
        });
        registerForContextMenu(lvNotes);
        notes.add(new Note("First note", "First note note", new Date()));
        notes.add(new Note("Second note", "Second note note", new Date()));
        notes.add(new Note("Third note", "Third note note", new Date()));
        notes.add(new Note("Fourth note", "Fourth note note", new Date()));
        notes.add(new Note("Fifth note", "Fifth note note", new Date()));
        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_notes, menu);
        return true;
    }
}
