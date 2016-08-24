package com.example.naggeshk.notetaker;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by naggeshk on 8/23/2016.
 */

class Note implements Serializable {
    private String title;
    private String note;
    private Date date;

    public Note(String title, String note, Date date) {
        this.title = title;
        this.note = note;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
